package object;

import main.GameApplication;

public class OBJ_Wood extends SuperObject{
    public OBJ_Wood(GameApplication ga) {
        name = "Wood";
        setup("wood", "objects", ga.TILE_SIZE * 7 / 8, ga.TILE_SIZE * 7 / 8);
        stackable = true;
        collision = true;
        

    }
}
