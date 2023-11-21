package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.image.Image;
import javafx.scene.shape.Arc;
import main.GameApplication;

import java.io.FileInputStream;

public class Slingshot extends Entity implements Weapon {

    Player player;
    int screenX;
    int screenY;
    boolean attacking;

    int damage;
    int attackSpeed;
    public Slingshot (GameApplication ga, Player player) {
        this.ga = ga;
        this.player = player;
    }
    @Override
    public void attack() {
    }

    @Override
    public void upgrade() {
    }

    public void getWeaponImage() {
        setup("stone_object1");
        setup("stone_object2");
        setup("stone_object3");
        setup("stone_object4");
    }

    public void setup(String imageName) {
        try {
            images.add(new Image(new FileInputStream("Slime_Time/res/weapon/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
        }
        catch (Exception e) {
            try {
                images.add(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
