package interactive_resources;

import javafx.scene.shape.Rectangle;
import main.GameApplication;
import entity.Entity;
import object.SuperObject;


public class SuperResource extends Entity {

    public SuperResource(GameApplication ga, int worldCol, int worldRow) {
        this.ga = ga;
        worldX = worldCol * ga.TILE_SIZE;
        worldY = worldRow * ga.TILE_SIZE;
        solidArea = new Rectangle(worldX, worldY, ga.TILE_SIZE, ga.TILE_SIZE);
        name = "Resource";
        maxLife = 4;
        life = maxLife;
        collision = true;
        iFrameCount = 0;
    }

    public void takeDamage(int damage) {
        if (life > 0 && iFrameCount > 30) {
            --life;
            iFrameCount = 0;
        }
        System.out.println("TAKING DAMAGE!");
    }
    public void removeFromGame(int index, SuperObject object) {
    //saving coordinates for item spawn
            int worldX = ga.resource[index].worldX;
            int worldY = ga.resource[index].worldY;
            //removing resource from game
            ga.resource[index] = null;
            // Add the new object to objM
            ga.objM.addItem(object, worldX, worldY);

    }

}