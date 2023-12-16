package monster;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.CollisionChecker;
import main.GameApplication;

import java.io.FileInputStream;
import java.util.Random;

public class GreenSlime extends Entity {
    GameApplication ga;
    public Image greenSlime1, greenSlime2, greenSlime3, greenSlime4, greenSlime5;
    public int greenSlimeHp;

    private int actionLockCounter = 0;


    public GreenSlime(GameApplication ga) {
        this.ga = ga;
        String name = "Steve";
        speed = 1;
        int maxSlimeHp = 8;
        greenSlimeHp = maxSlimeHp;
        collision = true;
        solidArea = new Rectangle(0,0, 42,33);


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
            return new Image(new FileInputStream("Slime_Time/res/slimes/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
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
        gcObj.strokeRect(screenX + 3, screenY + 15, 42, 33);
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
        //checkCollision();
        collisionOn = false;
        int tempWorldX = worldX;
        int tempWorldY = worldY;
        //ga.cChecker.checkTile(this);
        //ga.cChecker.checkMonster(this);
        //ga.cChecker.checkPlayer(this);
        //ga.cChecker.checkRock(this);
        //ga.cChecker.checkTree(this);

        //collision checker here

        if (!collisionOn) {
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
        ((Rectangle)solidArea).setX(worldX + 3);
        ((Rectangle)solidArea).setY(worldY + 15);

        int xDistance = Math.abs(worldX - ga.player.worldX);
        int yDistance = Math.abs(worldY - ga.player.worldY);
        int tileDistance = (xDistance + yDistance)/ga.TILE_SIZE;

        onPath = true;

        // De-aggro monster if you get 20 tiles away
/*        if(onPath == true && tileDistance > 20) {
            onPath = false;
        }*/
    }

    public void setAction() {

        if(onPath == true) {

            int goalCol = (ga.player.worldX + (int)ga.player.solidArea.getLayoutX())/ga.TILE_SIZE;
            int goalRow = (ga.player.worldY + (int)ga.player.solidArea.getLayoutY())/ga.TILE_SIZE;

            searchPath(goalCol,goalRow);

        }
        else {
            actionLockCounter++;
            //System.out.println(actionLockCounter);
            Random rand = new Random();

        if (actionLockCounter == 30) {
            int i = rand.nextInt(100) + 1;

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
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + (int)solidArea.getLayoutX())/ga.TILE_SIZE;
        int startRow = (worldY + (int)solidArea.getLayoutY())/ga.TILE_SIZE;

        ga.pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(ga.pFinder.search() == true) {

            int nextX = ga.pFinder.pathList.get(0).col * ga.TILE_SIZE;
            int nextY = ga.pFinder.pathList.get(0).row * ga.TILE_SIZE;

            int enLeftX = worldX + (int)solidArea.getLayoutX();
            int enRightX = worldX + (int)solidArea.getLayoutX() + (int)((Rectangle)solidArea).getWidth();
            int enTopY = worldY + (int)solidArea.getLayoutY();
            int enBottomY = worldY + (int)solidArea.getLayoutY() + (int)((Rectangle)solidArea).getHeight();

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + ga.TILE_SIZE) {
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + ga.TILE_SIZE) {
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + ga.TILE_SIZE) {
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {

                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {

                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {

                direction = "down";
                checkCollision();
                if(collisionOn = true) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {

                direction = "down";
                checkCollision();
                if(collisionOn = true) {
                    direction = "right";
                }
            }

            // for slime to path to a specific tile
/*            int nextCol = ga.pFinder.pathList.get(0).col;
            int nextRow = ga.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }*/
        }
    }

    public void checkCollision() {
        collisionOn = false;
        ga.cChecker.checkTile(this);
        ga.cChecker.checkResource(this);
        ga.cChecker.checkMonster(this);
        ga.cChecker.checkPlayer(this);
    }
/*    public void speak() {
        onPath = true;
    }*/
}
