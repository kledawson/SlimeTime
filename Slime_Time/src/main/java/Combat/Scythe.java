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

    GameApplication ga;
    Player player;
    public Arc arc = new Arc();
    int screenX;
    int screenY;
    double mouseAngle;
    boolean attacking;

    int damage;
    int attackSpeed;

    public Scythe(GameApplication ga, Player player) {
        this.ga = ga;
        this.player = player;
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
        attacking = true;
    }

    @Override
    public void upgrade() {

    }

    public void getWeaponImage() {
        up0 = setup("scythe_back_0");
        up1 = setup("scythe_back_1");
        up2 = setup("scythe_back_2");
        upIdle = setup("scythe_back_idle");

        down0 = setup("scythe_front_0");
        down1 = setup("scythe_front_1");
        down2 = setup("scythe_front_2");
        downIdle = setup("scythe_front_idle");

        left0 = setup("scythe_left_0");
        left1 = setup("scythe_left_1");
        left2 = setup("scythe_left_2");
        leftIdle = setup("scythe_left_idle");

        right0 = setup("scythe_right_0");
        right1 = setup("scythe_right_1");
        right2 = setup("scythe_right_2");
        rightIdle = setup("scythe_right_idle");

        left_up0 = setup("scythe_left_up_0");
        left_up1 = setup("scythe_left_up_1");
        left_up2 = setup("scythe_left_up_2");
        left_upIdle = setup("scythe_left_up_idle");

        left_down0 = setup("scythe_left_down_0");
        left_down1 = setup("scythe_left_down_1");
        left_down2 = setup("scythe_left_down_2");
        left_downIdle = setup("scythe_left_down_idle");

        right_up0 = setup("scythe_right_up_0");
        right_up1 = setup("scythe_right_up_1");
        right_up2 = setup("scythe_right_up_2");
        right_upIdle = setup("scythe_right_up_idle");

        right_down0 = setup("scythe_right_down_0");
        right_down1 = setup("scythe_right_down_1");
        right_down2 = setup("scythe_right_down_2");
        right_downIdle = setup("scythe_right_down_idle");
    }

    public Image setup(String imageName) {
        try {
            return new Image(new FileInputStream("Slime_Time/res/weapon/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
        }
        catch (Exception e) {
            try {
                return new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
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

        // Swing Animation
        if (attacking) {
            ++spriteCounter;
            if (spriteCounter > 5) {
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
    }

    public void render(GraphicsContext gc) {
        GraphicsContext gcScythe = gc;
        ImageView image = null;

        switch(direction) {
            case "up" -> {
                if (spriteNum == 1) image = new ImageView(up0);
                if (spriteNum == 2) image = new ImageView(up1);
                if (spriteNum == 3) image = new ImageView(up2);
                if (spriteNum == 4) image = new ImageView(upIdle);
            }
            case "down" -> {
                if (spriteNum == 1) image = new ImageView(down0);
                if (spriteNum == 2) image = new ImageView(down1);
                if (spriteNum == 3) image = new ImageView(down2);
                if (spriteNum == 4) image = new ImageView(downIdle);
            }
            case "left" -> {
                if (spriteNum == 1) image = new ImageView(left0);
                if (spriteNum == 2) image = new ImageView(left1);
                if (spriteNum == 3) image = new ImageView(left2);
                if (spriteNum == 4) image = new ImageView(leftIdle);
            }
            case "right" -> {
                if (spriteNum == 1) image = new ImageView(right0);
                if (spriteNum == 2) image = new ImageView(right1);
                if (spriteNum == 3) image = new ImageView(right2);
                if (spriteNum == 4) image = new ImageView(rightIdle);
            }
            case "left_down" -> {
                if (spriteNum == 1) image = new ImageView(left_down0);
                if (spriteNum == 2) image = new ImageView(left_down1);
                if (spriteNum == 3) image = new ImageView(left_down2);
                if (spriteNum == 4) image = new ImageView(left_downIdle);
            }
            case "left_up" -> {
                if (spriteNum == 1) image = new ImageView(left_up0);
                if (spriteNum == 2) image = new ImageView(left_up1);
                if (spriteNum == 3) image = new ImageView(left_up2);
                if (spriteNum == 4) image = new ImageView(left_upIdle);
            }
            case "right_down" -> {
                if (spriteNum == 1) image = new ImageView(right_down0);
                if (spriteNum == 2) image = new ImageView(right_down1);
                if (spriteNum == 3) image = new ImageView(right_down2);
                if (spriteNum == 4) image = new ImageView(right_downIdle);
            }
            case "right_up" -> {
                if (spriteNum == 1) image = new ImageView(right_up0);
                if (spriteNum == 2) image = new ImageView(right_up1);
                if (spriteNum == 3) image = new ImageView(right_up2);
                if (spriteNum == 4) image = new ImageView(right_upIdle);
            }
        }
        if (attacking) {
            gcScythe.drawImage(image.getImage(), screenX, screenY);
            gcScythe.strokeArc(player.screenX - 24, player.screenY - 24, arc.getRadiusX() * 2, arc.getRadiusY() * 2, arc.getStartAngle(), arc.getLength(), ArcType.ROUND);
        }
    }
}
