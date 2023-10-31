package object;

import main.GameApplication;
import object.*;

public class ObjectManager {

    GameApplication ga;

    public ObjectManager(GameApplication ga) {
        this.ga = ga;
    }

    // Loads all Object Positions
    public void setObject() {
        ga.obj[0] = new Example_Object(ga);
        ga.obj[0].worldX = 0 * ga.TILE_SIZE;
        ga.obj[0].worldY = 0 * ga.TILE_SIZE;


    }
}
