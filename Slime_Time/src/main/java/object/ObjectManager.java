package object;

import entity.Entity;
import main.GameApplication;
import interactive_resources.Rock;

public class ObjectManager {

    GameApplication ga;

    public ObjectManager(GameApplication ga) {
        this.ga = ga;
    }

    // Loads all Object Positions
    public void setObject() {

        ga.obj[0] = new OBJ_Wood(ga);
        ga.obj[0].worldX = 66 * ga.TILE_SIZE;
        ga.obj[0].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[0].worldY = 66 * ga.TILE_SIZE;
        ga.obj[0].solidArea.setY(66 * ga.TILE_SIZE);

        ga.obj[1] = new OBJ_Wood(ga);
        ga.obj[1].worldX = 66 * ga.TILE_SIZE;
        ga.obj[1].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[1].worldY = 61 * ga.TILE_SIZE;
        ga.obj[1].solidArea.setY(61 * ga.TILE_SIZE);

        ga.obj[2] = new OBJ_Stone(ga);
        ga.obj[2].worldX = 66 * ga.TILE_SIZE;
        ga.obj[2].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[2].worldY = 68 * ga.TILE_SIZE;
        ga.obj[2].solidArea.setY(68 * ga.TILE_SIZE);

        ga.obj[3] = new OBJ_Stone(ga);
        ga.obj[3].worldX = 66 * ga.TILE_SIZE;
        ga.obj[3].solidArea.setX(66 * ga.TILE_SIZE);
        ga.obj[3].worldY = 63 * ga.TILE_SIZE;
        ga.obj[3].solidArea.setY(63 * ga.TILE_SIZE);

        ga.obj[4] = new OBJ_Gold(ga);
        ga.obj[4].worldX = 68 * ga.TILE_SIZE;
        ga.obj[4].solidArea.setX(68 * ga.TILE_SIZE);
        ga.obj[4].worldY = 64 * ga.TILE_SIZE;
        ga.obj[4].solidArea.setY(64 * ga.TILE_SIZE);

        ga.obj[5] = new OBJ_Gold(ga);
        ga.obj[5].worldX = 69 * ga.TILE_SIZE;
        ga.obj[5].solidArea.setX(69 * ga.TILE_SIZE);
        ga.obj[5].worldY = 64 * ga.TILE_SIZE;
        ga.obj[5].solidArea.setY(64 * ga.TILE_SIZE);

        ga.obj[6] = new OBJ_Gold(ga);
        ga.obj[6].worldX = 70 * ga.TILE_SIZE;
        ga.obj[6].solidArea.setX(70 * ga.TILE_SIZE);
        ga.obj[6].worldY = 64 * ga.TILE_SIZE;
        ga.obj[6].solidArea.setY(64 * ga.TILE_SIZE);

        ga.obj[7] = new OBJ_Gold(ga);
        ga.obj[7].worldX = 71 * ga.TILE_SIZE;
        ga.obj[7].solidArea.setX(71 * ga.TILE_SIZE);
        ga.obj[7].worldY = 64 * ga.TILE_SIZE;
        ga.obj[7].solidArea.setY(64 * ga.TILE_SIZE);

        ga.obj[8] = new OBJ_Stone(ga);
        ga.obj[8].worldX = 67 * ga.TILE_SIZE;
        ga.obj[8].solidArea.setX(67 * ga.TILE_SIZE);
        ga.obj[8].worldY = 68 * ga.TILE_SIZE;
        ga.obj[8].solidArea.setY(68 * ga.TILE_SIZE);

        ga.obj[9] = new OBJ_Wood(ga);
        ga.obj[9].worldX = 67 * ga.TILE_SIZE;
        ga.obj[9].solidArea.setX(67 * ga.TILE_SIZE);
        ga.obj[9].worldY = 61 * ga.TILE_SIZE;
        ga.obj[9].solidArea.setY(61 * ga.TILE_SIZE);

    }
}
