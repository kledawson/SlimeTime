package object;

import main.GameApplication;

public class OBJ_Wood extends SuperObject{
    public OBJ_Wood(GameApplication ga) {
        name = "Wood";
        setup("wood", "objects", ga.TILE_SIZE - 10, ga.TILE_SIZE - 10);
        stackable = true;
        collision = true;
        

    }
}
