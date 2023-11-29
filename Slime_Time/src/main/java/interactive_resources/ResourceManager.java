package interactive_resources;

import main.GameApplication;
import object.Example_Object;

public class ResourceManager {

    GameApplication ga;

    public ResourceManager(GameApplication ga) {
        this.ga = ga;
    }


    public void setResource() {

        ga.resource[0] = new SuperResource(ga);
        ga.resource[0].worldX = 0 * ga.TILE_SIZE;
        ga.resource[0].worldY = 0 * ga.TILE_SIZE;

    }

    public void setRock() {

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

    public void setTree() {

        ga.tree[0] = new Tree(ga);
        ga.tree[0].worldX = 25 * ga.TILE_SIZE;
        ga.tree[0].worldY = 19 * ga.TILE_SIZE;

        ga.tree[1] = new Tree(ga);
        ga.tree[1].worldX = 24 * ga.TILE_SIZE;
        ga.tree[1].worldY = 19 * ga.TILE_SIZE;

        ga.tree[2] = new Tree(ga);
        ga.tree[2].worldX = 23 * ga.TILE_SIZE;
        ga.tree[2].worldY = 19 * ga.TILE_SIZE;

        ga.tree[3] = new Tree(ga);
        ga.tree[3].worldX = 22 * ga.TILE_SIZE;
        ga.tree[3].worldY = 19 * ga.TILE_SIZE;

        ga.tree[4] = new Tree(ga);
        ga.tree[4].worldX = 21 * ga.TILE_SIZE;
        ga.tree[4].worldY = 19 * ga.TILE_SIZE;

    }
}
