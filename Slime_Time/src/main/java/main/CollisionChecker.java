package main;

import entity.Entity;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CollisionChecker {
    GameApplication ga;

    public CollisionChecker(GameApplication ga) {
        this.ga = ga;
    }

    private void checkTileUtil(Entity entity, int tileNum1, int col1, int row1) {
        if (ga.tileM.tile[tileNum1].collision) {
            ga.tileM.tile[tileNum1].solidArea.setTranslateX(col1 * ga.TILE_SIZE);
            ga.tileM.tile[tileNum1].solidArea.setTranslateY(row1 * ga.TILE_SIZE);
            Shape intersection = Shape.intersect(entity.solidArea, ga.tileM.tile[tileNum1].solidArea);
            if (intersection.getBoundsInParent().getWidth() != -1) {
                entity.collisionOn = true;
            }
            ga.tileM.tile[tileNum1].solidArea.setTranslateX(col1 * ga.TILE_SIZE * -1);
            ga.tileM.tile[tileNum1].solidArea.setTranslateY(row1 * ga.TILE_SIZE * -1);
        }
    }

    // Checks Collision b/w Entity and Tile
    public void checkTile(Entity entity) {
        // Calculates Coordinates of Entity
        int entityLeftWorldX = (int) (entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int) (entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.solidArea.getY() + entity.solidArea.getHeight());

        // Calculates Tile Row/Col of Entity
        int entityLeftCol = entityLeftWorldX / ga.TILE_SIZE;
        int entityRightCol = entityRightWorldX  / ga.TILE_SIZE;
        int entityTopRow = entityTopWorldY / ga.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY  / ga.TILE_SIZE;


        int tileNum1, tileNum2, tileNum3, tileNum4;

        // Determines Collision by Checking Which Tiles the Entity is Adjacent to
        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityTopRow);
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityBottomRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityLeftCol, entityBottomRow);
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityRightCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
                break;
            case "left_up":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityLeftCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityRightCol, entityTopRow);
                break;
            case "left_down":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityLeftCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityRightCol, entityBottomRow);
                break;
            case "right_up":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                checkTileUtil(entity, tileNum1, entityRightCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityLeftCol, entityTopRow);
                break;
            case "right_down":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityRightCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityLeftCol, entityBottomRow);
                break;
        }
    }

    // Checks Collision b/w Entity and Object
    // Returns Which Index of Which Object is Being Collided
    public int checkObject(Entity entity) {
        int index = 999;
        for (int i = 0; i < ga.obj.length; ++i) {
            if (ga.obj[i] != null) {
                Shape intersection = Shape.intersect(entity.solidArea, ga.obj[i].solidArea);
                if (intersection.getBoundsInParent().getWidth() != -1) {
                    if (ga.obj[i].collision) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    public int checkMonster(Entity entity) {
        int index = 999;
        return index;
    }

    public int checkResource(Entity entity) {
        int index = 999;
        return index;
    }
}
