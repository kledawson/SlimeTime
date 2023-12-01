package interactive_resources;

import main.GameApplication;
import object.Example_Object;

public class ResourceManager {

    GameApplication ga;

    public ResourceManager(GameApplication ga) {
        this.ga = ga;
    }

    public void setResource() {

        ga.resource[0] = new Rock(ga, 75, 45);
        ga.resource[1] = new Rock(ga, 75, 46);
        ga.resource[2] = new Rock(ga, 75, 47);
        ga.resource[3] = new Rock(ga, 75, 48);
        ga.resource[4] = new Rock(ga, 75, 49);
        ga.resource[5] = new Tree(ga, 75, 50);
        ga.resource[6] = new Tree(ga, 75, 51);
        ga.resource[7] = new Tree(ga, 75, 52);
        ga.resource[8] = new Tree(ga, 75, 53);
        ga.resource[9] = new Tree(ga, 75, 54);

    }
}
