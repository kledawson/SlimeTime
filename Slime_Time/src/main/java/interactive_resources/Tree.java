package interactive_resources;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameApplication;

import java.io.FileInputStream;

public class Tree extends Entity {
    GameApplication ga;
    public Image tree; // Rock Sprites
    public int treeLife;


    public Tree(GameApplication ga) {
        this.ga = ga;
        String name = "Tree";
        int speed = 0;
        int maxTreeLife = 8;
        treeLife = maxTreeLife;

        getTreeImage();
        updateTreeImage();
    }


    public void getTreeImage() {
        tree = setupTree("tree");
    }

    public Image setupTree(String imageName) {
        try {
            return new Image(new FileInputStream("Slime_Time/res/interactive_resources/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
        }
        catch (Exception e) {
            try {
                return new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
            }
            catch (Exception ex) {
                System.err.println("Error loading image: " + imageName);
                ex.printStackTrace();
                return null;
            }
        }
    }

    public void render(GraphicsContext gc, GameApplication ga) {
        GraphicsContext gcObj = gc;
        int screenX = worldX - ga.player.worldX + ga.player.screenX;
        int screenY = worldY - ga.player.worldY + ga.player.screenY;

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
                worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
                worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
                worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
            gcObj.drawImage(tree, screenX, screenY);
        }
    }

    public void updateTreeImage() {

        if (treeLife == 7 || treeLife == 8) {
            tree = setupTree("tree");
        }
        else if (treeLife == 5 || treeLife == 6) {
            tree = setupTree("tree_break_1");
        }
        else if (treeLife == 3 || treeLife == 4) {
            tree = setupTree("tree_break_2");
        }
        else if (treeLife == 1 || treeLife == 2) {
            tree = setupTree("tree_break_3");
        }
        else if (treeLife == 0) {
            tree = setupTree("tree_broken");
        }
        else {
            tree = setupTree("error");
        }
    }

    public void treeDamage() {
        treeLife--;
        updateTreeImage();
    }
}
