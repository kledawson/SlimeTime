# CS151-Slime_Time
An action-packed 2D videogame made using the Java language and JavaFX.

You will play as a nameless farmer who has been thrust into action due to an unexpecting Slime invasion on his ranch. Use your trusty tools to fend them off, becoming stronger and stronger as time goes on.

![image](https://github.com/JQBNguyen/CS151-Slime_Time/assets/120300677/4731a70c-1f1b-4a73-9420-e0ad490b7310)

## Team 4 Members
These are the team members involved in the project and their contributions
### Johnny Nguyen
- Base Code Structure: Established the basic program structure for the JavaFX 2D game to function including the basics of the player character, movement, and item pick-ups.
- Weapon / Combat Functionality: Implemented mouse tracking and input detection for weapon attacks. Developed two different weapons that can "attack" on set intervals.
- Collision Functionality: Implemented collision functionality for each category of gameplay asset (ie. Player, Slimes, Resources, etc..). Determined functionality for movement boundaries and hit-detection hit-boxes.
- Improved Combat Hit-Detection: Implemented the idea of "I-frames" where an Entity is invulnerable for a chosen period before being able to be hit again.
- Nature of Gameplay: Implemented a system of timely-increased difficulty increases. Every 3 minutes, more slimes would chase the player as well as the strength of each slime.
- Sound / Music Implementation: Implemented sound and music capabilities for application.
- AI Bugfixing: Troubleshooted the initial Slime AI tracking algorithm to help fully develop AI system for monsters.
- UI Adjustments: I reorganized the UI menu screens for better organization on screen.
- Pixel Art: Designed and created all artistic assets for SlimeTime.
### Travis Nguyen
- Basic game framework (we ended up using Johnny Nguyen's base to work on, but we all did basic code structure on our own to understand better how to branch from the tutorial)
- Diagonal movement -> I did full diagonal movement implementation, including making sure collision worked correctly to correspond with player movement
- Initial UI concept & format -> (I did the initial UI before Johnny implemented his images for the UI, and thought of the basic logic/implementation on getting it to work with the Game Application)
- Title screen implementation -> did full title screen using screenshots of the game and Johnny's pixel art
- Resource/monster dropping items after player destroys them -> Coded implementation on having monsters and resources drop items when they are 0 hp.
- Player inventory system (picking up objects, using objects) -> As part of the UI-making process, I set up a functioning inventory system for the player,
including methods to search the player inventory and check if an object can be picked up. I also made it so that items can "stack," showing the number of items a player has
with a number on the bottom right of the item image
- Settings objects (gold, wood, stone) and green slimes -> Streamlined adding the monsters and resources into the game so that you only have to call a method instead of manually setting the stats
(i.e., coordinates)
- Monster respawn -> Did the logic make sure that after a player slays a slime, another slime respawns in 4 different respawn locations, with the
The respawn point is chosen randomly out of the 4 set points.
- Monster damage -> Made sure that the player can damage the monster
- Small code cleanup -> did some initial code cleanup (deleting commented-out code, removing unnecessary methods, etc.)
### Dawson Le

## Running the Game
Run the Java software program on a personal computer using a Java environment with the JavaFX library installed and JDK version 20.0.1.
Clone this repository and run from the "GameApplication" file from your IDE (we used IntelliJ Idea).

## Game Controls
- Use the 'WASD' keys to move the player character in the cardinal and ordinal directions.
- Use the mouse cursor to aim attacks in certain directions
  - Left Click for Melee Attack
  - Right Click for Ranged Attack
- U Key for Upgrade Screen
- C Key for Character Screen
- P Key for Pause Screen

## Features
- Player vs. Entity Combat System
  - Melee & Projectile Attacks (Mouse Controlled Combat With Cursor Tracking)
- Custom Map / Art
- Collision-Based Damage
  - Slimes do Contact Damage
- Slime AI Pathfinding
  - Utilizes A* Algorithm
- Resource Gathering
  - Trees & Rocks
- Item Pick-ups
  - Wood, Stone, & Gold
- Inventory & Upgrading
- 8 Directional Player Movement
  - Cardinal & Ordinal Directions
- Music & Sound Effects
- Multiple Menu Screens
  - Upgrade, Character Stats, Pause
 
## Plan and Approach
- Learn Basics of 2D Game Development from YouTube Playlist [2]
  - How to Run a 2D Game on Java Environment
  - Learn JavaFX
  - Basic Components of Game (ie. Character, Background, Movement)
- Expand Upon Code Foundation Using 4 Pillars of OOD
  - Create Custom Weapons
  - Create Custom Entities
  - Create Custom Items
  - Create Custom Resources
  - Program Custom Game Mechanics
  - Utilize Strategy Design Patterns
  - Favor Composition over inheritance 

## Problems / Issues
- Difficulty in creating .jar or .exe files for ease of running the program.
- Collision Detection Implementation During Development
- UI Creation With JavaFX's GraphicsContext (initial difficulty with implementing buttons with project structure)

## References
[1] Taking inspiration off of "[Vampire Survivors](https://store.steampowered.com/app/1794680/Vampire_Survivors/)" game. 

[2] [How to Make a 2D Game in Java](https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)

## Special Thanks
OST Composer - Bryan Nguyen

![](https://github.com/JQBNguyen/CS151-Slime_Time/blob/main/slimetimegif.gif)
  
