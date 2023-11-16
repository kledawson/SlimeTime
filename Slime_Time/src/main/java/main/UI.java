package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import java.text.DecimalFormat;

// Handles Additional UI on top of Scene
public class UI {
    GameApplication ga;
    GraphicsContext gc;

    Font arial_40, arial_80B;
//    ImageView keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat  = new DecimalFormat("#0.00");
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GameApplication ga) {
        this.ga = ga;
        arial_40 = new Font("Arial", 40); // example font
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
        double rectangleWidth = ga.SCREEN_WIDTH - 538;
       gc.setFill(Color.BLACK);
       gc.setFill((Color.rgb(0,0,0,0.8)));
       gc.fillRoundRect(25,45,rectangleWidth, ga.SCREEN_HEIGHT - 90, 30, 20);
       gc.setStroke((Color.WHITE));
       gc.setLineWidth(2);
       gc.strokeRoundRect(25,45, rectangleWidth, ga.SCREEN_HEIGHT - 90, 20, 20);

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

        gc.fillText("Attack:", 50, yStart + 2 * lineHeight);
        gc.fillText(Integer.toString(ga.player.attack), valueOffset, yStart + 2 * lineHeight);

        gc.fillText("Defense:", 50, yStart + 3 * lineHeight);
        gc.fillText(Integer.toString(ga.player.defense), valueOffset, yStart + 3 * lineHeight);

        gc.fillText("Speed:", 50, yStart + 4 * lineHeight);
        gc.fillText(Integer.toString(ga.player.speed), valueOffset, yStart + 4 * lineHeight);
    }

    public void renderInventory() {
        //rectangle
        int frameX = ga.TILE_SIZE + 302;
        int frameY = ga.TILE_SIZE + 2;
        int frameWidth = ga.TILE_SIZE + 352;
        int frameHeight = ga.TILE_SIZE + 22;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slots
        int slotWidth = 37; // Width of each slot
        int slotHeight = 37; // Height of each slot
        int inventoryX = 368; // Starting X position for slots
        int inventoryY = 65; // Starting Y position for slots
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

            // Draw the quantity of items stacked
            if (ga.player.inventory.get(i).amount > 1) {
                gc.setFont(new Font("Arial", 12));
                gc.setFill(Color.WHITE);
                int amountX;
                int amountY;
                String s = "" + ga.player.inventory.get(i).amount;
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(String.valueOf(ga.player.inventory.get(i).amount ), slotX + slotWidth - 5, slotY + slotHeight - 5);
            }
        }

        //Displaying the amount

    }
    public void drawSubWindow (int x, int y, int width, int height) {
        gc.setFill(Color.rgb(0, 0, 0, 0.8)); // Set the background color
        gc.fillRoundRect(x, y, width, height, 30, 20); // Draw the main inventory rectangle
        gc.setStroke(Color.WHITE); // Set the border color
        gc.setLineWidth(2); // Set the border width
        gc.strokeRoundRect(x,y,width,height,30,20); // Draw white border around the main inventory rectangle
    }

}


