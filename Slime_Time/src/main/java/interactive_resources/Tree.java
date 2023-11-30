package interactive_resources;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameApplication;

import java.io.FileInputStream;

public class Tree extends SuperResource {

    public Tree(GameApplication ga) {
        super(ga);
        String name = "Tree";
        getTreeImage();
    }

    public void getTreeImage() {
        setup("tree", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("tree_broken", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
    }

    public void update() {
        switch (life) {
            case 4, 3, 2, 1 -> spriteNum = 1;
            case 0 -> spriteNum = 2;
        }
    }
}
