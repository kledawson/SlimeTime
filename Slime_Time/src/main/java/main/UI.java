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
        // RECTANGLE
        double rectangleWidth = ga.SCREEN_WIDTH - 538; // Decrease the width by 100 pixels
        double rectangleX = 275; // X-coordinate of the rectangle
        double rectangleY = 200; // Y-coordinate of the rectangle

        gc.setFill(Color.BLACK);
        gc.setFill((Color.rgb(0, 0, 0, 0.8)));
        gc.fillRoundRect(rectangleX, rectangleY, rectangleWidth - 100, ga.SCREEN_HEIGHT - 250, 30, 20);
        gc.setStroke((Color.WHITE));
        gc.setLineWidth(2);
        gc.strokeRoundRect(rectangleX, rectangleY, rectangleWidth - 100, ga.SCREEN_HEIGHT - 250, 20, 20);

        // TEXT
        int yStart = 270;
        int lineHeight = 50;
        int valueOffset = 200;

        gc.setFont(new Font("Ariel", 23));
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.LEFT);

        // Calculate the center of the rectangle for x-coordinate of the text
        double textCenterX = rectangleX + rectangleWidth / 2;

        gc.fillText("Level:", rectangleX + 50, yStart, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.level), textCenterX, yStart);

        gc.fillText("Life:", rectangleX + 50, yStart + lineHeight, rectangleWidth);
        gc.fillText(ga.player.life + "/" + ga.player.maxLife, textCenterX, yStart + lineHeight);

        gc.fillText("Melee Atk:", rectangleX + 50, yStart + 2 * lineHeight, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.scythe.attackValue), textCenterX, yStart + 2 * lineHeight);

        gc.fillText("Projectile Atk:", rectangleX + 50, yStart + 3 * lineHeight, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.slingshot.attackValue), textCenterX, yStart + 3 * lineHeight);

        gc.fillText("Speed:", rectangleX + 50, yStart + 4 * lineHeight, rectangleWidth);
        gc.fillText(Integer.toString(ga.player.speed), textCenterX, yStart + 4 * lineHeight);
    }


    public void renderInventory() {
        // Inventory rendering (2x4 slots)
        int frameX = ga.SCREEN_WIDTH - 340; // X position for inventory
        int frameY = ga.SCREEN_HEIGHT - 370; // Y position for inventory
        int slotWidth = 67; // Width of each slot
        int slotHeight = 67; // Height of each slot
        int inventoryX = frameX + 18; // Starting X position for slots
        int inventoryY = frameY + 15; // Starting Y position for slots

        // Draw the inventory frame
        int invWidth = 2; // Number of columns in the inventory
        int invHeight = 4; // Number of rows in the inventory
        drawSubWindow(frameX, frameY, slotWidth * invWidth + 50, slotHeight * invHeight + 50);

        // Draw inventory items within the available slots
        int currentSlot = 0; // Keep track of the current slot being filled
        for (int i = 0; i < ga.player.inventory.size(); i++) {
            SuperObject item = ga.player.inventory.get(i);
            int slotX = inventoryX + (currentSlot % invWidth) * (slotWidth + 10);
            int slotY = inventoryY + (currentSlot / invWidth) * (slotHeight + 10);
            // Draw the item image within the slot
            gc.drawImage(item.images.get(0), slotX, slotY);

            // Draw the quantity of items stacked (if greater than 1)
            if (item.amount > 1) {
                gc.setFont(new Font("Arial", 19));
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
        });
        ga.upgradeMeleeButton.setOnMouseClicked(e -> {
            ga.player.upgradeMelee();
        });
        ga.upgradeArmorButton.setOnMouseClicked(e -> {
            ga.player.upgradeArmor();
        });
        ga.upgradeProjectileButton.setOnMouseClicked(e -> {
            ga.player.upgradeProjectile();
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
}


