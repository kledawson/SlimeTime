package monster;

import javafx.scene.shape.Rectangle;
import main.GameApplication;

public class MonsterManager {

    GameApplication ga;

    public MonsterManager(GameApplication ga) {
        this.ga = ga;
    }


    public void setMonster() {

//        ga.monster[0] = new SuperMonster(ga);
//        ga.monster[0].worldX = 0 * ga.TILE_SIZE;
//        ga.monster[0].worldY = 0 * ga.TILE_SIZE;

    }

    public void setGreenSlime() {

        ga.greenSlime[0] = new GreenSlime(ga);
        ga.greenSlime[0].worldX = 66 * ga.TILE_SIZE;
        ga.greenSlime[0].worldY = 52 * ga.TILE_SIZE;
        ((Rectangle)ga.greenSlime[0].solidArea).setX(66 * ga.TILE_SIZE);
        ((Rectangle)ga.greenSlime[0].solidArea).setY(53 * ga.TILE_SIZE);

        ga.greenSlime[1] = new GreenSlime(ga);
        ga.greenSlime[1].worldX = 66 * ga.TILE_SIZE;
        ga.greenSlime[1].worldY = 51 * ga.TILE_SIZE;
        ((Rectangle)ga.greenSlime[0].solidArea).setX(66 * ga.TILE_SIZE);
        ((Rectangle)ga.greenSlime[0].solidArea).setY(52 * ga.TILE_SIZE);

        ga.greenSlime[2] = new GreenSlime(ga);
        ga.greenSlime[2].worldX = 66 * ga.TILE_SIZE;
        ga.greenSlime[2].worldY = 50 * ga.TILE_SIZE;
        ((Rectangle)ga.greenSlime[0].solidArea).setX(66 * ga.TILE_SIZE);
        ((Rectangle)ga.greenSlime[0].solidArea).setY(51 * ga.TILE_SIZE);

        ga.greenSlime[3] = new GreenSlime(ga);
        ga.greenSlime[3].worldX = 66 * ga.TILE_SIZE;
        ga.greenSlime[3].worldY = 49 * ga.TILE_SIZE;
        ((Rectangle)ga.greenSlime[0].solidArea).setX(66 * ga.TILE_SIZE);
        ((Rectangle)ga.greenSlime[0].solidArea).setY(50 * ga.TILE_SIZE);

        ga.greenSlime[4] = new GreenSlime(ga);
        ga.greenSlime[4].worldX = 66 * ga.TILE_SIZE;
        ga.greenSlime[4].worldY = 48 * ga.TILE_SIZE;
        ((Rectangle)ga.greenSlime[0].solidArea).setX(66 * ga.TILE_SIZE);
        ((Rectangle)ga.greenSlime[0].solidArea).setY(49 * ga.TILE_SIZE);

    }

}
