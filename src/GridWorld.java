import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class GridWorld implements Serializable {
    Grid[][] gridWorld = new Grid[101][101];
    Agent agent;
    //maybe store target and starting location
    //maybe store heap
    public GridWorld() {
        agent = new Agent(gridWorld);
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                gridWorld[i][j] = new Grid(i,j);
            }
        }
        addBlocks();
        
    }
    private void addBlocks() {
        // pick a random position and mark it as visited and unblocked
        Grid current = findRandomUnvisited();
        current.blocked = false;
        current.visited = true;

        Stack<Grid> stack = new Stack<Grid>();
        // while there are still unvisited grids
        while (findRandomUnvisited() != null) {
            if (stack.isEmpty()) {
                current = findRandomUnvisited();
                current.blocked = false;
                current.visited = true;
            }
            // select random neighbor that has not been visited.
            current = findRandomUnvisitedNeighbor(current.x,current.y);
            // if there are unvisited neighbors
            if (current != null) {
                current.visited = true;
                if (Math.random() < .3) {
                    // 30% mark as blocked
                    current.blocked = true;
                } else {
                    // 70% mark as unblocked and add to stack.
                    current.blocked = false;
                    stack.push(current);
                }
            } else {
                // if there are no unvisited neighbors
                if (stack.isEmpty()) continue;
                current = stack.pop();
            }
        }
        gridWorld[0][0].blocked = false;
        gridWorld[100][100].blocked = false;
    }
    // finds a random unvisited neighboring grid given an x and y coordinate
    private Grid findRandomUnvisitedNeighbor(int x, int y) {
        ArrayList<Grid> unvisited = new ArrayList<Grid>();
        if (x + 1 <= 100 && !gridWorld[x + 1][y].visited) {
            unvisited.add(gridWorld[x + 1][y]);
        }
        if (x - 1 >= 0 && !gridWorld[x - 1][y].visited) {
            unvisited.add(gridWorld[x - 1][y]);
        }
        if (y + 1 <= 100 && !gridWorld[x][y + 1].visited) {
            unvisited.add(gridWorld[x][y + 1]);
        }
        if (y - 1 >= 0 && !gridWorld[x][y - 1].visited) {
            unvisited.add(gridWorld[x][y - 1]);
        }
        if (unvisited.isEmpty()) return null;
        return unvisited.get((int) (Math.random() * unvisited.size()));
    }
    // finds a random unvisited grid
    private Grid findRandomUnvisited() {
        ArrayList<Grid> unvisited = new ArrayList<Grid>();
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                if (gridWorld[i][j].visited == false) unvisited.add(gridWorld[i][j]);
            }
        }
        if (unvisited.isEmpty()) return null;
        return unvisited.get((int) (Math.random() * unvisited.size()));
    }

    //This prints the real maze
    public void display() {
        for (int j = 100; j >= 0; j--) {
            for (int i = 0; i < 101; i++) {                
                if (i == agent.getX() && j == agent.getY()) {
                    System.out.print("A");
                    continue;
                }
                if (i == 100 && j == 100) {
                    System.out.print("T");
                    continue;
                }
                if (gridWorld[i][j].blocked) {
                    System.out.print("X");
                } else if (!gridWorld[i][j].blocked) {
                    System.out.print("-");
                } else {
                    System.out.print("?");
                }
            }
            System.out.println();
        }
    }
}