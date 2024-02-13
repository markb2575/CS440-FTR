import java.nio.file.Path;
import java.util.*;

public class AStarSearch {
    // Open list = priority queue
    // Closed list = hashmap
    


    public static boolean repeated_forward_a_star(GridWorld gridWorld) {
        Agent agent = gridWorld.agent;
//        HashMap<Integer, Grid> closedList = new HashMap<Integer, Grid>();
//        // I think that this will allow the openList to work with the id system
//        PriorityQueue<Grid> openList = new PriorityQueue<Integer>((Grid a, Grid b) ->
//            //compares the f-value of insertions to the openList to existing elements of the openList
//            Integer.compare(
//                // distance from start (of agent) + 1 (moving 1 away from agent) + distance to goal
//                closedList.get(Grid.coordsToId(agent.getX(), agent.getY())) + 1 + Math.abs(a.x - 101) + Math.abs(a.y - 101),
//                closedList.get(b) + Math.abs(b.x - 101) + Math.abs(b.y - 101)
//            )
//        );


        // Because the agent is always in the bottom left corner at the start we only need to check if the agent can move up or right
        if (!agent.canMoveUp()) {
            gridWorld.get(0,1).knownBlocked = true;
        }
        if (!agent.canMoveRight()) {
            gridWorld.get(1,0).knownBlocked = true;
        }
        gridWorld.get(0,0).visited = true;
        gridWorld.display();
        while (agent.getX() != 100 && agent.getY() != 100) {
//            System.out.println("calculating path");
            HashMap<Integer, PathNode> closedList = new HashMap<>();
            PriorityQueue<PathNode> openList = new PriorityQueue<>((a, b) -> Integer.compare(a.f, b.f));
            openList.add(new PathNode(null, agent.getX(), agent.getY()));

//            openList.forEach(element -> System.out.println("(" + element.x + "," + element.y + ") f: " + element.f));

            // while the closedList does not contain the id of the target square
            while (closedList.get(PathNode.coordsToId(100,100)) == null) {
                PathNode current = openList.poll();
                if (current == null) break;
//                System.out.println("current: " + current.x + " " + current.y);
                closedList.put(current.id, current);
                // If the current node is not at x = 0 and the left node is not known blocked and the closed list does not contain the key
                if (current.x != 0 && !gridWorld.get(current.x - 1, current.y).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x - 1, current.y))) {
//                    System.out.println("can move left");
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x - 1, current.y)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x - 1, current.y));
                    } else {
                        // If node is in openList check if we found a better path to this node
                        if (current.g + 1 < matchingNode.g) {
                            openList.remove(matchingNode);
                            matchingNode.g = current.g + 1;
                            matchingNode.f = matchingNode.g + matchingNode.h;
                            matchingNode.parent = current;
                            openList.add(matchingNode);
                        }
                    }
                }
                // If the current node is not at x = 100 and the right node is not known blocked and the closed list does not contain the key
                if (current.x != 100 && !gridWorld.get(current.x + 1, current.y).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x + 1, current.y))) {
//                    System.out.println("can move right");
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x + 1, current.y)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x + 1, current.y));
                    } else {
                        // If node is in openList check if we found a better path to this node
                        if (current.g + 1 < matchingNode.g) {
                            openList.remove(matchingNode);
                            matchingNode.g = current.g + 1;
                            matchingNode.f = matchingNode.g + matchingNode.h;
                            matchingNode.parent = current;
                            openList.add(matchingNode);
                        }
                    }
                }
                // If the current node is not at y = 0 and the lower node is not known blocked and the closed list does not contain the key
                if (current.y != 0 && !gridWorld.get(current.x, current.y - 1).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x, current.y - 1))) {
//                    System.out.println("can move down");
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x, current.y - 1)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x, current.y - 1));
                    } else {
                        // If node is in openList check if we found a better path to this node
                        if (current.g + 1 < matchingNode.g) {
                            openList.remove(matchingNode);
                            matchingNode.g = current.g + 1;
                            matchingNode.f = matchingNode.g + matchingNode.h;
                            matchingNode.parent = current;
                            openList.add(matchingNode);
                        }
                    }
                }
                // If the current node is not at y = 100 and the upper node is not known blocked and the closed list does not contain the key
                if (current.y != 100 && !gridWorld.get(current.x, current.y + 1).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x, current.y + 1))) {
//                    System.out.println("can move up");
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x, current.y + 1)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x, current.y + 1));
                    } else {
                        // If node is in openList check if we found a better path to this node
                        if (current.g + 1 < matchingNode.g) {
                            openList.remove(matchingNode);
                            matchingNode.g = current.g + 1;
                            matchingNode.f = matchingNode.g + matchingNode.h;
                            matchingNode.parent = current;
                            openList.add(matchingNode);
                        }
                    }
                }
//                System.out.println("iteration");
            }
            PathNode target = closedList.get(PathNode.coordsToId(100, 100));
            ArrayList<PathNode> path = new ArrayList<>();

            if (target == null) {
                System.out.println("Could not find target");
            } else {
                // Traverse the linked list in reverse order and add nodes to the ArrayList
                while (target != null) {
                    path.add(target);
                    target = target.parent;
                }

                // Reverse the ArrayList to get the path in forward order
                Collections.reverse(path);
                Scanner scanner = new Scanner(System.in);
                // Actions
                for (int i = 0; i < path.size() - 1; i++) {
                    PathNode current = path.get(i);
                    PathNode next = path.get(i + 1);

                    int deltaX = next.x - current.x;
                    int deltaY = next.y - current.y;



//                    System.out.println(current.x + " " + current.y);
//                    System.out.println(next.x + " " + next.y);
                    if (deltaX == 1 && deltaY == 0) {
                        // Move right
                        if (agent.canMoveRight()) {
                            agent.moveRight();
                            gridWorld.get(agent.getX(), agent.getY()).visited = true;
//                            System.out.println("move right");
                        } else {
//                            System.out.println("could not move right");
                            break;
                        }

                    } else if (deltaX == -1 && deltaY == 0) {
                        // Move left
                        if (agent.canMoveLeft()) {
                            agent.moveLeft();
                            gridWorld.get(agent.getX(), agent.getY()).visited = true;
//                            System.out.println("move left");
                        } else {
//                            System.out.println("could not move left");
                            break;
                        }

                    } else if (deltaX == 0 && deltaY == -1) {
                        // Move down
                        if (agent.canMoveDown()) {
                            agent.moveDown();
                            gridWorld.get(agent.getX(), agent.getY()).visited = true;
//                            System.out.println("move down");
                        } else {
//                            System.out.println("could not move down");
                            break;
                        }

                    } else if (deltaX == 0 && deltaY == 1) {
                        // Move up
                        if (agent.canMoveUp()) {
                            agent.moveUp();
                            gridWorld.get(agent.getX(), agent.getY()).visited = true;
//                            System.out.println("move up");
                        } else {
//                            System.out.println("could not move up");
                            break;
                        }
                    }
                    // Look around agent for each move and mark any blocked grids as known
                    if (!agent.canMoveUp() && agent.getY() != 100) {
                        gridWorld.get(agent.getX(), agent.getY() + 1).knownBlocked = true;
                    }
                    if (!agent.canMoveRight() && agent.getX() != 100) {
                        gridWorld.get(agent.getX() + 1, agent.getY()).knownBlocked = true;
                    }
                    if (!agent.canMoveDown() && agent.getY() != 0) {
                        gridWorld.get(agent.getX(), agent.getY() - 1).knownBlocked = true;
                    }
                    if (!agent.canMoveLeft() && agent.getX() != 0) {
                        gridWorld.get(agent.getX() - 1, agent.getY()).knownBlocked = true;
                    }
                    System.out.println("Enter 'n' to view next step");
                    String line = scanner.nextLine();
                    if (line.equals("n")) {
                        gridWorld.display();
                    }
                }
            }



        }

//        // while loop here
//        // if the grid has no parent set g to 0 because it is the starting grid
//        if (gridWorld.get(agent.getX(), agent.getY()).parent == -1) {
//            gridWorld.get(agent.getX(), agent.getY()).g = 0;
//        }
//        // if the agent can move in a direction add it to the openList
//        if (agent.canMoveRight()) openList.add(gridWorld.get(agent.getX() + 1, agent.getY()));
//        if (agent.canMoveLeft()) openList.add(gridWorld.get(agent.getX() - 1, agent.getY()));
//        if (agent.canMoveUp()) openList.add(gridWorld.get(agent.getX(), agent.getY() + 1));
//        if (agent.canMoveDown()) openList.add(gridWorld.get(agent.getX(), agent.getY() - 1));
//
//        while (!openList.isEmpty()) {
//            System.out.println(openList.poll());
//        }
        // Look up, down, left, and right to determine which grids can be moved to and add to openList in order of lowest cost

        return false;
    }

}
