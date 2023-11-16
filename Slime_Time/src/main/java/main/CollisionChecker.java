package main;

import entity.Entity;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CollisionChecker {
    GameApplication ga;

    public CollisionChecker(GameApplication ga) {
        this.ga = ga;
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
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left_up":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum4 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision || ga.tileM.tile[tileNum3].collision || ga.tileM.tile[tileNum4].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left_down":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum4 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision || ga.tileM.tile[tileNum3].collision || ga.tileM.tile[tileNum4].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right_up":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum4 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision || ga.tileM.tile[tileNum3].collision || ga.tileM.tile[tileNum4].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right_down":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum4 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision || ga.tileM.tile[tileNum3].collision || ga.tileM.tile[tileNum4].collision) {
                    entity.collisionOn = true;
                }
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
