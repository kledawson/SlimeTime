package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Entity {
    public int worldX, worldY; // World Coordinates
    public int speed; // Speed of Entity (# of Pixels Walked per Tick)

    //Character Attributes
    public int maxLife;
    public int life;
    public int level;
    public int strength; //tbd if will be used
    public int dexterity; //tbd if will be used
    public int attack;
    public int defense; //tbd if will be used
    public int exp;
    public int nextLevelExp;
    public Entity currentWeapon;

    //Item attributes
    public int attackValue;
    public int defenseValue; //tbd if will be used

    public Image up0, up1, up2, upIdle,
            down0, down1, down2, downIdle,
            left0, left1, left2, leftIdle,
            right0, right1, right2, rightIdle,
            left_up0, left_up1, left_up2, left_upIdle,
            right_up0, right_up1, right_up2, right_upIdle,
            left_down0, left_down1, left_down2, left_downIdle,
            right_down0, right_down1, right_down2, right_downIdle; // Sprites
    public String direction; // Direction Entity is Moving
    public int spriteCounter = 0; // Animation Timer
    public int spriteNum = 1; // Sprite Frame
    public Rectangle solidArea; // Hit box
    public int solidAreaDefaultX, solidAreaDefaultY; // Hit box Coordinates
    public boolean collisionOn = false; // Collision State
}
