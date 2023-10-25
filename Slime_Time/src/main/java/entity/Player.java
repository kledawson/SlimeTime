package entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GameApplication;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;

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
        up1 = setup("image_name");
        up2 = setup("image_name");
        down1 = setup("image_name");
        down2 = setup("image_name");
        left1 = setup("image_name");
        left2 = setup("image_name");
        right1 = setup("image_name");
        right2 = setup("image_name");
        upIdle = setup("image_name");
        downIdle = setup("image_name");
        leftIdle = setup("image_name");
        rightIdle = setup("image_name");
    }

    // Utility Method for Loading Player Sprites
    public Image setup(String imageName) {
        try {
            return new Image(new FileInputStream("res\\player\\" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
        }
        catch (Exception e) {
            try {
                return new Image(new FileInputStream("res\\tiles\\no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
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
        if (KeyHandler.upPressed || KeyHandler.downPressed || KeyHandler.leftPressed || KeyHandler.rightPressed) {
            if (KeyHandler.upPressed) {
                direction = "up";
            } else if (KeyHandler.downPressed) {
                direction = "down";
            } else if (KeyHandler.leftPressed) {
                direction = "left";
            } else if (KeyHandler.rightPressed) {
                direction = "right";
            }

            // Checks Collision
            collisionOn = false;
            ga.cChecker.checkTile(this);
            int objIndex = ga.cChecker.checkObject(this, true);

            // Stops Player if Collision is On
            if(!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" ->  worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
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
        }
        // Resets Animation Timer and Sets Sprite to Idle
        else {
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
