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
    public void clearKnowledge() {
        agent.resetPosition();
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                gridWorld[i][j].knownBlocked = false;
                gridWorld[i][j].visited = false;
                gridWorld[i][j].step = -1;
            }
        }
    }
    public void clearVisited() {
        agent.resetPosition();
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                gridWorld[i][j].visited = false;
            }
        }
    }
    //This prints the real maze
    public void display() {
//        System.out.println(agent.getX() + " " + agent.getY());
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
                if (gridWorld[i][j].knownBlocked) {
                    System.out.print("X");
                } else if (gridWorld[i][j].blocked) {
                    System.out.print("?");
                } else if (gridWorld[i][j].step >= 0) {
                    System.out.print(gridWorld[i][j].step);
                } else if (gridWorld[i][j].visited) {
                    System.out.print("=");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println("X: Discovered Block    ?: Undiscovered Block    -: Empty    =: Visited    A: Agent    T: Target");
    }
//    public void display_small() {
////        System.out.println(agent.getX() + " " + agent.getY());
//        for (int j = agent.getY()+5; j >= agent.getY()-5; j--) {
//            for (int i = agent.getX()-10; i < agent.getX()+10; i++) {
//                if (i < 0 || j < 0 || i > 100 || j > 100) continue;
//                if (i == agent.getX() && j == agent.getY()) {
//                    System.out.print("A");
//                    continue;
//                }
//                if (i == 100 && j == 100) {
//                    System.out.print("T");
//                    continue;
//                }
//                if (gridWorld[i][j].knownBlocked) {
//                    System.out.print("X");
//                } else if (gridWorld[i][j].blocked) {
//                    System.out.print("?");
//                } else if (gridWorld[i][j].visited) {
//                    System.out.print("=");
//                } else {
//                    System.out.print("-");
//                }
//            }
//            if (j < 0 || j > 100) continue;
//            System.out.println();
//        }
//        System.out.println("X: Discovered Block    ?: Undiscovered Block    -: Empty    =: Visited    A: Agent    T: Target");
//    }
    public Grid get(int x, int y) {
        return gridWorld[x][y];
    }
}