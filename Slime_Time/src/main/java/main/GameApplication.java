package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import entity.Player;
import object.ObjectManager;
import object.SuperObject;
import tile.TileManager;

// Game Application
public class GameApplication extends Application {
    // Screen, World Settings
    final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 px
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 px
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this,false);
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public ObjectManager objM = new ObjectManager(this);
    public UI ui = new UI(this);

    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.setFill(Color.BLACK);

        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(keyH = new KeyHandler(this,true));
        scene.setOnKeyReleased(keyH = new KeyHandler(this,false));

        stage.setTitle("2D Adventure");
        stage.setScene(scene);
        stage.show();

        startGameLoop(gc);
    }

    public static void main(String[] args) {
        launch();
    }

    // Any Methods to Set up at Beginning of Game
    public void setupGame() {
        objM.setObject();
        // playMusic(0);
        gameState = playState;
    }

    // Game Loop
    public void startGameLoop(GraphicsContext gc) {
        System.out.println("Setup Game");
        setupGame();
        new AnimationTimer() {
            double drawInterval = 1000000000 / FPS; // Running at Certain FPS
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;

            @Override
            public void handle(long now) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;
                if (delta >= 1) {
                    update();
                    render(gc);
                    --delta;
                    ++drawCount;
                }
                if (timer >= 1000000000) {
                    System.out.println("FPS: " + drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }.start();
    }

    public void update() {
        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {
            // Pause State
        }
    }

    public void render(GraphicsContext gc) {
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        tileM.render(gc);

        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.render(gc, this);
            }
        }

        player.render(gc);

        ui.render(gc);

        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
    }

    public void playMusic(int i) {
        sound.play(0);
        sound.loop(0);
    }
    public void stopMusic() {
        sound.stop(1);
    }
    public void playSE(int i) {
        sound.play(i);
    }
}
