package entity;

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

        setDefaultValues();
        getPlayerImage();
    }

    // Set Spawn, Speed, Direction
    public void setDefaultValues() {
        worldX = ga.TILE_SIZE * 23;
        worldY = ga.TILE_SIZE * 21;
        speed = 4;
        direction = "down";
    }

    // Loads Player Sprites
    public void getPlayerImage() {
        up1 = setup("farmer_back_1");
        up2 = setup("farmer_back_2");
        down1 = setup("farmer_front_1");
        down2 = setup("farmer_front_2");
        left1 = setup("farmer_left_1");
        left2 = setup("farmer_left_2");
        right1 = setup("farmer_right_1");
        right2 = setup("farmer_right_2");
        upIdle = setup("farmer_back_idle");
        downIdle = setup("farmer_front_idle");
        leftIdle = setup("farmer_left_idle");
        rightIdle = setup("farmer_right_idle");
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
                    direction = "up";
                    worldY -= diagonalSpeed;
                    worldX -= diagonalSpeed;
                } else if (KeyHandler.rightPressed) {
                    direction = "up";
                    worldY -= diagonalSpeed;
                    worldX += diagonalSpeed + 1; //adding 1 doesn't really fix the issue of the speed (it's still juust slightly off, but it's much better than before, only real fix would be to convert to double but that would cause a bunch of other errors.
                } else {
                    direction = "up";
                    worldY -= speed;
                }
            }   else if (KeyHandler.downPressed) {
                if (KeyHandler.leftPressed) {
                    direction = "down";
                    worldY += diagonalSpeed;
                    worldX -= diagonalSpeed;
                } else if (KeyHandler.rightPressed) {
                    direction = "down";
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
    }


    // Render Method
    public void render(GraphicsContext gc) {
        GraphicsContext gcPlayer = gc;
        ImageView image = null;

        // Sets Sprite Based on Direction and Animation Frame
        switch(direction) {
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
        }

        // Renders Player
        gcPlayer.drawImage(image.getImage(), screenX, screenY);

        // Renders Player Hit box
        gcPlayer.setStroke(Color.BLUE);
        gcPlayer.strokeRect(screenX + 8, screenY + 16, 32, 32);
    }
}
