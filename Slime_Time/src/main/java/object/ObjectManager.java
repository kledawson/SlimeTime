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

        ga.rock[0] = new Rock(ga);
        ga.rock[0].worldX = 25 * ga.TILE_SIZE;
        ga.rock[0].worldY = 23 * ga.TILE_SIZE;

        ga.rock[1] = new Rock(ga);
        ga.rock[1].worldX = 24 * ga.TILE_SIZE;
        ga.rock[1].worldY = 23 * ga.TILE_SIZE;

        ga.rock[2] = new Rock(ga);
        ga.rock[2].worldX = 23 * ga.TILE_SIZE;
        ga.rock[2].worldY = 23 * ga.TILE_SIZE;

        ga.rock[3] = new Rock(ga);
        ga.rock[3].worldX = 22 * ga.TILE_SIZE;
        ga.rock[3].worldY = 23 * ga.TILE_SIZE;

        ga.rock[4] = new Rock(ga);
        ga.rock[4].worldX = 21 * ga.TILE_SIZE;
        ga.rock[4].worldY = 23 * ga.TILE_SIZE;

        ga.obj[0] = new OBJ_Key(ga);
        ga.obj[0].worldX = 26 * ga.TILE_SIZE;
        ga.obj[0].worldY = 26 * ga.TILE_SIZE;

        ga.obj[1] = new OBJ_Key(ga);
        ga.obj[1].worldX = 26 * ga.TILE_SIZE;
        ga.obj[1].worldY = 21 * ga.TILE_SIZE;

        ga.obj[2] = new OBJ_Key(ga);
        ga.obj[2].worldX = 26 * ga.TILE_SIZE;
        ga.obj[2].worldY = 28 * ga.TILE_SIZE;

        ga.obj[3] = new OBJ_Key(ga);
        ga.obj[3].worldX = 26 * ga.TILE_SIZE;
        ga.obj[3].worldY = 23 * ga.TILE_SIZE;

    }
}
