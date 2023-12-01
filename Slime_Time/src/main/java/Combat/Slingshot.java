package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

public class Slingshot extends Entity implements Weapon {

    Player player;
    int screenX;
    int screenY;
    boolean attacking;

    int damage = 1;
    int attackSpeed;
    public Slingshot (GameApplication ga, Player player) {
        this.ga = ga;
        this.player = player;
        attackValue = damage;
        attackSpeed = 20;
        speed = 20;
        screenX = player.screenX + 4 * ga.SCALE;
        screenY = player.screenY + 4 * ga.SCALE;
        solidArea = new Rectangle(0, 0, 8 * ga.SCALE, 8 * ga.SCALE);
        solidArea.setX(screenX);
        solidArea.setY(screenY);
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
        setup("stone_object1", "weapon", 8 * ga.SCALE, 8 * ga.SCALE);
        setup("stone_object2", "weapon", 8 * ga.SCALE, 8 * ga.SCALE);
        setup("stone_object3", "weapon", 8 * ga.SCALE, 8 * ga.SCALE);
        setup("stone_object4", "weapon", 8 * ga.SCALE, 8 * ga.SCALE);
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
            solidArea.setX(screenX - player.screenX + player.worldX);
            solidArea.setY(screenY - player.screenY + player.worldY);

            int resourceIndex = ga.cChecker.checkResource(this);
            if (resourceIndex != 999) {
                ga.resource[resourceIndex].takeDamage();
            }

            ++spriteCounter;
            if (spriteCounter >= attackSpeed / 4) {
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
            gcSlingshot.strokeRect(screenX, screenY, 8 * ga.SCALE, 8 * ga.SCALE);
        }
    }
}
