package entity;

import Combat.Scythe;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GameApplication;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.security.Key;

public class Player extends Entity{
    GameApplication ga;
    KeyHandler keyH; // Key Handler to Deal with Movement and Potential other Key Presses
    public final int screenX; // Screen X-Coord
    public final int screenY; // Screen Y-Coord
    public Scythe scythe;

    public Player(GameApplication ga, KeyHandler keyH) {
        this.ga = ga;
        this.keyH = keyH;

        // Sets Screen Coords to Center of Screen
        screenX = ga.SCREEN_WIDTH / 2 - ga.TILE_SIZE / 2;
        screenY = ga.SCREEN_HEIGHT / 2 - ga.TILE_SIZE / 2;

        // Sets Hit box to be Smaller Than Sprite
        solidArea = new Rectangle();
        solidArea.setX(ga.TILE_SIZE / 6);
        solidArea.setY(ga.TILE_SIZE / 3);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
        solidArea.setWidth(ga.TILE_SIZE * 2 / 3);
        solidArea.setHeight(ga.TILE_SIZE * 2 / 3);

        scythe = new Scythe(ga, this);

        setDefaultValues();
        getPlayerImage();
    }

    // Set Spawn, Speed, Direction
    public void setDefaultValues() {
        worldX = ga.TILE_SIZE * 23;
        worldY = ga.TILE_SIZE * 21;
        speed = 4;
        direction = "down";

        //Player stats
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;       //more str -> more dmg
        dexterity = 1;      //more dex -> more dmg can be recieved
        exp = 0;
        nextLevelExp = 5;
        attack = getAttack();   //total atk determined by str and weapon
        defense = getDefense(); //total defense determined by dex and armor
    }

    public int getAttack() {
        return attack = strength;
    }

    public int getDefense() {
        return defense = dexterity; //if there will be an armor system, add in defense value here
    }
    // Loads Player Sprites
    public void getPlayerImage() {
        up1 = setup("farmer_back_1");
        up2 = setup("farmer_back_2");
        upIdle = setup("farmer_back_idle");

        down1 = setup("farmer_front_1");
        down2 = setup("farmer_front_2");
        downIdle = setup("farmer_front_idle");

        left1 = setup("farmer_left_1");
        left2 = setup("farmer_left_2");
        leftIdle = setup("farmer_left_idle");

        right1 = setup("farmer_right_1");
        right2 = setup("farmer_right_2");
        rightIdle = setup("farmer_right_idle");

        left_up1 = setup("farmer_left_up_1");
        left_up2 = setup("farmer_left_up_2");
        left_upIdle = setup("farmer_left_up_idle");

        left_down1 = setup("farmer_left_down_1");
        left_down2 = setup("farmer_left_down_2");
        left_downIdle = setup("farmer_left_down_idle");

        right_up1 = setup("farmer_right_up_1");
        right_up2 = setup("farmer_right_up_2");
        right_upIdle = setup("farmer_right_up_idle");

        right_down1 = setup("farmer_right_down_1");
        right_down2 = setup("farmer_right_down_2");
        right_downIdle = setup("farmer_right_down_idle");
    }

    // Utility Method for Loading Player Sprites
    public Image setup(String imageName) {
        try {
            return new Image(new FileInputStream("Slime_Time/res/player/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
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

    // Update Method
    public void update() {
        // Sets Direction Based on Key Press
        int tempWorldX = worldX;
        int tempWorldY = worldY;

        double diagonalSpeed = speed / Math.sqrt(2); // Calculate diagonal speed
        if (KeyHandler.upPressed || KeyHandler.downPressed || KeyHandler.leftPressed || KeyHandler.rightPressed) {
            if (KeyHandler.upPressed) {
                if (KeyHandler.leftPressed) {
                    direction = "left_up";
                    worldY -= diagonalSpeed;
                    worldX -= diagonalSpeed;
                } else if (KeyHandler.rightPressed) {
                    direction = "right_up";
                    worldY -= diagonalSpeed;
                    worldX += diagonalSpeed + 1; //adding 1 doesn't really fix the issue of the speed (it's still just slightly off, but it's much better than before, only real fix would be to convert to double but that would cause a bunch of other errors.
                } else {
                    direction = "up";
                    worldY -= speed;
                }
            }   else if (KeyHandler.downPressed) {
                if (KeyHandler.leftPressed) {
                    direction = "left_down";
                    worldY += diagonalSpeed;
                    worldX -= diagonalSpeed;
                } else if (KeyHandler.rightPressed) {
                    direction = "right_down";
                    worldY += diagonalSpeed;
                    worldX += diagonalSpeed + 1;
                } else {
                    direction = "down";
                    worldY += speed;
                }
            } else if (KeyHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (KeyHandler.rightPressed) {
                direction = "right";
                worldX += speed;
            }
        }

        // Checks Collision
        collisionOn = false;
        ga.cChecker.checkTile(this);
        int objIndex = ga.cChecker.checkObject(this, true);

        // Stops Player if Collision is On
        if (collisionOn) {
            worldX = tempWorldX;
            worldY = tempWorldY;
        }

        // Walking Animation
        ++spriteCounter;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // Resets Animation Timer and Sets Sprite to Idle
        if (!KeyHandler.upPressed && !KeyHandler.downPressed && !KeyHandler.leftPressed && !KeyHandler.rightPressed) {
            spriteNum = 3;
            spriteCounter = 0;
        }

        // Weapon
        scythe.update();
    }


    // Render Method
    public void render(GraphicsContext gc) {
        GraphicsContext gcPlayer = gc;
        ImageView image = null;

        // Sets Sprite Based on Direction and Animation Frame
        switch(scythe.direction) {
            case "up" -> {
                if (spriteNum == 1) image = new ImageView(up1);
                if (spriteNum == 2) image = new ImageView(up2);
                if (spriteNum == 3) image = new ImageView(upIdle);
            }
            case "down" -> {
                if (spriteNum == 1) image = new ImageView(down1);
                if (spriteNum == 2) image = new ImageView(down2);
                if (spriteNum == 3) image = new ImageView(downIdle);
            }
            case "left" -> {
                if (spriteNum == 1) image = new ImageView(left1);
                if (spriteNum == 2) image = new ImageView(left2);
                if (spriteNum == 3) image = new ImageView(leftIdle);
            }
            case "right" -> {
                if (spriteNum == 1) image = new ImageView(right1);
                if (spriteNum == 2) image = new ImageView(right2);
                if (spriteNum == 3) image = new ImageView(rightIdle);
            }
            case "left_down" -> {
                if (spriteNum == 1) image = new ImageView(left_down1);
                if (spriteNum == 2) image = new ImageView(left_down2);
                if (spriteNum == 3) image = new ImageView(left_downIdle);
            }
            case "left_up" -> {
                if (spriteNum == 1) image = new ImageView(left_up1);
                if (spriteNum == 2) image = new ImageView(left_up2);
                if (spriteNum == 3) image = new ImageView(left_upIdle);
            }
            case "right_down" -> {
                if (spriteNum == 1) image = new ImageView(right_down1);
                if (spriteNum == 2) image = new ImageView(right_down2);
                if (spriteNum == 3) image = new ImageView(right_downIdle);
            }
            case "right_up" -> {
                if (spriteNum == 1) image = new ImageView(right_up1);
                if (spriteNum == 2) image = new ImageView(right_up2);
                if (spriteNum == 3) image = new ImageView(right_upIdle);
            }
        }

        // Renders Player
        gcPlayer.drawImage(image.getImage(), screenX, screenY);

        // Renders Player Hit box
        gcPlayer.setStroke(Color.BLUE);
        gcPlayer.strokeRect(screenX + 8, screenY + 16, 32, 32);
        scythe.render(gc);
    }
}
