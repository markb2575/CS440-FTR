import java.io.Serializable;

public class Grid implements Serializable {
    int id;                  // Calculated from its coordinates
    int parent;              // id of the parent (previous) square, calculated from its coordinates
    int g;                   // Path length from start to this node
    int h;                   // Heuristic  - estimated path length from this node to the goal
    int f;                   // f = g + h   - the estimated length of an optimal path going through this node
    int x;                   // x coordinate of grid in gridworld
    int y;                   // y coordinate of grid in gridworld
    boolean blocked;         // indicates whether the grid is a block or empty
    boolean visited = false; // Used to create the maze
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        id = (101 * y) + x;
    }

    public static int coordsToId(int x, int y) {    // Translates x,y coords into a unique id (this allows us to refer to any square on the grid by a single int, allowing us to use the id as the key of a hashmap
        return (101 * y) + x;
    }
    public static int idToX(int id) {               // Returns the x coordinate given an id
        return id % 101;
    }
    public static int idToY(int id) {               // Returns the y coordinate given an id
        return id / 101;
    }
}
