package monster;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GameApplication;
import object.OBJ_Gold;

import java.io.FileInputStream;
import java.util.Random;

public class GreenSlime extends Entity {
    GameApplication ga;
    public Image greenSlime1, greenSlime2, greenSlime3, greenSlime4, greenSlime5;

    private int actionLockCounter = 0;


    public GreenSlime(GameApplication ga) {
        this.ga = ga;
        String name = "Steve";
        speed = 1;
        int maxSlimeHp = 8;
        life = maxSlimeHp;
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
        int screenX = worldX - ga.player.worldX + ga.player.screenX;
        int screenY = worldY - ga.player.worldY + ga.player.screenY;

        // Draws Only What Camera Can See
        if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
                worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
                worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
                worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
            gc.drawImage(greenSlime1, screenX, screenY);
        }
        gc.strokeRect(screenX + 3, screenY + 15, 42, 33);
    }

    public void updateGreenSlimeImage() {

        if (life == 7 || life == 8) {
            greenSlime1 = setupGreenSlime("greenSlime1");
        }
        else if (life == 5 || life == 6) {
            greenSlime2 = setupGreenSlime("greenSlime1");
        }
        else if (life == 3 || life == 4) {
            greenSlime3 = setupGreenSlime("greenSlime1");
        }
        else if (life == 1 || life == 2) {
            greenSlime4 = setupGreenSlime("greenSlime1");
        }
        else if (life == 0) {
            greenSlime5 = setupGreenSlime("greenSlime1");
        }
        else {
            greenSlime1 = setupGreenSlime("greenSlime1");
        }
    }

    public void greenSlimeDamage() {
        if (life > 0) {
            life--;
            updateGreenSlimeImage();
        }
        System.out.println("Monster taking damage!");
    }

    public void updateGreenSlime(int index) {
        setAction();
        checkCollision();
        collisionOn = false;
        int tempWorldX = worldX;
        int tempWorldY = worldY;
        ga.cChecker.checkTile(this);
        ga.cChecker.checkResource(this);
//        ga.cChecker.checkMonster(this);
        //ga.cChecker.checkPlayer(this);

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

        if(!onPath && tileDistance < 5) {
            int i = new Random().nextInt(100)+1;
            if(i > 50) {
                onPath = true;
            }
        }

        if (life == 0) {
            ga.greenSlime.get(index).monsterKill(index);
        }
        //onPath = true;

        // De-aggro monster if you get 20 tiles away
/*        if(onPath == true && tileDistance > 20) {
            onPath = false;
        }*/
    }

    public void monsterKill(int index) {
        //saving coordinates right before despawn
            int worldX = ga.greenSlime.get(index).worldX;
            int worldY = ga.greenSlime.get(index).worldY;
            ga.greenSlime.set(index, null);
            // Add the new object to objM
            ga.objM.addItem(new OBJ_Gold(ga), worldX, worldY);

            //spawn a new slime after
        int centerX = 55 * ga.TILE_SIZE;
        int centerY = 50 * ga.TILE_SIZE;
        int respawnRange = 40; // Adjust this value for the range around the center

        int randomX = (int) (centerX - respawnRange + Math.random() * (15 * respawnRange));
        int randomY = (int) (centerY - respawnRange + Math.random() * (15 * respawnRange));



        ga.Monster.spawnMonster(new GreenSlime(ga), randomX, randomY);


    }

    public void setAction() {

        if(onPath) {

            int goalCol = (int)((Rectangle)ga.player.solidArea).getX()/ga.TILE_SIZE;
            int goalRow = (int)((Rectangle)ga.player.solidArea).getY()/ga.TILE_SIZE;

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

        int startCol = (int)((Rectangle)solidArea).getX()/ga.TILE_SIZE;
        int startRow = (int)((Rectangle)solidArea).getY()/ga.TILE_SIZE;

        ga.pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(ga.pFinder.search()) {

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
                if(collisionOn) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {

                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {

                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {

                direction = "down";
                checkCollision();
                if(collisionOn) {
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
