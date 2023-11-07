package object;

import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import main.GameApplication;

public class SuperObject {
    public ImageView image;
    public String name;
    public boolean collision = false;
    public double worldX;
    public double worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Hit box
    public double solidAreaDefaultX = 0;
    public double solidAreaDefaultY = 0;

    public void render(GraphicsContext gc, GameApplication ga) {
        GraphicsContext gcObj = gc;
        double screenX = worldX - ga.player.worldX + ga.player.screenX;
        double screenY = worldY - ga.player.worldY + ga.player.screenY;

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
            worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
            worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
            worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
                gcObj.drawImage(image.getImage(), screenX, screenY);
        }
    }
}
