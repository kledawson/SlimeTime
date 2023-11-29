package object;

import main.GameApplication;

public class OBJ_Gold extends SuperObject {
    public OBJ_Gold(GameApplication ga) {
        name = "Gold";
        setup("key", "objects", ga.TILE_SIZE, ga.TILE_SIZE);
        stackable = true;
        collision = true;

    }
}