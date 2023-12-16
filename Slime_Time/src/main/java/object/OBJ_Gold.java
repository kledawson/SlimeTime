package object;

import main.GameApplication;

public class OBJ_Gold extends SuperObject {
    public OBJ_Gold(GameApplication ga) {
        name = "Gold";
        setup("gold", "objects", ga.TILE_SIZE * 7 / 8, ga.TILE_SIZE * 7 / 8);
        stackable = true;
        collision = true;

    }
}