package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import javafx.scene.control.*;
import object.SuperObject;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// Handles Additional UI on top of Scene
public class UI {
    GameApplication ga;
    GraphicsContext gc;

    Font arial_40, arial_80B;
    //    ImageView keyImage;
    public List<Image> images = new ArrayList<>();
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public boolean showUpgradeScreen = false;
    double playTime;
    DecimalFormat dFormat  = new DecimalFormat("#0.00");

    // Variables to store upgrade costs

//    Button upgradeMeleeButton; // Declare the buttons
//    Button upgradeArmorButton;
//    Button upgradeProjectileButton;
//    Button upgradeBootsButton;

    public UI(GameApplication ga) {
        this.ga = ga;
        arial_40 = new Font("Arial", 40); // example font
        getUIImage();
    }

    public void getUIImage() {
        setup("upgrade_menu", ga.SCREEN_WIDTH * 3 / 4, ga.SCREEN_HEIGHT * 3 / 4);
        setup("button_image", 144, 72);
        setup("boots_icon", 144, 144);
        setup("melee_icon", 144, 144);
        setup("ranged_icon", 144, 144);
        setup("heart_icon", 144, 144);
    }

    public void setup(String imageName, double width, double height) {
        try {
            images.add(new Image(new FileInputStream("Slime_Time/res/ui/" + imageName + ".png"), width, height, false, false));
        }
        catch (Exception e) {
            try {
                images.add(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), width, height, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void render(GraphicsContext gc) {
        this.gc = gc;
        gc.setFont(arial_40);
        gc.setFill(Color.WHITE);

        if (ga.gameState == ga.playState) {
            // UI in playState
        }
        if (ga.gameState == ga.pauseState) {
            renderPauseScreen();
        }
        if (ga.gameState == ga.characterState) {
            renderCharacterScreen();
            renderInventory();
        }
//        if (showUpgradeScreen) {
//            renderUpgradeScreen();
//        }
        if (ga.upgradeBootsButton.isVisible()) {
            renderUpgradeScreen();
        }
    }
    public void renderPauseScreen() {
        gc.setFont(gc.getFont().font(80));
        String text = "PAUSED";
        int x = ga.SCREEN_WIDTH / 2;
        int y = ga.SCREEN_HEIGHT / 2;
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, x, y);
    }


    public void renderCharacterScreen() {
        //RECTANGLE
        double rectangleWidth = ga.SCREEN_WIDTH2 - 538;
        gc.setFill(Color.BLACK);
        gc.setFill((Color.rgb(0,0,0,0.8)));
        gc.fillRoundRect(25,45,rectangleWidth, ga.SCREEN_HEIGHT2 - 250, 30, 20);
        gc.setStroke((Color.WHITE));
        gc.setLineWidth(2);
        gc.strokeRoundRect(25,45, rectangleWidth, ga.SCREEN_HEIGHT2 - 250, 20, 20);

        //TEXT
        int yStart = 120;
        int lineHeight = 50;
        int valueOffset = 200;

        gc.setFont(new Font("Ariel", 23));
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.LEFT);


        gc.fillText("Level:", 50, yStart);
        gc.fillText(Integer.toString(ga.player.level), valueOffset, yStart);

        gc.fillText("Life:", 50, yStart + lineHeight);
        gc.fillText(ga.player.life + "/" + ga.player.maxLife, valueOffset, yStart + lineHeight);

        gc.fillText("Melee Atk:", 50, yStart + 2 * lineHeight);
        gc.fillText(Integer.toString(ga.player.scythe.attackValue), valueOffset, yStart + 2 * lineHeight);

        gc.fillText("Projectile Atk:", 50, yStart + 3 * lineHeight);
        gc.fillText(Integer.toString(ga.player.slingshot.attackValue), valueOffset, yStart + 3 * lineHeight);

        gc.fillText("Speed:", 50, yStart + 4 * lineHeight);
        gc.fillText(Integer.toString(ga.player.speed), valueOffset, yStart + 4 * lineHeight);
    }

    public void renderInventory() {
        //rectangle
        int frameX = ga.SCREEN_WIDTH2 - 925; //452m 1050
        int frameY = ga.SCREEN_HEIGHT2 - 200;
        int frameWidth = ga.TILE_SIZE + 352;
        int frameHeight = ga.TILE_SIZE + 22;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slots
        int slotWidth = 37; // Width of each slot
        int slotHeight = 37; // Height of each slot
        int inventoryX = frameX + 18; // Starting X position for slots
        int inventoryY = frameY + 15; // Starting Y position for slots
        //Looks like slot size/bar won't be changed at all for the foreseeable future, should be good to just leave it tiled
        //and then have items occupy the empty tiles (kind of like minecraft)
        // Draw inventory slots

        for (int i = 0; i < 8; i++) {
            gc.strokeRoundRect(inventoryX + i * (slotWidth + 10), inventoryY, slotWidth, slotHeight, 20, 10);
        }

        for (int i = 0; i < ga.player.inventory.size(); i++) {
            Image itemImage = ga.player.inventory.get(i).images.get(0);
            int amount = ga.player.inventory.get(i).amount;

            int slotX = inventoryX + i * (slotWidth + 10);
            int slotY = inventoryY;

            // Draw inventory slot
            gc.strokeRoundRect(slotX, slotY, slotWidth, slotHeight, 20, 10);

            // Draw item image
            gc.drawImage(itemImage, slotX, slotY);

            SuperObject item = ga.player.inventory.get(i);

            // Draw the quantity of items stacked
            if (item.amount > 1) {
                gc.setFont(new Font("Arial", 19));
                gc.setFill(Color.WHITE);
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(String.valueOf(ga.player.inventory.get(i).amount ), slotX + slotWidth - 5, slotY + slotHeight - 5);
            }
            else {
                ga.player.inventory.remove(item);
            }

        }
    }

    public void renderUpgradeScreen() {
        int frameX = ga.SCREEN_WIDTH / 8;
        int frameY = ga.SCREEN_HEIGHT / 8;
        int frameWidth = ga.SCREEN_WIDTH * 3 / 4 ;
        int frameHeight = ga.SCREEN_HEIGHT * 3 / 4;

        int buttonWidth = 144;
        int buttonHeight = 72;
        int buttonSpacing = 64; // Spacing between buttons
        int totalButtonWidth = 4 * buttonWidth + 3 * buttonSpacing;
        int buttonX = ga.SCREEN_WIDTH / 8 + ga.TILE_SIZE;
        int buttonY = ga.SCREEN_HEIGHT / 8 + ga.TILE_SIZE;

        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, ga.SCREEN_WIDTH, ga.SCREEN_HEIGHT);
        gc.drawImage(images.get(0), frameX, frameY, frameWidth, frameHeight);

        gc.drawImage(images.get(2), buttonX, buttonY);
        gc.drawImage(images.get(3), buttonX + buttonWidth + buttonSpacing, buttonY);
        gc.drawImage(images.get(4), buttonX + 2 * (buttonWidth + buttonSpacing), buttonY);
        gc.drawImage(images.get(5), buttonX + 3 * (buttonWidth + buttonSpacing), buttonY);

        // Upgrade
        ga.upgradeBootsButton.setOnMouseClicked(e -> {
            // Handle Upgrade Boots button action
            // Update costs and button text
           ga.player.upgradeBoots();
           updateUpgradeButtonsText();

           // ga.player.test();

        });
        ga.upgradeMeleeButton.setOnMouseClicked(e -> {
            ga.player.upgradeMelee();
            updateUpgradeButtonsText();
        });
        ga.upgradeArmorButton.setOnMouseClicked(e -> {
            ga.player.upgradeArmor();
            updateUpgradeButtonsText();
        });
        ga.upgradeProjectileButton.setOnMouseClicked(e -> {
            ga.player.upgradeProjectile();
            updateUpgradeButtonsText();
        });

        // Mouse Hover Above
        ga.upgradeBootsButton.setOnMouseEntered(e -> {

        });
        ga.upgradeMeleeButton.setOnMouseEntered(e -> {

        });
        ga.upgradeArmorButton.setOnMouseEntered(e -> {

        });
        ga.upgradeProjectileButton.setOnMouseEntered(e -> {

        });

        // Reset Image
        ga.upgradeBootsButton.setOnMouseExited(e -> {

        });
        ga.upgradeMeleeButton.setOnMouseExited(e -> {

        });
        ga.upgradeArmorButton.setOnMouseExited(e -> {

        });
        ga.upgradeProjectileButton.setOnMouseExited(e -> {

        });
    }

    public void drawSubWindow (int x, int y, int width, int height) {
        gc.setFill(Color.rgb(0, 0, 0, 0.8)); // Set the background color
        gc.fillRoundRect(x, y, width, height, 30, 20); // Draw the main inventory rectangle
        gc.setStroke(Color.WHITE); // Set the border color
        gc.setLineWidth(2); // Set the border width
        gc.strokeRoundRect(x,y,width,height,30,20); // Draw white border around the main inventory rectangle
    }
    public void updateUpgradeButtonsText() {
//        // Update upgrade button texts based on the player's inventory and costs
//        // Update boots costs
//        ga.upgradeBootsButton.setText("Upgrade Boots\nCost: " + ga.player.bootsGoldCost + " Gold, " + ga.player.bootsStoneCost + " Stone, " + ga.player.bootsWoodCost + " Wood");
//
//        // Update melee costs
//        ga.upgradeMeleeButton.setText("Upgrade Melee\nCost: " + ga.player.meleeGoldCost + " Gold, " + ga.player.meleeStoneCost + " Stone, " + ga.player.meleeWoodCost + " Wood");
//
//        // Update armor costs
//        ga.upgradeArmorButton.setText("Upgrade Armor\nCost: " + ga.player.armorGoldCost + " Gold, " + ga.player.armorStoneCost + " Stone, " + ga.player.armorWoodCost + " Wood");
//
//        // Update projectile costs
//        ga.upgradeProjectileButton.setText("Upgrade Projectile\nCost: " + ga.player.projectileGoldCost + " Gold, " + ga.player.projectileStoneCost + " Stone, " + ga.player.projectileWoodCost + " Wood");
    }
}


