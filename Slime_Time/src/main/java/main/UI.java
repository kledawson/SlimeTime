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
    }
    public void renderPauseScreen() {
        gc.setFont(gc.getFont().font(80));
        String text = "PAUSED";
        double x = ga.SCREEN_WIDTH / 2;
        double y = ga.SCREEN_HEIGHT / 2;
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, x, y);
    }
}
