package object;

import main.GameApplication;

public class OBJ_Stone extends SuperObject{
    public OBJ_Stone(GameApplication ga) {
        name = "Stone";
        setup("stone", "objects", ga.TILE_SIZE - 15, ga.TILE_SIZE - 15);
        stackable = true;
        collision = true;


    }
}
