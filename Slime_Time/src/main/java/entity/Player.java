package entity;

import Combat.Scythe;
import Combat.Slingshot;
import com.almasb.fxgl.core.collection.Array;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GameApplication;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import object.OBJ_Key;
import object.SuperObject;

import java.io.FileInputStream;
import java.security.Key;
import java.util.ArrayList;

public class Player extends Entity{
    GameApplication ga;
    KeyHandler keyH; // Key Handler to Deal with Movement and Potential other Key Presses
    public final int screenX; // Screen X-Coord
    public final int screenY; // Screen Y-Coord
    public Scythe scythe;
    public Slingshot slingshot;
    public ArrayList<SuperObject> inventory = new ArrayList<>();
    public final int maxInventorySize = 8; //subject to change later


    public Player(GameApplication ga, KeyHandler keyH) {
        this.ga = ga;
        this.keyH = keyH;

        // Sets Screen Coords to Center of Screen
        screenX = ga.SCREEN_WIDTH / 2 - ga.TILE_SIZE / 2;
        screenY = ga.SCREEN_HEIGHT / 2 - ga.TILE_SIZE / 2;

        // Sets Hit box to be Smaller Than Sprite
        solidArea = new Rectangle();
        solidArea.setX(ga.TILE_SIZE / 6 + worldX);
        solidArea.setY(ga.TILE_SIZE / 3 + worldY);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
        solidArea.setWidth(ga.TILE_SIZE * 2 / 3);
        solidArea.setHeight(ga.TILE_SIZE * 2 / 3);

        scythe = new Scythe(ga, this);

        setDefaultValues();
        getPlayerImage();
        setItems();
    }

    public void setItems() {
        //inventory.add()

    }


    // Set Spawn, Speed, Direction
    public void setDefaultValues() {
        worldX = ga.TILE_SIZE * 64;
        worldY = ga.TILE_SIZE * 50;
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
        setup("farmer_back_1");
        setup("farmer_back_2");
        setup("farmer_back_idle");

        setup("farmer_front_1");
        setup("farmer_front_2");
        setup("farmer_front_idle");

        setup("farmer_left_1");
        setup("farmer_left_2");
        setup("farmer_left_idle");

        setup("farmer_right_1");
        setup("farmer_right_2");
        setup("farmer_right_idle");

        setup("farmer_left_up_1");
        setup("farmer_left_up_2");
        setup("farmer_left_up_idle");

        setup("farmer_left_down_1");
        setup("farmer_left_down_2");
        setup("farmer_left_down_idle");

        setup("farmer_right_up_1");
        setup("farmer_right_up_2");
        setup("farmer_right_up_idle");

        setup("farmer_right_down_1");
        setup("farmer_right_down_2");
        setup("farmer_right_down_idle");
    }

    // Utility Method for Loading Player Sprites
    public void setup(String imageName) {
        try {
            images.add(new Image(new FileInputStream("Slime_Time/res/player/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
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

        // Changes Hitbox Coordinates
        solidArea.setX(ga.TILE_SIZE / 6 + worldX);
        solidArea.setY(ga.TILE_SIZE / 3 + worldY);

        // Checks Collision
        collisionOn = false;
        ga.cChecker.checkTile(this);
        int objIndex = ga.cChecker.checkObject(this);
        pickUpObject(objIndex);


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
    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            //pickup only

            String text;
            if (canObtainItem(ga.obj[i])) {
                text = ga.obj[i].name + "acquired!";
            }
            else {
                text = "You cannot carry anymore";
            }
            // ga.ui.addMessage(text); ->  implement later
            ga.obj[i] = null;
        }
    }
    public boolean canObtainItem(SuperObject item) {
        boolean canObtain = false;
        //check if stackable
        if (item.stackable) {
            int index = searchItemInInventory(item.name);
            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }
            else { //New Item to check vacancy
                if(inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else { //not stackable
            if(inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }


    // Render Method
    public void render(GraphicsContext gc) {
        GraphicsContext gcPlayer = gc;
        Image image = null;

        // Sets Sprite Based on Direction and Animation Frame
        switch(scythe.direction) {
            case "up" -> {
                image = images.get(-1 + spriteNum);
            }
            case "down" -> {
                image = images.get(2 + spriteNum);
            }
            case "left" -> {
                image = images.get(5 + spriteNum);
            }
            case "right" -> {
                image = images.get(8 + spriteNum);
            }
            case "left_up" -> {
                image = images.get(11 + spriteNum);
            }
            case "left_down" -> {
                image = images.get(14 + spriteNum);
            }
            case "right_up" -> {
                image = images.get(17 + spriteNum);
            }
            case "right_down" -> {
                image = images.get(20 + spriteNum);
            }

        }

        // Renders Player
        gcPlayer.drawImage(image, screenX, screenY);

        // Renders Player Hit box
        gcPlayer.setStroke(Color.BLUE);
        gcPlayer.strokeRect(screenX + 8, screenY + 16, 32, 32);
        scythe.render(gc);
    }
}
