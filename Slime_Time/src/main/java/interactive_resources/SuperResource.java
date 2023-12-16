package interactive_resources;


import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import main.GameApplication;
import entity.Entity;
import object.SuperObject;

import java.io.FileInputStream;

public class SuperResource extends Entity {

    public SuperResource(GameApplication ga, int worldCol, int worldRow) {
        this.ga = ga;
        worldX = worldCol * ga.TILE_SIZE;
        worldY = worldRow * ga.TILE_SIZE;
        solidArea = new Rectangle(worldX, worldY, ga.TILE_SIZE, ga.TILE_SIZE);
        name = "Resource";
        maxLife = 4;
        life = maxLife;
        collision = true;
        iFrameCount = 0;
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
        if (life > 0 && iFrameCount > 30) {
            --life;
            iFrameCount = 0;
        }
        System.out.println("TAKING DAMAGE!");
    }

    public void removeFromGame(int index, SuperObject object) {
        if (ga.resource[index].life == 0) {
            int worldX = ga.resource[index].worldX;
            int worldY = ga.resource[index].worldY;
            ga.resource[index] = null;
            // Add the new object to objM
            ga.objM.addItem(object, worldX, worldY);

        }
    }

}