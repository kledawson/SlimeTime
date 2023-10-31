package object;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.GameApplication;

import java.io.FileInputStream;

public class Example_Object extends SuperObject{
    // Loads Image & Determine Collision
    public Example_Object(GameApplication ga) {
        name = "KeyHole";
        try {
            image = new ImageView(new Image(new FileInputStream("Slime_Time/res/object/object_name.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
        }
        catch (Exception e) {
            try {
                image = new ImageView(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                image = null;
            }
        }
        collision = true;
    }

}
