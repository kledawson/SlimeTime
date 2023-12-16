package tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
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
    public List<String> tileCollisions = new ArrayList<>();
    public List<String> tileNameCollision = new ArrayList<>();
    public List<Polygon> tileSolidAreas = new ArrayList<>();
    boolean drawPath = true;

    // Generates Empty Tiles
    public TileManager(GameApplication ga) {
        this.ga = ga;
        tile = new Tile[200];
        mapTileNum = new int[ga.MAX_WORLD_COL][ga.MAX_WORLD_ROW];

        getTileSolidAreas();
        getTileImage();
        loadMap("Slime_Time/res/maps/slimetimemap.txt");
    }

    public void getTileSolidAreas() {
        for (int i = 0; i < 26; ++i) {
            tileSolidAreas.add(new Polygon());
        }
        tileSolidAreas.get(0).getPoints().addAll(
                0.0, 0.0,
                0.0, 48.0,
                48.0, 0.0,
                48.0, 48.0
        );
        tileSolidAreas.get(1).getPoints().addAll(
                12.0, 12.0,
                12.0, 48.0,
                48.0, 12.0,
                48.0, 48.0
        );
        tileSolidAreas.get(2).getPoints().addAll(
                0.0, 12.0,
                0.0, 48.0,
                36.0, 12.0,
                36.0, 48.0
        );
        tileSolidAreas.get(3).getPoints().addAll(
                12.0, 0.0,
                12.0, 48.0,
                48.0, 0.0,
                48.0, 48.0
        );
        tileSolidAreas.get(4).getPoints().addAll(
                0.0, 0.0,
                0.0, 48.0,
                36.0, 0.0,
                36.0, 48.0
        );
        tileSolidAreas.get(5).getPoints().addAll(
                12.0, 0.0,
                12.0, 36.0,
                48.0, 0.0,
                48.0, 36.0
        );
        tileSolidAreas.get(6).getPoints().addAll(
                0.0, 0.0,
                0.0, 36.0,
                36.0, 0.0,
                36.0, 36.0
        );
        tileSolidAreas.get(7).getPoints().addAll(
                0.0, 12.0,
                0.0, 48.0,
                48.0, 12.0,
                48.0, 48.0
        );
        tileSolidAreas.get(8).getPoints().addAll(
                0.0, 0.0,
                0.0, 36.0,
                48.0, 0.0,
                48.0, 36.0
        );
        tileSolidAreas.get(9).getPoints().addAll(
                9.0, 12.0,
                9.0, 48.0,
                21.0, 36.0,
                21.0, 48.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(10).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                27.0, 36.0,
                27.0, 48.0,
                39.0, 12.0,
                39.0, 48.0
        );
        tileSolidAreas.get(11).getPoints().addAll(
                9.0, 0.0,
                9.0, 48.0,
                21.0, 0.0,
                21.0, 48.0
        );
        tileSolidAreas.get(12).getPoints().addAll(
                27.0, 0.0,
                27.0, 48.0,
                39.0, 0.0,
                39.0, 48.0
        );
        tileSolidAreas.get(13).getPoints().addAll(
                9.0, 0.0,
                9.0, 36.0,
                21.0, 0.0,
                21.0, 12.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(14).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                27.0, 0.0,
                27.0, 12.0,
                39.0, 0.0,
                39.0, 36.0
        );
        tileSolidAreas.get(15).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(16).getPoints().addAll(
                27.0, 12.0,
                27.0, 48.0,
                39.0, 36.0,
                39.0, 48.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(17).getPoints().addAll(
                0.0, 12.0,
                0.0, 48.0,
                9.0, 36.0,
                9.0, 48.0,
                21.0, 12.0,
                21.0, 48.0

        );
        tileSolidAreas.get(18).getPoints().addAll(
                27.0, 0.0,
                27.0, 36.0,
                39.0, 0.0,
                39.0, 12.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(19).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                9.0, 12.0,
                9.0, 36.0,
                21.0, 0.0,
                21.0, 36.0
        );
        tileSolidAreas.get(20).getPoints().addAll(
                30.0, 12.0,
                30.0, 36.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(21).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                12.0, 0.0,
                12.0, 12.0,
                48.0, 0.0,
                48.0, 26.0
        );
        tileSolidAreas.get(22).getPoints().addAll(
                0.0, 0.0,
                0.0, 36.0,
                36.0, 0.0,
                36.0, 12.0,
                48.0, 12.0,
                48.0, 36.0
        );
        tileSolidAreas.get(23).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                15.0, 12.0,
                15.0, 36.0
        );
        tileSolidAreas.get(24).getPoints().addAll(
                0.0, 12.0,
                0.0, 36.0,
                12.0, 36.0,
                12.0, 48.0,
                48.0, 12.0,
                48.0, 48.0
        );
        tileSolidAreas.get(25).getPoints().addAll(
                0.0, 12.0,
                0.0, 48.0,
                36.0, 36.0,
                36.0, 48.0,
                48.0, 12.0,
                48.0, 36.0
        );
    }

    // Loads Tile Images
    public void getTileImage() {
        try {
            tileNameCollision = Files.readAllLines(Paths.get("Slime_Time/res/maps/tileNameCollision.txt"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tileNameCollision.size(); i += 2) {
            fileNames.add(tileNameCollision.get(i));
            tileCollisions.add(tileNameCollision.get(i + 1));
        }

        for (int i = 0; i < fileNames.size(); ++i) {
            setup(i, fileNames.get(i), tileCollisions.get(i));
        }

        /*
        setup(0, "grass_tile_1", false);
        setup(1, "grass_tile_2", false);
        */
    }

    // Utility Method to Load Tile Images
    public void setup(int index, String imageName, String collisionType) {
        try {
            tile[index] = new Tile();
            tile[index].setImage(new Image(new FileInputStream("Slime_Time/res/tiles/" + imageName + ".png"), ga.TILE_SIZE, ga.TILE_SIZE, false, false));
            if (collisionType.compareTo("n") != 0) {
                tile[index].collision = true;
                tile[index].solidArea = tileSolidAreas.get(Integer.parseInt(collisionType));
            }
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
                gc.drawImage(tile[tileNum].getImage(), screenX, screenY);
            }
            ++worldCol;
            if (worldCol == ga.MAX_WORLD_COL) {
                worldCol = 0;
                ++worldRow;
            }
        }

        if(drawPath) {
            Color aliceBlue = Color.ALICEBLUE;
            gc.setFill(aliceBlue.deriveColor(1.0, 1.0, 1.0, 0.5));

            for(int i = 0; i <ga.pFinder.pathList.size(); i++) {

                int worldX = ga.pFinder.pathList.get(i).col * ga.TILE_SIZE;
                int worldY = ga.pFinder.pathList.get(i).row * ga.TILE_SIZE;
                int screenX = worldX - ga.player.worldX + ga.player.screenX;
                int screenY = worldY - ga.player.worldY + ga.player.screenY;

                gc.fillRect(screenX,screenY, ga.TILE_SIZE, ga.TILE_SIZE);

            }

        }
    }

}
