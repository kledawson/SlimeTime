package tiles_interactive;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

import java.io.FileInputStream;

public class Rock extends Entity {
    public boolean destructible = true;
    private Rectangle solidArea;

    public Rock(GameApplication ga) {
        this.ga = ga;
        String name = "Rock";
        int speed = 0;
        int maxLife = 8;
        int life = maxLife;

        solidArea = new Rectangle(3, 18, 18, 18);
        solidArea.setFill(Color.BLUE);
        Pane root = new Pane();
        root.getChildren().add(solidArea);
        getRockImage();
    }

    public void getRockImage() {
        setupRock("rock");
        /*
        setupRock("rock_break1");
        setupRock("rock_break2");
        setupRock("rock_break3");
        */
    }

    public void setupRock(String imageName) {
        try {
            images.add(new Image(new FileInputStream("Slime_Time/res/tiles_interactive/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
        }
        catch (Exception e) {
            try {
                images.add(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setRock() {
        ga.rock[0] = new Rock(ga);
        ga.rock[0].worldX = 25 * ga.TILE_SIZE;
        ga.rock[0].worldY = 28 * ga.TILE_SIZE;

        ga.rock[1] = new Rock(ga);
        ga.rock[1].worldX = 24 * ga.TILE_SIZE;
        ga.rock[1].worldY = 28 * ga.TILE_SIZE;
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
            gcObj.drawImage(images.get(0), screenX, screenY);
        }
    }

}
