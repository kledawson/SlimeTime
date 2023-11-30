package interactive_resources;

import main.GameApplication;
import object.Example_Object;

public class ResourceManager {

    GameApplication ga;

    public ResourceManager(GameApplication ga) {
        this.ga = ga;
    }

    public void setResource() {

        ga.resource[0] = new Rock(ga);
        ga.resource[0].worldX = 75 * ga.TILE_SIZE;
        ga.resource[0].worldY = 45 * ga.TILE_SIZE;
        ga.resource[0].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[0].solidArea.setY(45 * ga.TILE_SIZE);

        ga.resource[1] = new Rock(ga);
        ga.resource[1].worldX = 75 * ga.TILE_SIZE;
        ga.resource[1].worldY = 46 * ga.TILE_SIZE;
        ga.resource[1].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[1].solidArea.setY(46 * ga.TILE_SIZE);

        ga.resource[2] = new Rock(ga);
        ga.resource[2].worldX = 75 * ga.TILE_SIZE;
        ga.resource[2].worldY = 47 * ga.TILE_SIZE;
        ga.resource[2].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[2].solidArea.setY(47 * ga.TILE_SIZE);

        ga.resource[3] = new Rock(ga);
        ga.resource[3].worldX = 75 * ga.TILE_SIZE;
        ga.resource[3].worldY = 48 * ga.TILE_SIZE;
        ga.resource[3].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[3].solidArea.setY(48 * ga.TILE_SIZE);

        ga.resource[4] = new Rock(ga);
        ga.resource[4].worldX = 75 * ga.TILE_SIZE;
        ga.resource[4].worldY = 49 * ga.TILE_SIZE;
        ga.resource[4].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[4].solidArea.setY(49 * ga.TILE_SIZE);

        ga.resource[5] = new Tree(ga);
        ga.resource[5].worldX = 75 * ga.TILE_SIZE;
        ga.resource[5].worldY = 50 * ga.TILE_SIZE;
        ga.resource[5].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[5].solidArea.setY(50 * ga.TILE_SIZE);

        ga.resource[6] = new Tree(ga);
        ga.resource[6].worldX = 75 * ga.TILE_SIZE;
        ga.resource[6].worldY = 51 * ga.TILE_SIZE;
        ga.resource[6].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[6].solidArea.setY(51 * ga.TILE_SIZE);

        ga.resource[7] = new Tree(ga);
        ga.resource[7].worldX = 75 * ga.TILE_SIZE;
        ga.resource[7].worldY = 52 * ga.TILE_SIZE;
        ga.resource[7].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[7].solidArea.setY(52 * ga.TILE_SIZE);

        ga.resource[8] = new Tree(ga);
        ga.resource[8].worldX = 75 * ga.TILE_SIZE;
        ga.resource[8].worldY = 53 * ga.TILE_SIZE;
        ga.resource[8].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[8].solidArea.setY(53 * ga.TILE_SIZE);

        ga.resource[9] = new Tree(ga);
        ga.resource[9].worldX = 75 * ga.TILE_SIZE;
        ga.resource[9].worldY = 54 * ga.TILE_SIZE;
        ga.resource[9].solidArea.setX(75 * ga.TILE_SIZE);
        ga.resource[9].solidArea.setY(54 * ga.TILE_SIZE);

    }
}
