package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import main.GameApplication;
import java.util.ArrayList;
import java.util.List;

public class Scythe extends Entity implements Weapon {
    Player player;
    int screenX;
    int screenY;
    double mouseAngle;
    boolean attacking;
    int damage = 1;
    int attackCount;
    int centerX;
    int centerY;
    List<Polygon> scytheSolidAreas = new ArrayList<>();
    public Scythe(GameApplication ga, Player player) {
        super(ga);
        attacking = false;
        this.player = player;
        solidArea = new Polygon();
        attackValue = damage;
        attackSpeed = 60;
        attackCount = 60;
        centerX = player.screenX + ga.TILE_SIZE / 2;
        centerY = player.screenY + ga.TILE_SIZE / 2;
        solidArea.setStroke(Color.BLUE);
        solidArea.setFill(Color.TRANSPARENT);
        getWeaponImage();
        getScytheSolidAreas();
    }

    private void getScytheSolidAreas() {
        for (int i = 0; i < 6; ++i) {
            scytheSolidAreas.add(new Polygon());
        }
        scytheSolidAreas.get(0).getPoints().addAll(
                0.0, 0.0,
                0.0, 30.0,
                48.0, 0.0,
                48.0, 30.0
        );
        scytheSolidAreas.get(1).getPoints().addAll(
                0.0, 0.0,
                0.0, 48.0,
                30.0, 0.0,
                30.0, 48.0
        );
        scytheSolidAreas.get(2).getPoints().addAll(
                0.0, 0.0,
                0.0, 45.0,
                24.0, 24.0,
                24.0, 45.0,
                45.0, 0.0,
                45.0, 24.0
        );
        scytheSolidAreas.get(3).getPoints().addAll(
                0.0, 0.0,
                0.0, 45.0,
                24.0, 0.0,
                24.0, 21.0,
                45.0, 21.0,
                45.0, 45.0
        );
        scytheSolidAreas.get(4).getPoints().addAll(
                0.0, 21.0,
                0.0, 45.0,
                21.0, 0.0,
                21.0, 21.0,
                45.0, 0.0,
                45.0, 45.0
        );
        scytheSolidAreas.get(5).getPoints().addAll(
                0.0, 0.0,
                0.0, 24.0,
                21.0, 24.0,
                21.0, 45.0,
                45.0, 0.0,
                45.0, 45.0
        );
    }
    private void getWeaponImage() {
        setup("scythe_back_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_back_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_back_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_back_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_front_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_front_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_front_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_front_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_left_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_right_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_left_up_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_up_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_up_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_up_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_left_down_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_down_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_down_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_left_down_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_right_up_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_up_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_up_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_up_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("scythe_right_down_0", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_down_1", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_down_2", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("scythe_right_down_idle", "weapon", ga.TILE_SIZE, ga.TILE_SIZE);
    }
    @Override
    public void attack() {
        if (attackCount >= attackSpeed) {
            attacking = true;
            attackCount = 0;
            ga.playSE(3);
        }
    }
    @Override
    public void upgrade() {
        if (player.hasRequiredItems(player.meleeCost)) {
            int goldIndex = player.searchItemInInventory("Gold");
            int stoneIndex = player.searchItemInInventory("Stone");
            int woodIndex = player.searchItemInInventory("Wood");

            player.inventory.get(goldIndex).amount -= player.meleeCost;
            player.inventory.get(stoneIndex).amount -= player.meleeCost;
            player.inventory.get(woodIndex).amount -= player.meleeCost;

            //apply upgrade
            ++attackValue;
            attackSpeed = (int) (attackSpeed * 2.0 / 3);

            // Update the costs for the next upgrade
            ++player.meleeCost;
            ga.playSE(8);
        }
    }
    public void update() {
        if (!attacking) {
            mouseAngle = Math.atan((ga.mouseY - centerY) / (ga.mouseX - centerX)) * 180 / Math.PI;
            double yDiff = ga.mouseY - centerY;
            double xDiff = ga.mouseX - centerX;
            if (yDiff <= 0 && mouseAngle > -67.5 && mouseAngle <= -22.5) {
                direction = "right_up";
            } else if (yDiff <= 0 && (mouseAngle > 67.5 || mouseAngle <= -67.5)) {
                direction = "up";
            } else if (yDiff <= 0 && mouseAngle > 22.5) {
                direction = "left_up";
            } else if (xDiff <= 0 && mouseAngle > -22.5 && mouseAngle <= 22.5) {
                direction = "left";
            } else if (mouseAngle > -67.5 && mouseAngle <= -22.5) {
                direction = "left_down";
            } else if (mouseAngle > 67.5 || mouseAngle <= -67.5) {
                direction = "down";
            } else if (mouseAngle > 22.5) {
                direction = "right_down";
            } else if (mouseAngle > -22.5) {
                direction = "right";
            }

            switch (direction) {
                case "up" -> {
                    solidArea = scytheSolidAreas.get(0);
                    solidArea.setTranslateX(player.worldX);
                    solidArea.setTranslateY(player.worldY - 9 * ga.SCALE);
                    screenX = player.screenX;
                    screenY = player.screenY - ga.TILE_SIZE + ga.SCALE;
                }
                case "left_up" -> {
                    solidArea = scytheSolidAreas.get(2);
                    solidArea.setTranslateX(player.worldX - 7 * ga.SCALE);
                    solidArea.setTranslateY(player.worldY - 7 * ga.SCALE);
                    screenX = player.screenX - ga.TILE_SIZE / 2;
                    screenY = player.screenY - ga.TILE_SIZE / 2;
                }
                case "left" -> {
                    solidArea = scytheSolidAreas.get(1);
                    solidArea.setTranslateX(player.worldX - 9 * ga.SCALE);
                    solidArea.setTranslateY(player.worldY);
                    screenX = player.screenX - ga.TILE_SIZE + ga.SCALE;
                    screenY = player.screenY;
                }
                case "left_down" -> {
                    solidArea = scytheSolidAreas.get(3);
                    solidArea.setTranslateX(player.worldX - 7 * ga.SCALE);
                    solidArea.setTranslateY(player.worldY + 8 * ga.SCALE);
                    screenX = player.screenX - ga.TILE_SIZE / 2;
                    screenY = player.screenY + ga.TILE_SIZE / 2;
                }
                case "down" -> {
                    solidArea = scytheSolidAreas.get(0);
                    solidArea.setTranslateX(player.worldX);
                    solidArea.setTranslateY(player.worldY - ga.SCALE + ga.TILE_SIZE);
                    screenX = player.screenX;
                    screenY = player.screenY + ga.TILE_SIZE - ga.SCALE;
                }
                case "right_down" -> {
                    solidArea = scytheSolidAreas.get(4);
                    solidArea.setTranslateX(player.worldX + 8 * ga.SCALE);
                    solidArea.setTranslateY(player.worldY + 8 * ga.SCALE);
                    screenX = player.screenX + ga.TILE_SIZE / 2;
                    screenY = player.screenY + ga.TILE_SIZE / 2;
                }
                case "right" -> {
                    solidArea = scytheSolidAreas.get(1);
                    solidArea.setTranslateX(player.worldX - ga.SCALE + ga.TILE_SIZE);
                    solidArea.setTranslateY(player.worldY);
                    screenX = player.screenX + ga.TILE_SIZE - ga.SCALE;
                    screenY = player.screenY;
                }
                case "right_up" -> {
                    solidArea = scytheSolidAreas.get(5);
                    solidArea.setTranslateX(player.worldX + 8 * ga.SCALE);
                    solidArea.setTranslateY(player.worldY - 7 * ga.SCALE);
                    screenX = player.screenX + ga.TILE_SIZE / 2;
                    screenY = player.screenY - ga.TILE_SIZE / 2;
                }
            }
        }

        // Swing Animation & Hit Detection
        if (attacking) {
            //setting up the player to be able to attack monsters and resources
            List<Integer> resourceIndices = ga.cChecker.checkResource(this);
            for (Integer index : resourceIndices) {
                ga.resource[index].takeDamage(attackValue);
            }

            List<Integer> monsterIndices = ga.cChecker.checkMonster(this);
            for (Integer index : monsterIndices) {
                ga.greenSlime.get(index).greenSlimeDamage(attackValue);
            }

            ++spriteCounter;
            if (spriteCounter >= 5) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                    attacking = false;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }



        ++attackCount;
    }
    public void render(GraphicsContext gc) {
        Image image = null;
        if (attacking) {
            switch(direction) {
                case "up" ->
                    image = images.get(-1 + spriteNum);

                case "down" ->
                    image = images.get(3 + spriteNum);

                case "left" ->
                    image = images.get(7 + spriteNum);

                case "right" ->
                    image = images.get(11 + spriteNum);

                case "left_up" ->
                    image = images.get(15 + spriteNum);

                case "left_down" ->
                    image = images.get(19 + spriteNum);

                case "right_up" ->
                    image = images.get(23 + spriteNum);

                case "right_down" ->
                    image = images.get(27 + spriteNum);

            }

            gc.drawImage(image, screenX, screenY);
        }
    }
}
