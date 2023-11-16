package tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameApplication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TileManager {

    GameApplication ga;
    public Tile[] tile;
    public int[][] mapTileNum;
    public List<String> fileNames = new ArrayList<>();
    public List<String> tileCollisions = new ArrayList<String>();
    public List<String> tileNameCollision = new ArrayList<>();

    // Generates Empty Tiles
    public TileManager(GameApplication ga) {
        this.ga = ga;
        tile = new Tile[200];
        mapTileNum = new int[ga.MAX_WORLD_COL][ga.MAX_WORLD_ROW];

        getTileImage();
        // loadMap("res\\map\\map_name.txt");
        loadMap("Slime_Time/res/maps/slimetimemap.txt");
    }

    // Loads Tile Images
    public void getTileImage() {
        try {
            tileNameCollision = Files.readAllLines(Paths.get("Slime_Time/res/maps/tileNameCollision.txt"));
        }
        catch (Exception e) {
            e.printStackTrace();;
        }

        for (int i = 0; i < tileNameCollision.size(); i += 2) {
            fileNames.add(tileNameCollision.get(i));
            tileCollisions.add(tileNameCollision.get(i + 1));
        }

        for (int i = 0; i < fileNames.size(); ++i) {
            System.out.println(i + " " + fileNames.get(i) + " " + tileCollisions.get(i));
            setup(i, fileNames.get(i), tileCollisions.get(i).compareTo("y") == 0);
        }

        /*
        setup(0, "grass_tile_1", false);
        setup(1, "grass_tile_2", false);
        */
    }

    // Utility Method to Load Tile Images
    public void setup(int index, String imageName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].setImage(new Image(new FileInputStream("Slime_Time/res/tiles/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            tile[index].collision = collision;
        }
        catch (Exception e) {
            try {
                tile[index].setImage(new Image(new FileInputStream("Slime_Time/res/tiles/no_sprite.png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            }
            catch (Exception ex) {
                ex.printStackTrace();
                tile[index].setImage(null);
            }
        }
    }

    // Reads txt File to Load Map
    public void loadMap(String mapFile) {
        try {
            File myFile = new File(mapFile);
            Scanner myReader = new Scanner(myFile);
            int col = 0;
            int row = 0;
            while (col < ga.MAX_WORLD_COL && row < ga.MAX_WORLD_ROW && myReader.hasNextLine()) {
                String line = myReader.nextLine();
                while (col < ga.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    ++col;
                }
                if (col == ga.MAX_WORLD_COL) {
                    col = 0;
                    ++row;
                }
            }
        }
        catch (Exception e) {
            try {
                File myFile = new File("Slime_Time/res/maps/default_50x50_map.txt");
                Scanner myReader = new Scanner(myFile);
                int col = 0;
                int row = 0;
                while (col < ga.MAX_WORLD_COL && row < ga.MAX_WORLD_ROW && myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    while (col < ga.MAX_WORLD_COL) {
                        String[] numbers = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        ++col;
                    }
                    if (col == ga.MAX_WORLD_COL) {
                        col = 0;
                        ++row;
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Renders Map
    public void render(GraphicsContext gc) {
        GraphicsContext gcTile = gc;
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < ga.MAX_WORLD_COL && worldRow < ga.MAX_WORLD_ROW) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * ga.TILE_SIZE;
            int worldY = worldRow * ga.TILE_SIZE;
            int screenX = worldX - ga.player.worldX + ga.player.screenX;
            int screenY = worldY - ga.player.worldY + ga.player.screenY;

            // Draws Only if On Camera
            if (worldX + ga.TILE_SIZE > ga.player.worldX - ga.player.screenX &&
                    worldX - ga.TILE_SIZE  < ga.player.worldX + ga.player.screenX &&
                    worldY + ga.TILE_SIZE  > ga.player.worldY - ga.player.screenY &&
                    worldY - ga.TILE_SIZE  < ga.player.worldY + ga.player.screenY) {
                gcTile.drawImage(tile[tileNum].getImage(), screenX, screenY);
            }
            ++worldCol;
            if (worldCol == ga.MAX_WORLD_COL) {
                worldCol = 0;
                ++worldRow;
            }
        }
    }

}
