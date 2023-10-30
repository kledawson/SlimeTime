package tile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Tile Object for use in TileManager Class
public class Tile {
    public ImageView image;
    public boolean collision = false;

    public void setImage(Image img) {
        image = new ImageView(img);
    }
    public Image getImage() {
        return image.getImage();
    }

}
