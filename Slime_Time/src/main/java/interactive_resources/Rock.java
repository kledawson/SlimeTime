package interactive_resources;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameApplication;
import object.OBJ_Gold;
import object.OBJ_Stone;
import object.SuperObject;

import java.io.FileInputStream;

public class Rock extends SuperResource {

    public Rock(GameApplication ga, int worldCol, int worldRow) {
        super(ga, worldCol, worldRow);
        String name = "Rock";
        getRockImage();
    }


    public void getRockImage() {
        setup("rock", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("rock_break_1", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("rock_break_2", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("rock_break_3", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("rock_broken", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
    }

    public void update(int i) {
       // int index = ga.cChecker.checkResource(this);
        switch (life) {
            case 4 -> spriteNum = 1;
            case 3 -> spriteNum = 2;
            case 2 -> spriteNum = 3;
            case 1 -> spriteNum = 4;
            case 0 -> {
               removeFromGame(i, new OBJ_Stone(ga));
            }
        }
        ++iFrameCount;
    }

}