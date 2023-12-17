package monster;

import ai.Node;
import ai.PathFinder;
import entity.Entity;
import interactive_resources.SuperResource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GameApplication;
import object.OBJ_Gold;

import java.io.FileInputStream;
import java.util.Random;

public class GreenSlime extends Entity {
    GameApplication ga;

    private int actionLockCounter = 0;


    public GreenSlime(GameApplication ga) {
        this.ga = ga;
        String name = "Steve";
        speed = 1;
        int maxSlimeHp = 8;
        life = maxSlimeHp;
        collision = true;
        solidArea = new Rectangle(0,0, 42,33);
        attackValue = 1;

        getGreenSlimeImage();
    }


    public void getGreenSlimeImage() {
        setupGreenSlime("greenSlime1");
        setupGreenSlime("greenSlime2");
        setupGreenSlime("greenSlime3");
        setupGreenSlime("greenSlime4");
        setupGreenSlime("greenSlime5");
    }


    public void setupGreenSlime(String imageName) {
        try {
            images.add(new Image(new FileInputStream("Slime_Time/res/slimes/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
        }
        catch (Exception e) {
            try {
                images.add(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            }
            catch (Exception ex) {
                System.err.println("Error loading image: " + imageName);
                ex.printStackTrace();
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
            gc.drawImage(images.get(-1 + spriteNum) , screenX, screenY);
        }
        gc.strokeRect(screenX + 3, screenY + 15, 42, 33);
    }

    public void greenSlimeDamage(int damage) {
        if (iFrameCount >= 20) {
            life -= damage;
            iFrameCount = 0;
            // updateGreenSlimeImage();
        }
        System.out.println("Monster taking damage!");
    }

    public void update(int index) {
        setAction();
        checkCollision();
        collisionOn = false;
        ga.cChecker.checkTile(this);
        ga.cChecker.checkResource(this);

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        ((Rectangle)solidArea).setX(worldX + 3);
        ((Rectangle)solidArea).setY(worldY + 15);

        int xDistance = Math.abs(worldX - ga.player.worldX);
        int yDistance = Math.abs(worldY - ga.player.worldY);
        int tileDistance = (xDistance + yDistance)/ga.TILE_SIZE;

        if(!onPath && tileDistance < 100) {
            int i = new Random().nextInt(100)+1;
            if(i > 50) {
                onPath = true;
            }
        }

        // Slimes de-aggro whenever player leaves 20 tiles, but in our case slimes aggro from 100 tiles away (always aggro)
/*        if(onPath == true && tileDistance > 20) {
            onPath = false;
        }*/

        if (life == 0) {
            ga.greenSlime.get(index).monsterKill(index);
        }

        // Jumping Animation
        if (spriteCounter > 10) {
            switch (spriteNum) {
                case 1 -> spriteNum = 2;
                case 2 -> spriteNum = 3;
                case 3 -> spriteNum = 4;
                case 4 -> spriteNum = 5;
                case 5 -> spriteNum = 1;
            }
                spriteCounter = 0;
        }
        ++spriteCounter;

        ++iFrameCount;
    }

    public void monsterKill(int index) {
        //saving coordinates right before despawn
            int worldX = ga.greenSlime.get(index).worldX;
            int worldY = ga.greenSlime.get(index).worldY;
            ga.greenSlime.set(index, null);
            // Add the new object to objM
            ga.objM.addItem(new OBJ_Gold(ga), worldX, worldY);

            //spawn a new slime after
        int[][] spawnPoints = {
                {70 * ga.TILE_SIZE, 64 * ga.TILE_SIZE},
                {70 * ga.TILE_SIZE, 50 * ga.TILE_SIZE},
                {70 * ga.TILE_SIZE, 75 * ga.TILE_SIZE},
                {70 * ga.TILE_SIZE, 45 * ga.TILE_SIZE}
        };

        // Randomly select a spawn point index
        int spawnIndex = (int) (Math.random() * spawnPoints.length);

        // Get the coordinates of the randomly selected spawn point
        int[] selectedSpawnPoint = spawnPoints[spawnIndex];
        int randomX = selectedSpawnPoint[0];
        int randomY = selectedSpawnPoint[1];



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

        }
    }

    public void checkCollision() {
        collisionOn = false;
        ga.cChecker.checkTile(this);
        ga.cChecker.checkResource(this);
        ga.cChecker.checkMonster(this);
        ga.cChecker.checkPlayer(this);
    }

}
