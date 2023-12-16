package monster;


import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import main.GameApplication;
import entity.Entity;

import java.io.FileInputStream;

public class SuperMonster extends Entity {
    GameApplication ga;
    public Image monster;
    public String name;
    public int worldX, worldY;

    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Hit box
    int speed;
    int maxLife;
    int life = maxLife;

    public SuperMonster(GameApplication ga) {
        this.ga = ga;
        String name = "Monster";
        int speed = 0;
        int maxLife = 8;
        int life = maxLife;

        getMonsterImage();
    }

    public void getMonsterImage() {
        monster = setupMonster("");
    }

    public Image setupMonster(String imageName) {
        try {
            return new Image(new FileInputStream("Slime_Time/res/monster/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
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
        int screenX = worldX - ga.player.worldX + ga.player.screenX;
        int screenY = worldY - ga.player.worldY + ga.player.screenY;

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
                worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
                worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
                worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
            gc.drawImage(monster, screenX, screenY);
        }
    }


}