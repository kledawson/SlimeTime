package entity;

import Combat.Scythe;
import Combat.Slingshot;
import com.almasb.fxgl.core.collection.Array;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GameApplication;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import object.OBJ_Gold;
import object.OBJ_Stone;
import object.OBJ_Wood;
import object.SuperObject;

import java.io.FileInputStream;
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

    public int bootsGoldCost = 1;
    public int bootsStoneCost = 1;
    public int bootsWoodCost = 1;

    public int meleeGoldCost = 1;
    public int meleeStoneCost = 1;
    public int meleeWoodCost = 1;

    public int armorGoldCost = 1;
    public int armorStoneCost = 1;
    public int armorWoodCost = 1;

    public int projectileGoldCost = 1;
    public int projectileStoneCost = 1;
    public int projectileWoodCost = 1;


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
        slingshot = new Slingshot(ga, this);

        setDefaultValues();
        getPlayerImage();
        setItems();
    }

    public void setItems() {
        //inventory.add()
        SuperObject gold = new OBJ_Gold(ga);
        SuperObject wood = new OBJ_Wood(ga);
        SuperObject stone = new OBJ_Stone(ga);
        wood.amount = 20;
        stone.amount = 20;
        gold.amount = 20;
        inventory.add(gold);
        inventory.add(wood);
        inventory.add(stone);
    }


    // Set Spawn, Speed, Direction
    public void setDefaultValues() {
        worldX = ga.TILE_SIZE * 64;
        worldY = ga.TILE_SIZE * 50;
        speed = 3;
        direction = "down";

        //Player stats
        level = 1;
        maxLife = 6;
        life = maxLife;
        exp = 0;
        nextLevelExp = 5;
    }
    // Loads Player Sprites
    public void getPlayerImage() {
        setup("farmer_back_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_back_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_back_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_front_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_front_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_front_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_left_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_left_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_left_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_right_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_right_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_right_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_left_up_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_left_up_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_left_up_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_left_down_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_left_down_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_left_down_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_right_up_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_right_up_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_right_up_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);

        setup("farmer_right_down_1", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_right_down_2", "player", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("farmer_right_down_idle", "player", ga.TILE_SIZE, ga.TILE_SIZE);
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
        ga.cChecker.checkResource(this);
        ga.cChecker.checkMonster(this);
        int objIndex = ga.cChecker.checkObject(this);
        pickUpObject(objIndex);

/*
        int resourceIndex = ga.cChecker.checkResource(this);*/


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
        slingshot.update();

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

public boolean hasRequiredItems(int goldCost, int stoneCost, int woodCost) {
        int goldIndex = searchItemInInventory("Gold");
        int stoneIndex = searchItemInInventory("Stone");
        int woodIndex = searchItemInInventory("Wood");

    return goldIndex != 999 && inventory.get(goldIndex).amount >= goldCost &&
            stoneIndex != 999 && inventory.get(stoneIndex).amount >= stoneCost &&
            woodIndex != 999 && inventory.get(woodIndex).amount >= woodCost;
}

    public void upgradeBoots() {

        int bootsGoldIncrease = 1;
        int bootsStoneIncrease = 1;
        int bootsWoodIncrease = 1;

        if (hasRequiredItems(bootsGoldCost, bootsStoneCost, bootsWoodCost)) {
            int goldIndex = searchItemInInventory("Gold");
            int stoneIndex = searchItemInInventory("Stone");
            int woodIndex = searchItemInInventory("Wood");

            inventory.get(goldIndex).amount -= bootsGoldCost;
            inventory.get(stoneIndex).amount -= bootsStoneCost;
            inventory.get(woodIndex).amount -= bootsWoodCost;

            // apply upgrade to player
            speed++;
            // Update the costs for the next upgrade
            bootsGoldCost += bootsGoldIncrease;
            bootsStoneCost += bootsStoneIncrease;
            bootsWoodCost += bootsWoodIncrease;
        }
    }

    public void upgradeMelee() {

        int meleeGoldIncrease = 1;
        int meleeStoneIncrease = 1;
        int meleeWoodIncrease = 1;

        // Check if player has required items in inventory
        if (hasRequiredItems(meleeGoldCost, meleeStoneCost, meleeWoodCost)) {
            int goldIndex = searchItemInInventory("Gold");
            int stoneIndex = searchItemInInventory("Stone");
            int woodIndex = searchItemInInventory("Wood");

            inventory.get(goldIndex).amount -= meleeGoldCost;
            inventory.get(stoneIndex).amount -= meleeStoneCost;
            inventory.get(woodIndex).amount -= meleeWoodCost;

            //apply upgrade
            scythe.attackValue++;
            scythe.attackSpeed -= 5;

            // Update the costs for the next upgrade
            meleeGoldCost += meleeGoldIncrease;
            meleeStoneCost += meleeStoneIncrease;
            meleeWoodCost += meleeWoodIncrease;
        }
    }

    public void upgradeArmor() {
        // Similar implementation as upgradeMelee()
        int armorGoldIncrease = 1;
        int armorStoneIncrease = 1;
        int armorWoodIncrease = 1;

        // Check if player has required items in inventory
        if (hasRequiredItems(armorGoldCost, armorStoneCost, armorWoodCost)) {
            int goldIndex = searchItemInInventory("Gold");
            int stoneIndex = searchItemInInventory("Stone");
            int woodIndex = searchItemInInventory("Wood");

            inventory.get(goldIndex).amount -= armorGoldCost;
            inventory.get(stoneIndex).amount -= armorStoneCost;
            inventory.get(woodIndex).amount -= armorWoodCost;

            //apply upgrade
            maxLife++;
            life++;

            // Update the costs for the next upgrade
            armorGoldCost += armorGoldIncrease;
            armorStoneCost += armorStoneIncrease;
            armorWoodCost += armorWoodIncrease;
        }
    }

    public void upgradeProjectile() {
        // Similar implementation as upgradeMelee()
        int projectileGoldIncrease = 1;
        int projectileStoneIncrease = 1;
        int projectileWoodIncrease = 1;

        // Check if player has required items in inventory
        if (hasRequiredItems(projectileGoldCost, projectileStoneCost, projectileWoodCost)) {
            int goldIndex = searchItemInInventory("Gold");
            int stoneIndex = searchItemInInventory("Stone");
            int woodIndex = searchItemInInventory("Wood");

            inventory.get(goldIndex).amount -= projectileGoldCost;
            inventory.get(stoneIndex).amount -= projectileStoneCost;
            inventory.get(woodIndex).amount -= projectileWoodCost;

            //apply upgrade
            slingshot.attackValue++;
            slingshot.speed += 2;

            // Update the costs for the next upgrade
            projectileGoldCost += projectileGoldIncrease;
            projectileStoneCost += projectileStoneIncrease;
            projectileWoodCost += projectileWoodIncrease;
        }
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
        //gcPlayer.setStroke(Color.TRANSPARENT);
        gcPlayer.strokeRect(screenX + 8, screenY + 16, 32, 32);
        scythe.render(gc);
        slingshot.render(gc);
    }

}
