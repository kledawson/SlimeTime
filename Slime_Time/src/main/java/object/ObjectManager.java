package object;

import main.GameApplication;


public class ObjectManager {

    private GameApplication ga;

    public ObjectManager(GameApplication ga) {
        this.ga = ga;
    }


    //adds items to the object manager (to be used for resource management)
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





