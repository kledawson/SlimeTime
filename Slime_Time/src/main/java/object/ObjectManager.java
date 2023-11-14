package object;

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

        ga.obj[0] = new Example_Object(ga);
        ga.obj[0].worldX = 0 * ga.TILE_SIZE;
        ga.obj[0].worldY = 0 * ga.TILE_SIZE;

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

    }
}
