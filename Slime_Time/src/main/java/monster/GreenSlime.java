package monster;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameApplication;

import java.io.FileInputStream;
import java.util.Random;

public class GreenSlime extends Entity {
    GameApplication ga;
    public Image greenSlime1, greenSlime2, greenSlime3, greenSlime4, greenSlime5;
    public int greenSlimeHp;
    public int i;


    public GreenSlime(GameApplication ga) {
        this.ga = ga;
        String name = "Green_Slime";
        int speed = 0;
        int maxSlimeHp = 8;
        greenSlimeHp = maxSlimeHp;

        getGreenSlimeImage();
        updateGreenSlimeImage();
    }


    public void getGreenSlimeImage() {
        greenSlime1 = setupGreenSlime("greenSlime1");
        greenSlime2 = setupGreenSlime("greenSlime2");
        greenSlime3 = setupGreenSlime("greenSlime3");
        greenSlime4 = setupGreenSlime("greenSlime4");
        greenSlime5 = setupGreenSlime("greenSlime5");
    }


    public Image setupGreenSlime(String imageName) {
        try {
            return new Image(new FileInputStream("Slime_Time/res/monster/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
        }
        catch (Exception e) {
            try {
                return new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
            }
            catch (Exception ex) {
                System.err.println("Error loading image: " + imageName);
                ex.printStackTrace();
                return null;
            }
        }
    }

    public void render(GraphicsContext gc, GameApplication ga) {
        GraphicsContext gcObj = gc;
        int screenX = worldX - ga.player.worldX + ga.player.screenX;
        int screenY = worldY - ga.player.worldY + ga.player.screenY;

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
                worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
                worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
                worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
            gcObj.drawImage(greenSlime1, screenX, screenY);
        }
    }

    public void updateGreenSlimeImage() {

        if (greenSlimeHp == 7 || greenSlimeHp == 8) {
            greenSlime1 = setupGreenSlime("greenSlime1");
        }
        else if (greenSlimeHp == 5 || greenSlimeHp == 6) {
            greenSlime2 = setupGreenSlime("greenSlime1");
        }
        else if (greenSlimeHp == 3 || greenSlimeHp == 4) {
            greenSlime3 = setupGreenSlime("greenSlime1");
        }
        else if (greenSlimeHp == 1 || greenSlimeHp == 2) {
            greenSlime4 = setupGreenSlime("greenSlime1");
        }
        else if (greenSlimeHp == 0) {
            greenSlime5 = setupGreenSlime("greenSlime1");
        }
        else {
            greenSlime1 = setupGreenSlime("greenSlime1");
        }
    }

    public void greenSlimeDamage() {
        greenSlimeHp--;
        updateGreenSlimeImage();
    }

    public void updateGreenSlime() {
        setAction();
        collisionOn = false;
        //collision checker here

        if (collisionOn == false) {
            switch(direction){
                case "up": worldY -= speed;
                    break;
                case "down": worldY += speed;
                    break;
                case "left": worldX -= speed;
                    break;
                case "right": worldX += speed;
                    break;
            }


        }
    }

    public void setAction(){
        int actionLockCounter = 0;
        actionLockCounter++;
        Random rand = new Random();
        int i = rand.nextInt(100)+1;

        if (i <= 25) {
            direction = "up";
        }
        if (i > 25 && i <= 50) {
            direction = "down";
        }
        if (i > 50 && i <= 75) {
            direction = "left";
        }
        if (i > 75 && i <= 100) {
            direction = "right";
        }

        actionLockCounter = 0;
    }
}
