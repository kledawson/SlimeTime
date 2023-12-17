package interactive_resources;

import main.GameApplication;
import object.OBJ_Wood;

public class Tree extends SuperResource {

    public Tree(GameApplication ga, int worldCol, int worldRow) {
        super(ga, worldCol, worldRow);
        getTreeImage();
    }

    public void getTreeImage() {
        setup("tree", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("tree_break_1", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("tree_break_2", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("tree_break_3", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("tree_broken", "interactive_resources", ga.TILE_SIZE, ga.TILE_SIZE);
    }

    //updating sprite images to showcase "damaged" resources, sets up logic for resource destruction
    public void update(int i) {
        switch (life) {
            case 4 -> spriteNum = 1;
            case 3 -> spriteNum = 2;
            case 2 -> spriteNum = 3;
            case 1 -> spriteNum = 4;
            case 0 -> {
                removeFromGame(i, new OBJ_Wood(ga));
                ga.playSE(9);
            }
        }
        ++iFrameCount;
    }

    @Override
    public void takeDamage(int damage) {
        ga.playSE(10);
        super.takeDamage(damage);
    }
}
