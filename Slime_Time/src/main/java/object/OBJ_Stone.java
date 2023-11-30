package object;

import main.GameApplication;

public class OBJ_Stone extends SuperObject{
    public OBJ_Stone(GameApplication ga) {
        name = "Stone";
        setup("stone", "objects", ga.TILE_SIZE, ga.TILE_SIZE);
        stackable = true;
        collision = true;


    }
}
