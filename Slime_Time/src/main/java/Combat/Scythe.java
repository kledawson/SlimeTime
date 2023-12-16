package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

import java.io.FileInputStream;

public class Scythe extends Entity implements Weapon {
    Player player;
    int screenX;
    int screenY;
    double mouseAngle;
    boolean attacking;
    int damage = 1;
    int attackCount;
    public Scythe(GameApplication ga, Player player) {
        super(ga);
        this.player = player;
        solidArea = new Arc();
        attackValue = damage;
        attackSpeed = 60;
        attackCount = 60;
        ((Arc)solidArea).setCenterX(player.screenX + ga.TILE_SIZE / 2);
        ((Arc)solidArea).setCenterY(player.screenY + ga.TILE_SIZE / 2);
        ((Arc)solidArea).setRadiusX(ga.TILE_SIZE);
        ((Arc)solidArea).setRadiusY(ga.TILE_SIZE);
        ((Arc)solidArea).setLength(90);
        ((Arc)solidArea).setType(ArcType.ROUND);
        solidArea.setStroke(Color.BLUE);
        solidArea.setFill(Color.TRANSPARENT);
        getWeaponImage();
    }

    @Override
    public void attack() {
        if (attackCount >= attackSpeed) {
            attacking = true;
            attackCount = 0;
        }
    }

    @Override
    public void upgrade() {

    }

    public void getWeaponImage() {
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

    public void update() {
        if (!attacking) {
            mouseAngle = Math.atan((ga.mouseY - ((Arc)solidArea).getCenterY()) / (ga.mouseX - ((Arc)solidArea).getCenterX())) * 180 / Math.PI;
            double yDiff = ga.mouseY - ((Arc)solidArea).getCenterY();
            double xDiff = ga.mouseX - ((Arc)solidArea).getCenterX();
            if (yDiff <= 0 && mouseAngle > -67.5 && mouseAngle <= -22.5) {
                direction = "right_up";
            } else if (yDiff <= 0 && (mouseAngle > 67.5 || mouseAngle <= -67.5)) {
                direction = "up";
            } else if (yDiff <= 0 && mouseAngle > 22.5 && mouseAngle <= 67.5) {
                direction = "left_up";
            } else if (xDiff <= 0 && mouseAngle > -22.5 && mouseAngle <= 22.5) {
                direction = "left";
            } else if (mouseAngle > -67.5 && mouseAngle <= -22.5) {
                direction = "left_down";
            } else if (mouseAngle > 67.5 || mouseAngle <= -67.5) {
                direction = "down";
            } else if (mouseAngle > 22.5 && mouseAngle <= 67.5) {
                direction = "right_down";
            } else if (mouseAngle > -22.5 && mouseAngle <= 22.5) {
                direction = "right";
            }

            switch (direction) {
                case "up" -> {
                    ((Arc)solidArea).setStartAngle(45);
                    screenX = player.screenX;
                    screenY = player.screenY - ga.TILE_SIZE + ga.SCALE;
                }
                case "left_up" -> {
                    ((Arc)solidArea).setStartAngle(90);
                    screenX = player.screenX - ga.TILE_SIZE / 2;
                    screenY = player.screenY - ga.TILE_SIZE / 2;
                }
                case "left" -> {
                    ((Arc)solidArea).setStartAngle(135);
                    screenX = player.screenX - ga.TILE_SIZE + ga.SCALE;
                    screenY = player.screenY;
                }
                case "left_down" -> {
                    ((Arc)solidArea).setStartAngle(180);
                    screenX = player.screenX - ga.TILE_SIZE / 2;
                    screenY = player.screenY + ga.TILE_SIZE / 2;
                }
                case "down" -> {
                    ((Arc)solidArea).setStartAngle(225);
                    screenX = player.screenX;
                    screenY = player.screenY + ga.TILE_SIZE - ga.SCALE;
                }
                case "right_down" -> {
                    ((Arc)solidArea).setStartAngle(270);
                    screenX = player.screenX + ga.TILE_SIZE / 2;
                    screenY = player.screenY + ga.TILE_SIZE / 2;
                }
                case "right" -> {
                    ((Arc)solidArea).setStartAngle(315);
                    screenX = player.screenX + ga.TILE_SIZE - ga.SCALE;
                    screenY = player.screenY;
                }
                case "right_up" -> {
                    ((Arc)solidArea).setStartAngle(0);
                    screenX = player.screenX + ga.TILE_SIZE / 2;
                    screenY = player.screenY - ga.TILE_SIZE / 2;
                }
            }
        }

//        solidArea.setLayoutX((screenX + ga.player.worldX - ga.player.screenX) - solidArea.getLayoutBounds().getMinX());
//        solidArea.setLayoutY((screenY + ga.player.worldY - ga.player.screenY) - solidArea.getLayoutBounds().getMinY());

        solidArea.setLayoutX((ga.player.worldX + 24) - solidArea.getLayoutBounds().getMinX());
        solidArea.setLayoutY((ga.player.worldY + 24) - solidArea.getLayoutBounds().getMinY());

        // Swing Animation & Hit Detection
        if (attacking) {
//            int monIndex = ga.cChecker.checkMonster(this);

//
//            if (monIndex != 999) {
//                // ga.monsters[monIndex].takeDamage(damage);
//            }

            ++spriteCounter;
            if (spriteCounter >= 5) {
                if (spriteNum == 1) {
                    int resourceIndex = ga.cChecker.checkResource(this);
                    if (resourceIndex != 999) {
                        ga.resource[resourceIndex].takeDamage();
                    }
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
        GraphicsContext gcScythe = gc;
        Image image = null;

        switch(direction) {
            case "up" -> {
                image = images.get(-1 + spriteNum);
            }
            case "down" -> {
                image = images.get(3 + spriteNum);
            }
            case "left" -> {
                image = images.get(7 + spriteNum);
            }
            case "right" -> {
                image = images.get(11 + spriteNum);
            }
            case "left_up" -> {
                image = images.get(15 + spriteNum);
            }
            case "left_down" -> {
                image = images.get(19 + spriteNum);
            }
            case "right_up" -> {
                image = images.get(23 + spriteNum);
            }
            case "right_down" -> {
                image = images.get(27 + spriteNum);
            }
        }
        if (attacking) {
            gcScythe.drawImage(image, screenX, screenY);
            gcScythe.strokeArc(player.screenX - 24, player.screenY - 24,
                    ((Arc)solidArea).getRadiusX() * 2, ((Arc)solidArea).getRadiusY() * 2,
                    ((Arc)solidArea).getStartAngle(), ((Arc)solidArea).getLength(), ArcType.ROUND);
        }
        gcScythe.strokeRect((ga.player.worldX + 24) - solidArea.getLayoutBounds().getMinX(),
                (ga.player.worldY + 24) - solidArea.getLayoutBounds().getMinY(),
                ga.TILE_SIZE, ga.TILE_SIZE);
    }
}
