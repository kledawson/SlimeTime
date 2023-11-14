package main;

import javafx.scene.canvas.GraphicsContext;
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
       gc.fillRoundRect(25,45,rectangleWidth, ga.SCREEN_HEIGHT - 90, 20, 20);
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


        /*
        gc.fillText("Experience:", 50, yStart + 4 * lineHeight);
        gc.fillText(ga.player.exp + "/" + ga.player.nextLevelExp, valueOffset, yStart + 4 * lineHeight);

        gc.fillText("Next level: ", 50, yStart + 5 * lineHeight);
        gc.fillText(Integer.toString(ga.player.nextLevelExp), valueOffset, yStart + 5 * lineHeight);
         */

    }

    public void renderInventory() {

    }


}


