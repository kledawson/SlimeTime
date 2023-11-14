package main;

import entity.Entity;

public class CollisionChecker {
    GameApplication ga;

    public CollisionChecker(GameApplication ga) {
        this.ga = ga;
    }

    // Checks Collision b/w Entity and Tile
    public void checkTile(Entity entity) {
        // Calculates Coordinates of Entity
        int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.worldX + entity.solidArea.getX() + entity.solidArea.getWidth() - 4);
        int entityTopWorldY = (int) (entity.worldY + entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.getY() + entity.solidArea.getHeight() - 4);

        // Calculates Tile Row/Col of Entity
        int entityLeftCol = entityLeftWorldX / ga.TILE_SIZE;
        int entityRightCol = entityRightWorldX  / ga.TILE_SIZE;
        int entityTopRow = entityTopWorldY / ga.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY  / ga.TILE_SIZE;

        int tileNum1, tileNum2;

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
        }
    }

    // Checks Collision b/w Entity and Object
    // Returns Which Index of Which Object is Being Collided
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        // Iterates Through All Non-null Objects
        for (int i = 0; i < ga.obj.length; ++i) {
            if (ga.obj[i] != null) {
                // Determines Entity Hit box Coordinates
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                // Determines Object Hit box Coordinates
                ga.obj[i].solidArea.setX(ga.obj[i].worldX + ga.obj[i].solidArea.getX());
                ga.obj[i].solidArea.setY(ga.obj[i].worldY + ga.obj[i].solidArea.getY());

                // Sets Entity Hit box Coordinates
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        break;
                    case "down":
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        break;
                    case "left":
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        break;
                    case "right":
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        break;
                }

                // Checks if Areas Intersect and Applies Collision When Applicable
                if (entity.solidArea.intersects(ga.obj[i].solidArea.getX() + 1, ga.obj[i].solidArea.getY() + 1, 46, 46)) {
                    if (ga.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                // Resets Entity and Object Hit-box Coordinates
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                ga.obj[i].solidArea.setX(ga.obj[i].solidAreaDefaultX);
                ga.obj[i].solidArea.setY(ga.obj[i].solidAreaDefaultY);
            }
        }

        return index;
    }

    public int checkEntity(Entity entity) {
        int index = 999;
        return index;
    }
}
