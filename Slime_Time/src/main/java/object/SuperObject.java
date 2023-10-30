package object;

import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import main.GameApplication;

public class SuperObject {
    public ImageView image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Hit box
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void render(GraphicsContext gc, GameApplication ga) {
        GraphicsContext gcObj = gc;
        int screenX = worldX - ga.player.worldX + ga.player.screenX;
        int screenY = worldY - ga.player.worldY + ga.player.screenY;

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
            worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
            worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
            worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
                gcObj.drawImage(image.getImage(), screenX, screenY);
        }
    }
}
