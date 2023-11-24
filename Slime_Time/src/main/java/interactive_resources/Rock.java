package interactive_resources;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameApplication;

import java.io.FileInputStream;

public class Rock extends Entity {
    GameApplication ga;
    public Image rock, rock_break1, rock_break2, rock_break3, rock_broken, error; // Rock Sprites
    public int rockLife;


    public Rock(GameApplication ga) {
        this.ga = ga;
        String name = "Rock";
        int speed = 0;
        int maxRockLife = 8;
        rockLife = maxRockLife;

        getRockImage();
        updateRockImage();
    }


    public void getRockImage() {
        rock = setupRock("rock");
        /*rock_break1 = setupRock("rock_break_1");
        rock_break2 = setupRock("rock_break_2");
        rock_break3 = setupRock("rock_break_3");*/
    }

    public Image setupRock(String imageName) {
        try {
            return new Image(new FileInputStream("Slime_Time/res/interactive_resources/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false);
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
            gcObj.drawImage(rock, screenX, screenY);
        }
    }

    public void updateRockImage() {

        if (rockLife == 7 || rockLife == 8) {
            rock = setupRock("rock");
        }
        else if (rockLife == 5 || rockLife == 6) {
            rock = setupRock("rock_break_1");
        }
        else if (rockLife == 3 || rockLife == 4) {
            rock = setupRock("rock_break_2");
        }
        else if (rockLife == 1 || rockLife == 2) {
            rock = setupRock("rock_break_3");
        }
        else if (rockLife == 0) {
            rock = setupRock("rock_broken");
        }
        else {
            rock = setupRock("error");
        }
    }

    public void rockDamage() {
        rockLife--;
        updateRockImage();
    }
}
