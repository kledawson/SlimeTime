package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GameApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    public GameApplication ga;
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

    //Item attributes
    public int attackValue;
    public int defenseValue; //tbd if will be used

    public List<Image> images = new ArrayList<>();
    public String direction; // Direction Entity is Moving
    public int spriteCounter = 0; // Animation Timer
    public int spriteNum = 1; // Sprite Frame
    public Rectangle solidArea; // Hit box
    public int solidAreaDefaultX, solidAreaDefaultY; // Hit box Coordinates
    public boolean collisionOn = false; // Collision State
}
