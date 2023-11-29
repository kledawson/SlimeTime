package interactive_resources;


import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import main.GameApplication;
import entity.Entity;

import java.io.FileInputStream;

public class SuperResource extends Entity {
    GameApplication ga;
    public Image resource;
    public String name;
    public int worldX, worldY;

    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Hit box
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    int speed;
    int maxLife;
    int life = maxLife;

    public SuperResource(GameApplication ga) {
        this.ga = ga;
        String name = "Resource";
        int speed = 0;
        int maxLife = 8;
        int life = maxLife;

        getResourceImage();
    }

    public void getResourceImage() {
        resource = setupResource("");
    }

    public Image setupResource(String imageName) {
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
            gcObj.drawImage(resource, screenX, screenY);
        }
    }
}