// A PathNode will be used in the open list and closed list to store all the relevant info for each state or square of the grid

public class PathNode {

    int x;

    int y;
    int id;     // Calculated from its coordinates

    PathNode parent; // id of the parent (previous) square, calculated from its coordinates

    int g;      // Path length from start to this node

    int h;      // Heuristic  - estimated path length from this node to the goal
    int f;      // f = g + h   - the estimated length of an optimal path going through this node

    public static int coordsToId(int x, int y) {    // Translates x,y coords into a unique id (this allows us to refer to any square on the grid by a single int, allowing us to use the id as the key of a hashmap
        return (101 * y) + x;
    }

    public static int idToX(int id) {               // Returns the x coordinate given an id

        return id % 101;
    }
    public static int idToY(int id) {               // Returns the y coordinate given an id
        return id / 101;
    }

    public PathNode(PathNode parent, int x, int y) {
        if (parent == null) {
            g = 0;
        } else {
            g = parent.g + 1;
        }
        h = Math.abs(x - 101) + Math.abs(y - 101);
        this.f = h + g;
        this.parent = parent;
        this.x = x;
        this.y = y;
        id = (101 * y) + x;
    }

}
