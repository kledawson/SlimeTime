package interactive_resources;

import main.GameApplication;
import java.io.File;
import java.util.Scanner;

public class ResourceManager {

    GameApplication ga;
    int[] woodXY;

    public ResourceManager(GameApplication ga) {
        this.ga = ga;
        woodXY = new int[80];
    }

    public void setResource() {
        try {
            Scanner scanner = new Scanner(new File("Slime_Time/res/files/tree_coords.txt"));
            int i = 0;
            while (scanner.hasNextInt()) {
                woodXY[i++] = scanner.nextInt();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int j = 0;
        for (int i = 0; i < 80; i += 2) {
            ga.resource[j++] = new Tree(ga, woodXY[i], woodXY[i + 1]);
        }

    }
}
