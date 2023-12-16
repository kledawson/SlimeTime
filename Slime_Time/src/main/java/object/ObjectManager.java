package object;

import main.GameApplication;


public class ObjectManager {

    GameApplication ga;

    public ObjectManager(GameApplication ga) {
        this.ga = ga;
    }

    // Loads all Object Positions
    public void setObject() {
//        addItem(new OBJ_Wood(ga), 66 * ga.TILE_SIZE,66 * ga.TILE_SIZE);
//        addItem(new OBJ_Wood(ga), 66 * ga.TILE_SIZE,61 * ga.TILE_SIZE);
//        addItem(new OBJ_Wood(ga), 67 * ga.TILE_SIZE,61 * ga.TILE_SIZE);
//        addItem(new OBJ_Stone(ga), 66 * ga.TILE_SIZE,68 * ga.TILE_SIZE);
//        addItem(new OBJ_Stone(ga), 66 * ga.TILE_SIZE,63 * ga.TILE_SIZE);
//        addItem(new OBJ_Stone(ga), 67 * ga.TILE_SIZE,68 * ga.TILE_SIZE);
//        addItem(new OBJ_Gold(ga), 68 * ga.TILE_SIZE,64 * ga.TILE_SIZE);
//        addItem(new OBJ_Gold(ga), 69 * ga.TILE_SIZE,64 * ga.TILE_SIZE);
//        addItem(new OBJ_Gold(ga), 70 * ga.TILE_SIZE,64 * ga.TILE_SIZE);
//        addItem(new OBJ_Gold(ga), 71 * ga.TILE_SIZE,64 * ga.TILE_SIZE);
    }

    public void addItem(SuperObject item, int worldCol, int worldRow) {
        // Set properties of the item before adding it to the array
        // Find the next available slot in the object array to add the item
                item.worldX = worldCol;
                item.worldY = worldRow;
                item.solidArea.setX(worldCol);
                item.solidArea.setY(worldRow);
                ga.obj.add(item);

            }
        }





