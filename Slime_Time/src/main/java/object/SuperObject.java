package object;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import main.GameApplication;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SuperObject {
    GameApplication ga;
    public List<Image> images = new ArrayList<>();
    public ImageView image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Hit box
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public boolean stackable;
    public int amount = 1;

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

    public void setup(String imageName, String fileType, int sizeX, int sizeY) {
        try {
            image = new ImageView(new Image(new FileInputStream("Slime_Time/res/" + fileType + "/" + imageName + ".png"), sizeX, sizeY, false, false));
            images.add(new Image(new FileInputStream("Slime_Time/res/" + fileType + "/" + imageName + ".png"), sizeX, sizeY, false, false));
        }
        catch (Exception e) {
            try {
                image = new ImageView(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
                images.add(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                image = null;
            }
        }
    }
}
