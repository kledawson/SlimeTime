package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Entity {
    public int worldX, worldY; // World Coordinates
    public int speed; // Speed of Entity (# of Pixels Walked per Tick)
    public Image up1, up2, down1, down2, left1, left2, right1, right2, upIdle, downIdle, leftIdle, rightIdle; // Sprites
    public String direction; // Direction Entity is Facing
    public int spriteCounter = 0; // Animation Timer
    public int spriteNum = 1; // Sprite Frame
    public Rectangle solidArea; // Hit box
    public int solidAreaDefaultX, solidAreaDefaultY; // Hit box Coordinates
    public boolean collisionOn = false; // Collision State
}
