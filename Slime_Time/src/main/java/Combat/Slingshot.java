package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

import java.util.List;

public class Slingshot extends Entity implements Weapon {

    Player player;
    int screenX;
    int screenY;
    boolean attacking;

    int damage = 1;
    public int attackSpeed;
    int attackCount;
    public Slingshot (GameApplication ga, Player player) {
        super(ga);
        this.player = player;
        attackValue = damage;
        attackSpeed = 60;
        speed = 4;
        screenX = player.screenX + 4 * ga.SCALE;
        screenY = player.screenY + 4 * ga.SCALE;
        solidArea = new Rectangle(0, 0, 8 * ga.SCALE, 8 * ga.SCALE);
        ((Rectangle)solidArea).setX(screenX);
        ((Rectangle)solidArea).setY(screenY);
        getWeaponImage();
    }
    @Override
    public void attack() {
        if (attackCount >= attackSpeed) {
            attacking = true;
            attackCount = 0;
            ga.playSE(5);
        }
    }

    @Override
    public void upgrade() {
        if (player.hasRequiredItems(player.projectileGoldCost, player.projectileStoneCost, player.projectileWoodCost)) {
            int goldIndex = player.searchItemInInventory("Gold");
            int stoneIndex = player.searchItemInInventory("Stone");
            int woodIndex = player.searchItemInInventory("Wood");

            player.inventory.get(goldIndex).amount -= player.projectileGoldCost;
            player.inventory.get(stoneIndex).amount -= player.projectileStoneCost;
            player.inventory.get(woodIndex).amount -= player.projectileWoodCost;

            //apply upgrade
            ++attackValue;
            attackSpeed *= 2.0 / 3;
            speed *= 3.0 / 2;

            // Update the costs for the next upgrade
            ++player.projectileGoldCost;
            ++player.projectileStoneCost;
            ++player.projectileWoodCost;
            ga.playSE(8);
        }
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
            int diagonalSpeed = (int) (speed / Math.sqrt(2));
            switch (direction) {
                case "up" ->
                    screenY -= speed;
                case "left_up" -> {
                    screenX -= diagonalSpeed;
                    screenY -= diagonalSpeed;
                }
                case "left" ->
                    screenX -= speed;
                case "left_down" -> {
                    screenX -= diagonalSpeed;
                    screenY += diagonalSpeed;
                }
                case "down" ->
                    screenY += speed;
                case "right_down" -> {
                    screenX += diagonalSpeed;
                    screenY += diagonalSpeed;
                }
                case "right" ->
                        screenX += speed;
                case "right_up" -> {
                    screenX += diagonalSpeed;
                    screenY -= diagonalSpeed;
                }
            }
            ((Rectangle)solidArea).setX(screenX - player.screenX + player.worldX);
            ((Rectangle)solidArea).setY(screenY - player.screenY + player.worldY);

            List<Integer> resourceIndices = ga.cChecker.checkResource(this);
            for (Integer index : resourceIndices) {
                ga.resource[index].takeDamage(attackValue);
                spriteNum = 4;
                attacking = false;
                screenX = player.screenX + 4 * ga.SCALE;
                screenY = player.screenY + 4 * ga.SCALE;
                ((Rectangle)solidArea).setX(screenX);
                ((Rectangle)solidArea).setY(screenY);
            }

            List<Integer> monsterIndices = ga.cChecker.checkMonster(this);
            for (Integer index : monsterIndices) {
                ga.greenSlime.get(index).greenSlimeDamage(attackValue);
                spriteNum = 4;
                attacking = false;
                screenX = player.screenX + 4 * ga.SCALE;
                screenY = player.screenY + 4 * ga.SCALE;
                ((Rectangle)solidArea).setX(screenX);
                ((Rectangle)solidArea).setY(screenY);
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
                    ((Rectangle)solidArea).setX(screenX);
                    ((Rectangle)solidArea).setY(screenY);
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        ++attackCount;
    }
    public void render(GraphicsContext gc) {
        Image image;
        image = images.get(-1 + spriteNum);
        if (attacking) {
            gc.drawImage(image, screenX, screenY);
            gc.strokeRect(screenX, screenY, 8 * ga.SCALE, 8 * ga.SCALE);
        }
    }
}
