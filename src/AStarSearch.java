import java.util.PriorityQueue;
import java.util.HashMap;

public class AStarSearch {
    // Open list = priority queue
    // Closed list = hashmap
    


    public static boolean repeated_forward_a_star(GridWorld gridWorld) {
        Agent agent = gridWorld.agent;
        HashMap<Integer, Integer> closedList = new HashMap<Integer, Integer>();
        // I think that this will allow the openList to work with the id system
        PriorityQueue<Integer> openList = new PriorityQueue<Integer>((Integer a, Integer b) -> 
            //compares the f-value of insertions to the openList to existing elements of the openList
            Integer.compare(
                closedList.get(Grid.coordsToId(agent.getX(), agent.getY()) + 1) + Math.abs(Grid.idToX(a) - 101) + Math.abs(Grid.idToY(a) - 101),
                closedList.get(b) + Math.abs(Grid.idToX(b) - 101) + Math.abs(Grid.idToY(b) - 101)
            )
        );
        if (agent.canMoveRight()) openList.add(Grid.coordsToId(agent.getX() + 1, agent.getY()));
        if (agent.canMoveLeft()) openList.add(Grid.coordsToId(agent.getX() - 1, agent.getY()));
        if (agent.canMoveUp()) openList.add(Grid.coordsToId(agent.getX(), agent.getY() + 1));
        if (agent.canMoveDown()) openList.add(Grid.coordsToId(agent.getX(), agent.getY() - 1));

        while (openList.size() != 0) {
            System.out.println(openList.poll());
        }
        // Look up, down, left, and right to determine which grids can be moved to and add to openList in order of lowest cost

        return false;
    }
    
}
