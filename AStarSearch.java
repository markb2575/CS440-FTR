import java.util.PriorityQueue;
import java.util.HashMap;

public class AStarSearch {
    // Open list = priority queue
    // Closed list = hashmap
    


    public boolean repeated_forward_a_star(GridWorld gridWorld) {
        
        HashMap<Integer, Integer> closedList = new HashMap<Integer, Integer>();
        // I think that this will allow the openList to work with the id system
        PriorityQueue<Integer> openList = new PriorityQueue<Integer>((Integer a, Integer b) -> 
            Integer.compare(
                closedList.get(a) + Math.abs(gridWorld.agent.getX() - 101) + Math.abs(gridWorld.agent.getY() - 101),
                closedList.get(b) + Math.abs(gridWorld.agent.getX() - 101) + Math.abs(gridWorld.agent.getY() - 101)
            )
        );
        // Look up, down, left, and right to determine which grids can be moved to and add to openList in order of lowest cost

        return false;
    }
    
}
