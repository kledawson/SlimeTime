package ai;

import java.util.ArrayList;

import interactive_resources.SuperResource;
import main.GameApplication;

public class PathFinder {

    private GameApplication ga;
    private Node[][] node;
    private ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder(GameApplication ga) {
        this.ga = ga;
        instantiateNodes();
    }
    private void instantiateNodes() {

        node = new Node[ga.MAX_WORLD_COL][ga.MAX_WORLD_ROW];
        int col = 0;
        int row = 0;

        while(col < ga.MAX_WORLD_COL && row < ga.MAX_WORLD_ROW) {

            node[col][row] = new Node (col,row);

            col++;
            if(col == ga.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }
    private void resetNodes() {
        int col = 0;
        int row = 0;

        while(col < ga.MAX_WORLD_COL && row < ga.MAX_WORLD_ROW) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == ga.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    private void getCost(Node node) {

        //G-COST
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //H-COST
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        //F-COST
        node.fCost = node.gCost + node.hCost;
    }
    private void openNode(Node node) {

        if(!node.open && !node.checked && !node.solid) {

            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    private void trackThePath() {

        Node current = goalNode;

        while(current != startNode) {

            pathList.add(0,current);
            current = current.parent;
        }
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < ga.MAX_WORLD_COL && row < ga.MAX_WORLD_ROW) {

            int tileNum = ga.tileM.mapTileNum[col][row];
            if(ga.tileM.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            getCost(node[col][row]);
            col++;
            if(col == ga.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

        for (SuperResource res : ga.resource) {
            if (res != null) {
                col = res.worldX / ga.TILE_SIZE;
                row = res.worldY / ga.TILE_SIZE;
                node[col][row].solid = true;
            }
        }
    }
    public boolean search() {

        while(!goalReached && step < 500) {

            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            if (row + 1 < ga.MAX_WORLD_ROW) {
                openNode(node[col][row + 1]);
            }
            if (col + 1 < ga.MAX_WORLD_COL) {
                openNode(node[col + 1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++) {
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            if(openList.isEmpty()) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

}
