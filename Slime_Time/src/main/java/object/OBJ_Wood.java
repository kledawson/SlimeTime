package object;

import main.GameApplication;

public class OBJ_Wood extends SuperObject{
    public OBJ_Wood(GameApplication ga) {
        name = "Wood";
        setup("wood", "objects", ga.TILE_SIZE, ga.TILE_SIZE);
        stackable = true;
        collision = true;
        

    }
}
