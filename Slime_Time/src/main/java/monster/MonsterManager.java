package monster;

import entity.Entity;
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

        spawnMonster(new GreenSlime(ga), 66 * ga.TILE_SIZE, 53 * ga.TILE_SIZE );

        spawnMonster(new GreenSlime(ga), 66 * ga.TILE_SIZE, 51 * ga.TILE_SIZE );

        spawnMonster(new GreenSlime(ga), 66 * ga.TILE_SIZE, 50 * ga.TILE_SIZE );

        spawnMonster(new GreenSlime(ga), 66 * ga.TILE_SIZE, 49 * ga.TILE_SIZE );

        spawnMonster(new GreenSlime(ga), 66 * ga.TILE_SIZE, 48 * ga.TILE_SIZE );


    }

    //spawns in monster given coordinates
    public void spawnMonster(GreenSlime greenSlime, int worldX, int worldY) {
        greenSlime.worldX = worldX;
        greenSlime.worldY = worldY;
        ((Rectangle)greenSlime.solidArea).setX(worldX + 1);
        ((Rectangle)greenSlime.solidArea).setY(worldY + 1);
        ga.greenSlime.add(greenSlime);
    }

}
