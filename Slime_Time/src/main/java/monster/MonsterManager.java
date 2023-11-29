package monster;

import main.GameApplication;

public class MonsterManager {

    GameApplication ga;

    public MonsterManager(GameApplication ga) {
        this.ga = ga;
    }


    public void setMonster() {

        ga.monster[0] = new SuperMonster(ga);
        ga.monster[0].worldX = 0 * ga.TILE_SIZE;
        ga.monster[0].worldY = 0 * ga.TILE_SIZE;

    }

    public void setGreenSlime() {

        ga.greenSlime[0] = new GreenSlime(ga);
        ga.greenSlime[0].worldX = 25 * ga.TILE_SIZE;
        ga.greenSlime[0].worldY = 16 * ga.TILE_SIZE;

        ga.greenSlime[1] = new GreenSlime(ga);
        ga.greenSlime[1].worldX = 24 * ga.TILE_SIZE;
        ga.greenSlime[1].worldY = 16 * ga.TILE_SIZE;

        ga.greenSlime[2] = new GreenSlime(ga);
        ga.greenSlime[2].worldX = 23 * ga.TILE_SIZE;
        ga.greenSlime[2].worldY = 16 * ga.TILE_SIZE;

        ga.greenSlime[3] = new GreenSlime(ga);
        ga.greenSlime[3].worldX = 22 * ga.TILE_SIZE;
        ga.greenSlime[3].worldY = 16 * ga.TILE_SIZE;

        ga.greenSlime[4] = new GreenSlime(ga);
        ga.greenSlime[4].worldX = 21 * ga.TILE_SIZE;
        ga.greenSlime[4].worldY = 16 * ga.TILE_SIZE;

    }

}
