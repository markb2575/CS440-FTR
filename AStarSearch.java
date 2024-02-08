import java.util.PriorityQueue;
import java.util.HashMap;

public class AStarSearch {
    // Open list = priority queue
    // Closed list = hashmap
    PriorityQueue<Integer> openList = new PriorityQueue<Integer>();
    HashMap<Integer, Integer> closedList = new HashMap<Integer, Integer>();


    public boolean repeated_forward_a_star() {
        // Look up, down, left, and right to determine which grids can be moved to and add to openList in order of lowest cost
        return false;
    }
    
}
