package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import object.SuperObject;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

// Handles Additional UI on top of Scene
public class UI {
    GameApplication ga;
    GraphicsContext gc;

    Font pix20, pix23, pix80;
    //    ImageView keyImage;
    public List<Image> images = new ArrayList<>();
    public boolean messageOn = false;
    public String message = "";
    public boolean showUpgradeScreen = false;

    // Variables to store upgrade costs

//    Button upgradeMeleeButton; // Declare the buttons
//    Button upgradeArmorButton;
//    Button upgradeProjectileButton;
//    Button upgradeBootsButton;

    public UI(GameApplication ga) {
        this.ga = ga;
//        arial_40 = new Font("Arial", 40); // example font
        try {
            pix20 = Font.loadFont(new FileInputStream("Slime_Time/res/fonts/BULKYPIX.TTF"), 20);
            pix23 = Font.loadFont(new FileInputStream("Slime_Time/res/fonts/BULKYPIX.TTF"), 23);
            pix80 = Font.loadFont(new FileInputStream("Slime_Time/res/fonts/BULKYPIX.TTF"), 80);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        getUIImage();
    }

    public void getUIImage() {
        setup("upgrade_menu", ga.SCREEN_WIDTH * 3 / 4, ga.SCREEN_HEIGHT * 3 / 4);
        setup("button_image", 144, 72);
        setup("boots_icon", 144, 144);
        setup("melee_icon", 144, 144);
        setup("ranged_icon", 144, 144);
        setup("heart_icon", 144, 144);
        setup("inventory_menu", ga.SCREEN_WIDTH * 5 / 18, ga.SCREEN_HEIGHT * 3 / 4);
        setup("wood", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("stone", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("gold", ga.TILE_SIZE, ga.TILE_SIZE);
        setup("button_image1", ga.TILE_SIZE * 5 / 2, ga.TILE_SIZE + 18);
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

//    public void showMessage(String text) {
//        message = text;
//        messageOn = true;
//    }
    public void render(GraphicsContext gc) {
        this.gc = gc;
        gc.setFill(Color.WHITE);

        if (ga.gameState == ga.playState) {
            // UI in playState
        }
        if (ga.gameState == ga.pauseState) {
            renderPauseScreen();
        }
        if (ga.gameState == ga.characterState) {
            renderCharacterScreen();
        }
        if (showUpgradeScreen) {
            renderUpgradeScreen();
        }
    }
    public void renderPauseScreen() {
        gc.setFont(pix80);
        String text = "PAUSED";
        int x = ga.SCREEN_WIDTH / 2;
        int y = ga.SCREEN_HEIGHT / 2;
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, x, y);
    }


    public void renderCharacterScreen() {
        drawMenuWindow();

        // RECTANGLE
        double rectangleWidth = ga.SCREEN_WIDTH / 2; // Decrease the width by 100 pixels
        double rectangleX = ga.SCREEN_WIDTH / 9; // X-coordinate of the rectangle
        double rectangleY = ga.SCREEN_HEIGHT / 8; // Y-coordinate of the rectangle

        // TEXT
        int yStart = (int) (rectangleY + 96);
        int lineHeight = 64;
        int valueOffset = 200;

        gc.setFont(pix20);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.LEFT);

        // Calculate the center of the rectangle for x-coordinate of the text
        double textCenterX = rectangleX + rectangleWidth / 2;

        gc.fillText("Hit Points:", rectangleX + ga.TILE_SIZE, yStart, rectangleWidth);
        gc.fillText(ga.player.life + "/" + ga.player.maxLife, textCenterX, yStart);

        gc.fillText("Melee ATK:", rectangleX + ga.TILE_SIZE, yStart + lineHeight, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.scythe.attackValue), textCenterX, yStart + lineHeight);

        gc.fillText("MATK Speed:", rectangleX + ga.TILE_SIZE, yStart + 2 * lineHeight, rectangleWidth);
        gc.fillText(String.format("%.2f", (double)(ga.player.scythe.attackSpeed) / 60) + " PER SECOND", textCenterX, yStart + 2 * lineHeight);

        gc.fillText("Ranged Atk:", rectangleX + ga.TILE_SIZE, yStart + 3 * lineHeight, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.slingshot.attackValue), textCenterX, yStart + 3 * lineHeight);

        gc.fillText("RATK Speed:", rectangleX + ga.TILE_SIZE, yStart + 4 * lineHeight, rectangleWidth);
        gc.fillText(String.format("%.2f", (double)(ga.player.slingshot.speed) / 60) + " PER SECOND", textCenterX, yStart + 4 * lineHeight);

        gc.fillText("Speed:", rectangleX + ga.TILE_SIZE, yStart + 5 * lineHeight, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.speed), textCenterX, yStart + 5 * lineHeight);

        renderInventory();
    }


    public void renderInventory() {
        // Inventory rendering (2x4 slots)
        int frameX = ga.SCREEN_WIDTH / 9 + ga.SCREEN_WIDTH / 2; // X position for inventory
        int frameY = ga.SCREEN_HEIGHT / 8; // Y position for inventory
        int slotWidth = ga.ORIGINAL_TILE_SIZE * 4; // Width of each slot
        int slotHeight = ga.ORIGINAL_TILE_SIZE * 4; // Height of each slot
        int inventoryX = frameX + ga.ORIGINAL_TILE_SIZE * 4; // Starting X position for slots
        int inventoryY = frameY + ga.ORIGINAL_TILE_SIZE * 4; // Starting Y position for slots

        // Draw the inventory frame
        int invWidth = 2; // Number of columns in the inventory
        int invHeight = 4; // Number of rows in the inventory
        gc.drawImage(images.get(6), frameX, frameY, ga.SCREEN_WIDTH * 5 / 18, ga.SCREEN_HEIGHT * 3 / 4);

        // Draw inventory items within the available slots
        int currentSlot = 0; // Keep track of the current slot being filled
        for (int i = 0; i < ga.player.inventory.size(); i++) {
            SuperObject item = ga.player.inventory.get(i);
            int slotX = inventoryX + (currentSlot % invWidth) * (slotWidth * 2);
            int slotY = inventoryY + (currentSlot / invWidth) * (slotHeight * 2);
            // Draw the item image within the slot
            gc.drawImage(item.images.get(0), slotX, slotY, slotWidth * 2 / 3, slotHeight * 2 / 3);

            // Draw the quantity of items stacked (if greater than 1)
            if (item.amount > 1) {
                gc.setFont(pix20);
                gc.setFill(Color.WHITE);
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(String.valueOf(item.amount), slotX + slotWidth - 5, slotY + slotHeight - 5);
            } else {
                ga.player.inventory.remove(item);
            }

            currentSlot++;
        }
    }

    public void renderUpgradeScreen() {
        drawMenuWindow();

        int buttonWidth = ga.TILE_SIZE * 2;
        int buttonSpacing = 24; // Spacing between buttons
        int totalButtonWidth = 4 * buttonWidth + 3 * buttonSpacing;
        int buttonX = ga.SCREEN_WIDTH / 9 + ga.TILE_SIZE;
        int buttonY = ga.SCREEN_HEIGHT / 8 + ga.TILE_SIZE;

        int costX = ga.SCREEN_WIDTH / 9 + ga.TILE_SIZE * 7 / 2;
        int costY = ga.SCREEN_HEIGHT / 8 + ga.TILE_SIZE;

        // Upgrade Icons
        gc.drawImage(images.get(5), buttonX, buttonY, buttonWidth, buttonWidth);
        gc.drawImage(images.get(3), buttonX , buttonY + buttonWidth + buttonSpacing, buttonWidth, buttonWidth);
        gc.drawImage(images.get(4), buttonX, buttonY + 2 * (buttonWidth + buttonSpacing), buttonWidth, buttonWidth);
        gc.drawImage(images.get(2), buttonX, buttonY + 3 * (buttonWidth + buttonSpacing), buttonWidth, buttonWidth);

        // Cost Icons
        gc.drawImage(images.get(7), costX, costY);
        gc.drawImage(images.get(8), costX + ga.TILE_SIZE + 36, costY);
        gc.drawImage(images.get(9), costX + 2 * (ga.TILE_SIZE + 36), costY);

        gc.drawImage(images.get(7), costX, costY + ga.TILE_SIZE * 5 / 2);
        gc.drawImage(images.get(8), costX + ga.TILE_SIZE + 36, costY + ga.TILE_SIZE * 5 / 2);
        gc.drawImage(images.get(9), costX + 2 * (ga.TILE_SIZE + 36), costY + ga.TILE_SIZE * 5 / 2);

        gc.drawImage(images.get(7), costX, costY + 2 * (ga.TILE_SIZE * 5 / 2));
        gc.drawImage(images.get(8), costX + ga.TILE_SIZE + 36, costY + 2 * (ga.TILE_SIZE * 5 / 2));
        gc.drawImage(images.get(9), costX + 2 * (ga.TILE_SIZE + 36), costY + 2 * (ga.TILE_SIZE * 5 / 2));

        gc.drawImage(images.get(7), costX, costY + 3 * (ga.TILE_SIZE * 5 / 2));
        gc.drawImage(images.get(8), costX + ga.TILE_SIZE + 36, costY + 3 * (ga.TILE_SIZE * 5 / 2));
        gc.drawImage(images.get(9), costX + 2 * (ga.TILE_SIZE + 36), costY + 3 * (ga.TILE_SIZE * 5 / 2));

        // Upgrade Costs
        gc.setFont(pix20);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);

        gc.fillText(String.valueOf(ga.player.armorWoodCost), costX + ga.TILE_SIZE / 2, costY + ga.TILE_SIZE * 3 / 2);
        gc.fillText(String.valueOf(ga.player.armorStoneCost), costX + ga.TILE_SIZE / 2 + ga.TILE_SIZE + 36, costY + ga.TILE_SIZE * 3 / 2);
        gc.fillText(String.valueOf(ga.player.armorGoldCost), costX + ga.TILE_SIZE / 2 + 2 * (ga.TILE_SIZE + 36), costY + ga.TILE_SIZE * 3 / 2);

        gc.fillText(String.valueOf(ga.player.meleeWoodCost), costX + ga.TILE_SIZE / 2, costY + ga.TILE_SIZE * 3 / 2 + ga.TILE_SIZE * 5 / 2);
        gc.fillText(String.valueOf(ga.player.meleeStoneCost), costX + ga.TILE_SIZE / 2 + ga.TILE_SIZE + 36, costY + ga.TILE_SIZE * 3 / 2 + ga.TILE_SIZE * 5 / 2);
        gc.fillText(String.valueOf(ga.player.meleeGoldCost), costX + ga.TILE_SIZE / 2 + 2 * (ga.TILE_SIZE + 36), costY + ga.TILE_SIZE * 3 / 2 + ga.TILE_SIZE * 5 / 2);

        gc.fillText(String.valueOf(ga.player.projectileWoodCost), costX + ga.TILE_SIZE / 2, costY + ga.TILE_SIZE * 3 / 2 + 2 * (ga.TILE_SIZE * 5 / 2));
        gc.fillText(String.valueOf(ga.player.projectileStoneCost), costX + ga.TILE_SIZE / 2 + ga.TILE_SIZE + 36, costY + ga.TILE_SIZE * 3 / 2 + 2 * (ga.TILE_SIZE * 5 / 2));
        gc.fillText(String.valueOf(ga.player.projectileGoldCost), costX + ga.TILE_SIZE / 2 + 2 * (ga.TILE_SIZE + 36), costY + ga.TILE_SIZE * 3 / 2 + 2 * (ga.TILE_SIZE * 5 / 2));

        gc.fillText(String.valueOf(ga.player.bootsWoodCost), costX + ga.TILE_SIZE / 2, costY + ga.TILE_SIZE * 3 / 2 + 3 * (ga.TILE_SIZE * 5 / 2));
        gc.fillText(String.valueOf(ga.player.bootsStoneCost), costX + ga.TILE_SIZE / 2 + ga.TILE_SIZE + 36, costY + ga.TILE_SIZE * 3 / 2 + 3 * (ga.TILE_SIZE * 5 / 2));
        gc.fillText(String.valueOf(ga.player.bootsGoldCost), costX + ga.TILE_SIZE / 2 + 2 * (ga.TILE_SIZE + 36), costY + ga.TILE_SIZE * 3 / 2 + 3 * (ga.TILE_SIZE * 5 / 2));

        // Negative Button Images
        int buttonX2 = ga.SCREEN_WIDTH / 9 + ga.TILE_SIZE * 17 / 2;
        int buttonY2 = ga.SCREEN_HEIGHT / 8 + ga.TILE_SIZE + 28;
        if (!ga.player.hasRequiredItems(ga.player.armorGoldCost, ga.player.armorStoneCost, ga.player.armorWoodCost)) {
            gc.drawImage(images.get(10), buttonX2, buttonY2);
            ga.upgradeArmorButton.setVisible(false);
        }
        else {
            ga.upgradeArmorButton.setVisible(true);
        }
        if (!ga.player.hasRequiredItems(ga.player.meleeGoldCost, ga.player.meleeStoneCost, ga.player.meleeWoodCost)) {
            gc.drawImage(images.get(10), buttonX2, buttonY2 + ga.TILE_SIZE * 5 / 2);
            ga.upgradeMeleeButton.setVisible(false);
        }
        else {
            ga.upgradeMeleeButton.setVisible(true);
        }
        if (!ga.player.hasRequiredItems(ga.player.projectileGoldCost, ga.player.projectileStoneCost, ga.player.projectileWoodCost)) {
            gc.drawImage(images.get(10), buttonX2, buttonY2 + 2 * (ga.TILE_SIZE * 5 / 2));
            ga.upgradeProjectileButton.setVisible(false);
        }
        else {
            ga.upgradeProjectileButton.setVisible(true);
        }
        if (!ga.player.hasRequiredItems(ga.player.bootsGoldCost, ga.player.bootsStoneCost, ga.player.bootsWoodCost)) {
            gc.drawImage(images.get(10), buttonX2, buttonY2 + 3 * (ga.TILE_SIZE * 5 / 2));
            ga.upgradeBootsButton.setVisible(false);
        }
        else {
            ga.upgradeBootsButton.setVisible(true);
        }


        // Upgrade
        ga.upgradeBootsButton.setOnMouseClicked(e -> {
            // Handle Upgrade Boots button action
            // Update costs and button text
           ga.player.upgradeBoots();
        });
        ga.upgradeMeleeButton.setOnMouseClicked(e -> ga.player.scythe.upgrade());
        ga.upgradeArmorButton.setOnMouseClicked(e -> ga.player.upgradeArmor());
        ga.upgradeProjectileButton.setOnMouseClicked(e -> ga.player.slingshot.upgrade());

        renderInventory();
    }

    public void drawMenuWindow() {
        int frameX = ga.SCREEN_WIDTH / 9;
        int frameY = ga.SCREEN_HEIGHT / 8;
        int frameWidth = ga.SCREEN_WIDTH / 2 ;
        int frameHeight = ga.SCREEN_HEIGHT * 3 / 4;
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, ga.SCREEN_WIDTH, ga.SCREEN_HEIGHT);
        gc.drawImage(images.get(0), frameX, frameY, frameWidth, frameHeight);
    }
//    public void drawSubWindow (int x, int y, int width, int height) {
//        gc.setFill(Color.rgb(0, 0, 0, 0.8)); // Set the background color
//        gc.fillRoundRect(x, y, width, height, 30, 20); // Draw the main inventory rectangle
//        gc.setStroke(Color.WHITE); // Set the border color
//        gc.setLineWidth(2); // Set the border width
//        gc.strokeRoundRect(x,y,width,height,30,20); // Draw white border around the main inventory rectangle
//    }
}


