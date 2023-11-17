package object;

import entity.Entity;
import main.GameApplication;
import object.*;
import tiles_interactive.Rock;

public class ObjectManager {

    GameApplication ga;

    public ObjectManager(GameApplication ga) {
        this.ga = ga;
    }

    // Loads all Object Positions
    public void setObject() {
        
        ga.obj[0] = new OBJ_Key(ga);
        ga.obj[0].worldX = 66 * ga.TILE_SIZE;
        ga.obj[0].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[0].worldY = 66 * ga.TILE_SIZE;
        ga.obj[0].solidArea.setY(66 * ga.TILE_SIZE);

        ga.obj[1] = new OBJ_Key(ga);
        ga.obj[1].worldX = 66 * ga.TILE_SIZE;
        ga.obj[1].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[1].worldY = 61 * ga.TILE_SIZE;
        ga.obj[1].solidArea.setY(61 * ga.TILE_SIZE);

        ga.obj[2] = new OBJ_Key(ga);
        ga.obj[2].worldX = 66 * ga.TILE_SIZE;
        ga.obj[2].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[2].worldY = 68 * ga.TILE_SIZE;
        ga.obj[2].solidArea.setY(68 * ga.TILE_SIZE);

        ga.obj[3] = new OBJ_Key(ga);
        ga.obj[3].worldX = 66 * ga.TILE_SIZE;
        ga.obj[3].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[3].worldY = 63 * ga.TILE_SIZE;
        ga.obj[3].solidArea.setY(63 * ga.TILE_SIZE);

    }
}
