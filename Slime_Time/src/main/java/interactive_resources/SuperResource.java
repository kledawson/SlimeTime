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

    public SuperResource(GameApplication ga) {
        this.ga = ga;
        name = "Resource";
        maxLife = 4;
        life = maxLife;
        solidArea = new Rectangle(0, 0, ga.TILE_SIZE, ga.TILE_SIZE);
        collision = true;
    }

    public void render(GraphicsContext gc, GameApplication ga) {
        GraphicsContext gcRes = gc;
        int screenX = worldX - ga.player.worldX + ga.player.screenX;
        int screenY = worldY - ga.player.worldY + ga.player.screenY;

        Image image = null;
        image = images.get(-1 + spriteNum);

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
                worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
                worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
                worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
            gcRes.drawImage(image, screenX, screenY);
        }
    }

    public void takeDamage() {
        if (life > 0) {
            --life;
        }
        System.out.println("TAKING DAMAGE!");
    }
}