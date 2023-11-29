package Combat;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import main.GameApplication;

import java.io.FileInputStream;

public class Scythe extends Entity implements Weapon {

    Player player;
    public Arc arc = new Arc();
    int screenX;
    int screenY;
    double mouseAngle;
    boolean attacking;
    int damage = 1;
    int attackCount;
    public Scythe(GameApplication ga, Player player) {
        this.ga = ga;
        this.player = player;
        attackValue = damage;
        attackSpeed = 60;
        attackCount = 60;
        arc.setCenterX(player.screenX + ga.TILE_SIZE / 2);
        arc.setCenterY(player.screenY + ga.TILE_SIZE / 2);
        arc.setRadiusX(ga.TILE_SIZE);
        arc.setRadiusY(ga.TILE_SIZE);
        arc.setLength(90);
        arc.setStroke(Color.BLUE);
        arc.setType(ArcType.ROUND);
        arc.setFill(Color.TRANSPARENT);
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
        setup("scythe_back_0");
        setup("scythe_back_1");
        setup("scythe_back_2");
        setup("scythe_back_idle");

        setup("scythe_front_0");
        setup("scythe_front_1");
        setup("scythe_front_2");
        setup("scythe_front_idle");

        setup("scythe_left_0");
        setup("scythe_left_1");
        setup("scythe_left_2");
        setup("scythe_left_idle");

        setup("scythe_right_0");
        setup("scythe_right_1");
        setup("scythe_right_2");
        setup("scythe_right_idle");

        setup("scythe_left_up_0");
        setup("scythe_left_up_1");
        setup("scythe_left_up_2");
        setup("scythe_left_up_idle");

        setup("scythe_left_down_0");
        setup("scythe_left_down_1");
        setup("scythe_left_down_2");
        setup("scythe_left_down_idle");

        setup("scythe_right_up_0");
        setup("scythe_right_up_1");
        setup("scythe_right_up_2");
        setup("scythe_right_up_idle");

        setup("scythe_right_down_0");
        setup("scythe_right_down_1");
        setup("scythe_right_down_2");
        setup("scythe_right_down_idle");
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

    public void update() {
        if (!attacking) {
            mouseAngle = Math.atan((ga.mouseY - arc.getCenterY()) / (ga.mouseX - arc.getCenterX())) * 180 / Math.PI;
            double yDiff = ga.mouseY - arc.getCenterY();
            double xDiff = ga.mouseX - arc.getCenterX();
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
                    arc.setStartAngle(45);
                    screenX = player.screenX;
                    screenY = player.screenY - ga.TILE_SIZE + ga.SCALE;
                }
                case "left_up" -> {
                    arc.setStartAngle(90);
                    screenX = player.screenX - ga.TILE_SIZE / 2;
                    screenY = player.screenY - ga.TILE_SIZE / 2;
                }
                case "left" -> {
                    arc.setStartAngle(135);
                    screenX = player.screenX - ga.TILE_SIZE + ga.SCALE;
                    screenY = player.screenY;
                }
                case "left_down" -> {
                    arc.setStartAngle(180);
                    screenX = player.screenX - ga.TILE_SIZE / 2;
                    screenY = player.screenY + ga.TILE_SIZE / 2;
                }
                case "down" -> {
                    arc.setStartAngle(225);
                    screenX = player.screenX;
                    screenY = player.screenY + ga.TILE_SIZE - ga.SCALE;
                }
                case "right_down" -> {
                    arc.setStartAngle(270);
                    screenX = player.screenX + ga.TILE_SIZE / 2;
                    screenY = player.screenY + ga.TILE_SIZE / 2;
                }
                case "right" -> {
                    arc.setStartAngle(315);
                    screenX = player.screenX + ga.TILE_SIZE - ga.SCALE;
                    screenY = player.screenY;
                }
                case "right_up" -> {
                    arc.setStartAngle(0);
                    screenX = player.screenX + ga.TILE_SIZE / 2;
                    screenY = player.screenY - ga.TILE_SIZE / 2;
                }
            }
        }

        // Swing Animation & Hit Detection
        if (attacking) {
            int monIndex = ga.cChecker.checkMonster(this);
            int resourceIndex = ga.cChecker.checkResource(this);

            if (monIndex != 999) {
                // ga.monsters[monIndex].takeDamage(damage);
            }
            if (resourceIndex != 999) {
                // ga.resources[resourceIndex].takeDamage();
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
            gcScythe.strokeArc(player.screenX - 24, player.screenY - 24, arc.getRadiusX() * 2, arc.getRadiusY() * 2, arc.getStartAngle(), arc.getLength(), ArcType.ROUND);
        }
    }
}
