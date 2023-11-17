package object;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.GameApplication;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(GameApplication ga) {
        name = "Key";
        setup("key", "objects", ga.TILE_SIZE, ga.TILE_SIZE);
        stackable = true;
        collision = true;

    }
}