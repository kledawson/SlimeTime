package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

import java.io.FileInputStream;

public class Slingshot extends Entity implements Weapon {

    Player player;
    public Rectangle solidArea = new Rectangle();
    int screenX;
    int screenY;
    boolean attacking;

    int damage = 1;
    int attackSpeed;
    public Slingshot (GameApplication ga, Player player) {
        this.ga = ga;
        this.player = player;
        attackValue = damage;
        speed = 20;
        screenX = player.screenX + 4 * ga.SCALE;
        screenY = player.screenY + 4 * ga.SCALE;
        solidArea.setX(screenX);
        solidArea.setY(screenY);
        solidArea.setHeight(8 * ga.SCALE);
        solidArea.setWidth(8 * ga.SCALE);
        getWeaponImage();
    }
    @Override
    public void attack() {
        attacking = true;
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
            images.add(new Image(new FileInputStream("Slime_Time/res/weapon/" + imageName + ".png"), 8 * ga.SCALE, 8 * ga.SCALE, false, false));
        }
        catch (Exception e) {
            try {
                images.add(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), 8 * ga.SCALE, 8 * ga.SCALE, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        if (!attacking) {
            direction = player.scythe.direction;
        }
        else {
            double diagonalSpeed = speed / Math.sqrt(2);
            switch (direction) {
                case "up" -> {
                    screenY -= speed;
                }
                case "left_up" -> {
                    screenX -= diagonalSpeed;
                    screenY -= diagonalSpeed;
                }
                case "left" -> {
                    screenX -= speed;
                }
                case "left_down" -> {
                    screenX -= diagonalSpeed;
                    screenY += diagonalSpeed;
                }
                case "down" -> {
                    screenY += speed;
                }
                case "right_down" -> {
                    screenX += diagonalSpeed;
                    screenY += diagonalSpeed;
                }
                case "right" -> {
                    screenX += speed;
                }
                case "right_up" -> {
                    screenX += diagonalSpeed;
                    screenY -= diagonalSpeed;
                }
            }
            solidArea.setX(screenX);
            solidArea.setY(screenY);
            ++spriteCounter;
            if (spriteCounter >= 5) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                    attacking = false;
                    screenX = player.screenX + 4 * ga.SCALE;
                    screenY = player.screenY + 4 * ga.SCALE;
                    solidArea.setX(screenX);
                    solidArea.setY(screenY);
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void render(GraphicsContext gc) {
        GraphicsContext gcSlingshot = gc;
        Image image = null;
        image = images.get(-1 + spriteNum);
        if (attacking) {
            gcSlingshot.drawImage(image, screenX, screenY);
        }
        gcSlingshot.strokeRect(screenX, screenY, 8 * ga.SCALE, 8 * ga.SCALE);
    }
}
