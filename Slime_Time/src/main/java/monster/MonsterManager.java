package monster;

import entity.Entity;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

public class MonsterManager {

    GameApplication ga;
    int timer = 0;

    public MonsterManager(GameApplication ga) {
        this.ga = ga;
    }

    //sets greenslimes on the map
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

    public void update() {
        ++timer;
        if (timer > 60 * 60 * 3) {
            // SPAWN IN MORE MONSTERS HERE
            for (GreenSlime monster : ga.greenSlime) {
                if (monster != null) {
                    ++monster.attackValue;
                    ++monster.speed;
                }
            }
            timer = 0;
        }
    }

}
