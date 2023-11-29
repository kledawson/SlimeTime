package main;

import entity.Entity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import entity.Player;
import object.ObjectManager;
import object.SuperObject;
import tile.TileManager;
import tiles_interactive.Rock;

import java.io.FileInputStream;


// Game Application
public class GameApplication extends Application {
    // Screen, World Settings
    final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public final int MAX_SCREEN_COL_SMALL = 24;
    public final int MAX_SCREEN_ROW_SMALL = 16;
    public final int MAX_SCREEN_COL_LARGE = 54;
    public final int MAX_SCREEN_ROW_LARGE = 29;

    // Current maximum screen col and row values
    public int MAX_SCREEN_COL = MAX_SCREEN_COL_SMALL;
    public int MAX_SCREEN_ROW = MAX_SCREEN_ROW_SMALL;

    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 1152 px
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 768 px
    public final int MAX_WORLD_COL = 100;
    public final int MAX_WORLD_ROW = 100;
    int FPS = 60;

    //for full screen UI
    int SCREEN_WIDTH2 = TILE_SIZE * MAX_SCREEN_COL_LARGE;
    int SCREEN_HEIGHT2 = TILE_SIZE * MAX_SCREEN_ROW_LARGE;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this, false);
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public ObjectManager objM = new ObjectManager(this);
    public UI ui = new UI(this);
    public Player player = new Player(this, keyH);
    public Entity rock[] = new Entity[10];
    public SuperObject[] obj = new SuperObject[10];
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int characterState = 3;
    public Pane root;
    public boolean upgradeButtonsVisible = false;

    public double mouseX;
    public double mouseY;

//    Button upgradeMeleeButton; // Declare the buttons
//    Button upgradeArmorButton;
//    Button upgradeProjectileButton;
//    Button upgradeBootsButton;

    ImageView upgradeBootsButton;
    ImageView upgradeMeleeButton;
    ImageView upgradeArmorButton;
    ImageView upgradeProjectileButton;

    @Override
    public void start(Stage stage) {
        root = new Pane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.setFill(Color.BLACK);
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(keyH = new KeyHandler(this, true));
        scene.setOnKeyReleased(keyH = new KeyHandler(this, false));
        scene.setOnMouseMoved(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                player.scythe.attack();
            }
            else if (e.getButton() == MouseButton.SECONDARY) {
                player.slingshot.attack();
            }
        });

        Button showUpgradeScreenButton = new Button("Upgrade");
        showUpgradeScreenButton.setLayoutX(SCREEN_WIDTH - 100);
        showUpgradeScreenButton.setLayoutY(20);


        // Add the button to the root pane
        root.getChildren().add(showUpgradeScreenButton);


        // Set button positions and sizes
        int buttonWidth = 144;
        int buttonHeight = 72;
        int buttonSpacing = 64; // Spacing between buttons
        int totalButtonWidth = 4 * buttonWidth + 3 * buttonSpacing;
        int buttonX = SCREEN_WIDTH / 8 + TILE_SIZE;
        int buttonY = SCREEN_HEIGHT * 5 / 8;

        try {
            Image buttonImage = new Image(new FileInputStream("Slime_Time/res/ui/button_image.png"), buttonWidth, buttonHeight, false, false);
            upgradeBootsButton = new ImageView(buttonImage);
            upgradeMeleeButton = new ImageView(buttonImage);
            upgradeArmorButton = new ImageView(buttonImage);
            upgradeProjectileButton = new ImageView(buttonImage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        upgradeBootsButton.setLayoutX(buttonX);
        upgradeBootsButton.setLayoutY(buttonY);

        upgradeMeleeButton.setLayoutX(buttonX + buttonWidth + buttonSpacing);
        upgradeMeleeButton.setLayoutY(buttonY);

        upgradeArmorButton.setLayoutX(buttonX + 2 * (buttonWidth + buttonSpacing));
        upgradeArmorButton.setLayoutY(buttonY);

        upgradeProjectileButton.setLayoutX(buttonX + 3 * (buttonWidth + buttonSpacing));
        upgradeProjectileButton.setLayoutY(buttonY);


        upgradeBootsButton.setVisible(false);
        upgradeMeleeButton.setVisible(false);
        upgradeArmorButton.setVisible(false);
        upgradeProjectileButton.setVisible(false);

        // Add buttons to the root pane
        root.getChildren().addAll(
                upgradeBootsButton,
                upgradeMeleeButton,
                upgradeArmorButton,
                upgradeProjectileButton
        );

        showUpgradeScreenButton.setOnAction(e -> {
            if (upgradeBootsButton.isVisible()) {
                upgradeBootsButton.setVisible(false);
                upgradeMeleeButton.setVisible(false);
                upgradeArmorButton.setVisible(false);
                upgradeProjectileButton.setVisible(false);
            }
            else {
                upgradeBootsButton.setVisible(true);
                upgradeMeleeButton.setVisible(true);
                upgradeArmorButton.setVisible(true);
                upgradeProjectileButton.setVisible(true);
            }
        });

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

        for (Entity r : rock) {
            if (r != null) {
                ((Rock) r).render(gc, this);
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


