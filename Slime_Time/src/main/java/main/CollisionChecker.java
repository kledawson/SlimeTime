package main;

import entity.Entity;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class CollisionChecker {
    GameApplication ga;

    public CollisionChecker(GameApplication ga) {
        this.ga = ga;
    }

    private void checkTileUtil(Entity entity, int tileNum1, int col1, int row1) {
        if (ga.tileM.tile[tileNum1].collision) {
//            if (entity != ga.player) {
//                entity.collisionOn = true;
//                return;
//            }
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
        int entityLeftWorldX = (int) (((Rectangle)entity.solidArea).getX());
        int entityRightWorldX = (int) (((Rectangle)entity.solidArea).getX() + ((Rectangle)entity.solidArea).getWidth());
        int entityTopWorldY = (int) (((Rectangle)entity.solidArea).getY());
        int entityBottomWorldY = (int) (((Rectangle)entity.solidArea).getY() + ((Rectangle)entity.solidArea).getHeight());

        // Calculates Tile Row/Col of Entity
        int entityLeftCol = entityLeftWorldX / ga.TILE_SIZE;
        int entityRightCol = entityRightWorldX  / ga.TILE_SIZE;
        int entityTopRow = entityTopWorldY / ga.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY  / ga.TILE_SIZE;


        int tileNum1, tileNum2, tileNum3;

        // Determines Collision by Checking Which Tiles the Entity is Adjacent to
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityTopRow);
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityBottomRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityLeftCol, entityBottomRow);
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityRightCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
            }
            case "left_up" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityLeftCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityRightCol, entityTopRow);
            }
            case "left_down" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityLeftCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityLeftCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityRightCol, entityBottomRow);
            }
            case "right_up" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                checkTileUtil(entity, tileNum1, entityRightCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityLeftCol, entityTopRow);
            }
            case "right_down" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / ga.TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.TILE_SIZE;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                checkTileUtil(entity, tileNum1, entityRightCol, entityTopRow);
                checkTileUtil(entity, tileNum2, entityRightCol, entityBottomRow);
                checkTileUtil(entity, tileNum3, entityLeftCol, entityBottomRow);
            }
        }
    }

    // Checks Collision b/w Entity and Object
    // Returns Which Index of Which Object is Being Collided
    public int checkObject(Entity entity) {
        int index = 999;
        for (int i = 0; i < ga.obj.size(); ++i) {
            if (ga.obj.get(i) != null) {
                Shape intersection = Shape.intersect(entity.solidArea, ga.obj.get(i).solidArea);
                if (intersection.getBoundsInParent().getWidth() != -1) {
                    if (ga.obj.get(i).collision) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    public List<Integer> checkMonster(Entity entity) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < ga.greenSlime.size(); ++i) {
            if (ga.greenSlime.get(i) != null && entity != ga.greenSlime.get(i)) {
                Shape intersection = Shape.intersect(entity.solidArea, ga.greenSlime.get(i).solidArea);
                if (intersection.getBoundsInParent().getWidth() != -1) {
                    if (ga.greenSlime.get(i).collision) {
                        entity.collisionOn = true;
                        indices.add(i);
                    }
                }
            }
        }
        return indices;
    }

    public void checkPlayer(Entity entity) {
        Shape intersection = Shape.intersect(entity.solidArea, ga.player.solidArea);
        if (intersection.getBoundsInParent().getWidth() != -1) {
            entity.collisionOn = true;
        }
    }

    public List<Integer> checkResource(Entity entity) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < ga.resource.length; ++i) {
            if (ga.resource[i] != null) {
                Shape intersection = Shape.intersect(entity.solidArea, ga.resource[i].solidArea);
                if (intersection.getBoundsInParent().getWidth() != -1) {
                    System.out.println("COLLIDING");
                    if (ga.resource[i].collision) {
                        entity.collisionOn = true;
                        indices.add(i);
                    }
                }
            }
        }
        return indices;
    }
}
