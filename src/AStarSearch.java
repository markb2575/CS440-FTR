import java.nio.file.Path;
import java.util.*;

public class AStarSearch {
    // Open list = priority queue
    // Closed list = hashmap
    


    public static boolean repeated_forward_a_star_favor_small_g(GridWorld gridWorld) {
        long startTime = System.currentTimeMillis();
        Agent agent = gridWorld.agent;
        // Because the agent is always in the bottom left corner at the start we only need to check if the agent can move up or right
        if (!agent.canMoveUp()) {
            gridWorld.get(0,1).knownBlocked = true;
        }
        if (!agent.canMoveRight()) {
            gridWorld.get(1,0).knownBlocked = true;
        }
        gridWorld.get(0,0).visited = true;
//        gridWorld.display_small();
        boolean solvable = true;
        while ((agent.getX() != 100 || agent.getY() != 100) && solvable) {
            HashMap<Integer, PathNode> closedList = new HashMap<>();
            PriorityQueue<PathNode> openList = new PriorityQueue<>((a, b) -> { // Top priority is smallest f-value and smallest g-value
                if (a.f == b.f) {
                    return Integer.compare(a.g, b.g);
                }
                return Integer.compare(a.f, b.f);
            });

            openList.add(new PathNode(null, agent.getX(), agent.getY()));
            // while the closedList does not contain the id of the target square
            while (closedList.get(PathNode.coordsToId(100,100)) == null) {
                PathNode current = openList.poll();
                if (current == null) {
                    solvable = false;
                    break;
                }
                closedList.put(current.id, current);
                // If the current node is not at x = 0 and the left node is not known blocked and the closed list does not contain the key
                if (current.x != 0 && !gridWorld.get(current.x - 1, current.y).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x - 1, current.y))) {
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
            }
            PathNode target = closedList.get(PathNode.coordsToId(100, 100));
            ArrayList<PathNode> path = new ArrayList<>();
            // Traverse the linked list in reverse order and add nodes to the ArrayList
            while (target != null) {
                path.add(target);
                target = target.parent;
            }
            // Reverse the ArrayList to get the path in forward order
            Collections.reverse(path);
//            Scanner scanner = new Scanner(System.in);
            // Actions
            for (int i = 0; i < path.size() - 1; i++) {
                PathNode current = path.get(i);
                PathNode next = path.get(i + 1);
                int deltaX = next.x - current.x;
                int deltaY = next.y - current.y;
                if (deltaX == 1 && deltaY == 0) {
                    // Move right
                    if (agent.canMoveRight()) {
                        agent.moveRight();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == -1 && deltaY == 0) {
                    // Move left
                    if (agent.canMoveLeft()) {
                        agent.moveLeft();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == -1) {
                    // Move down
                    if (agent.canMoveDown()) {
                        agent.moveDown();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == 1) {
                    // Move up
                    if (agent.canMoveUp()) {
                        agent.moveUp();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
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
            }
            if (agent.getX() != 100 && agent.getY() != 100) {
                gridWorld.clearVisited();
            }
//            System.out.println("Enter 'n' to view next step.");
//            String line = scanner.nextLine();
//            if (line.equals("n")) {
//                gridWorld.display_small();
//            }
        }
        double executionTime = (System.currentTimeMillis() - startTime) / 1000.0;
        gridWorld.display();
        System.out.println("Execution time: " + executionTime + " seconds");
        if (agent.getX() == 100 && agent.getY() == 100) {
            System.out.println("Found target.");
            return true;
        }
        System.out.println("Could not find target.");
        return false;
    }
    public static boolean repeated_forward_a_star_favor_large_g(GridWorld gridWorld) {
        long startTime = System.currentTimeMillis();
        Agent agent = gridWorld.agent;
        // Because the agent is always in the bottom left corner at the start we only need to check if the agent can move up or right
        if (!agent.canMoveUp()) {
            gridWorld.get(0,1).knownBlocked = true;
        }
        if (!agent.canMoveRight()) {
            gridWorld.get(1,0).knownBlocked = true;
        }
        gridWorld.get(0,0).visited = true;
//        gridWorld.display_small();
        boolean solvable = true;
        while ((agent.getX() != 100 || agent.getY() != 100) && solvable) {
            HashMap<Integer, PathNode> closedList = new HashMap<>();
            PriorityQueue<PathNode> openList = new PriorityQueue<>((a, b) -> { // Top priority is smallest f-value and largest g-value (which also mean smallest h-value)
                if (a.f == b.f) {
                    return Integer.compare(b.g, a.g);
                }
                return Integer.compare(a.f, b.f);
            });

            openList.add(new PathNode(null, agent.getX(), agent.getY()));
            // while the closedList does not contain the id of the target square
            while (closedList.get(PathNode.coordsToId(100,100)) == null) {
                PathNode current = openList.poll();
                if (current == null) {
                    solvable = false;
                    break;
                }
//                System.out.println( "x:"+current.x + " y:" + current.y + " h:" + current.h + " g:" + current.g );
                closedList.put(current.id, current);
                // If the current node is not at x = 0 and the left node is not known blocked and the closed list does not contain the key
                if (current.x != 0 && !gridWorld.get(current.x - 1, current.y).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x - 1, current.y))) {
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
            }
            PathNode target = closedList.get(PathNode.coordsToId(100, 100));
            ArrayList<PathNode> path = new ArrayList<>();
            // Traverse the linked list in reverse order and add nodes to the ArrayList
            while (target != null) {
                path.add(target);
                target = target.parent;
            }
            // Reverse the ArrayList to get the path in forward order
            Collections.reverse(path);
//            Scanner scanner = new Scanner(System.in);
            // Actions
            for (int i = 0; i < path.size() - 1; i++) {
                PathNode current = path.get(i);
                PathNode next = path.get(i + 1);
                int deltaX = next.x - current.x;
                int deltaY = next.y - current.y;
                if (deltaX == 1 && deltaY == 0) {
                    // Move right
                    if (agent.canMoveRight()) {
                        agent.moveRight();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == -1 && deltaY == 0) {
                    // Move left
                    if (agent.canMoveLeft()) {
                        agent.moveLeft();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == -1) {
                    // Move down
                    if (agent.canMoveDown()) {
                        agent.moveDown();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == 1) {
                    // Move up
                    if (agent.canMoveUp()) {
                        agent.moveUp();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
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
            }
            if (agent.getX() != 100 && agent.getY() != 100) {
                gridWorld.clearVisited();
            }

//            System.out.println("Enter 'n' to view next step.");
//            String line = scanner.nextLine();
//            if (line.equals("n")) {
//                gridWorld.display_small();
//            }
        }
        double executionTime = (System.currentTimeMillis() - startTime) / 1000.0;
        gridWorld.display();
        System.out.println("Execution time: " + executionTime + " seconds");
        if (agent.getX() == 100 && agent.getY() == 100) {
            System.out.println("Found target.");
            return true;
        }
        System.out.println("Could not find target.");
        return false;
    }

    public static boolean repeated_backward_a_star(GridWorld gridWorld) {
        long startTime = System.currentTimeMillis();
        Agent agent = gridWorld.agent;
        // Because the agent is always in the bottom left corner at the start we only need to check if the agent can move up or right
        if (!agent.canMoveUp()) {
            gridWorld.get(0,1).knownBlocked = true;
        }
        if (!agent.canMoveRight()) {
            gridWorld.get(1,0).knownBlocked = true;
        }
        gridWorld.get(0,0).visited = true;
//        gridWorld.display_small();
        boolean solvable = true;
        while ((agent.getX() != 100 || agent.getY() != 100) && solvable) {
            HashMap<Integer, PathNode> closedList = new HashMap<>();
            PriorityQueue<PathNode> openList = new PriorityQueue<>((a, b) -> { // Top priority is smallest f-value and largest g-value (which also mean smallest h-value)
                if (a.f == b.f) {
                    return Integer.compare(b.g, a.g);
                }
                return Integer.compare(a.f, b.f);
            });

            openList.add(new PathNode(null, 100, 100, agent.getX(), agent.getY()));
            // while the closedList does not contain the id of the agent
            while (closedList.get(PathNode.coordsToId(agent.getX(),agent.getY())) == null) {
                PathNode current = openList.poll();
                if (current == null) {
                    solvable = false;
                    break;
                }
//                System.out.println( "x:"+current.x + " y:" + current.y + " h:" + current.h + " g:" + current.g );
                closedList.put(current.id, current);
                // If the current node is not at x = 0 and the left node is not known blocked and the closed list does not contain the key
                if (current.x != 0 && !gridWorld.get(current.x - 1, current.y).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x - 1, current.y))) {
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x - 1, current.y)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x - 1, current.y, agent.getX(), agent.getY()));
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
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x + 1, current.y)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x + 1, current.y, agent.getX(), agent.getY()));
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
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x, current.y - 1)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x, current.y - 1, agent.getX(), agent.getY()));
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
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x, current.y + 1)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        openList.add(new PathNode(current, current.x, current.y + 1, agent.getX(), agent.getY()));
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
            }
            PathNode target = closedList.get(PathNode.coordsToId(agent.getX(),agent.getY()));
            ArrayList<PathNode> path = new ArrayList<>();
            // Traverse the linked list in reverse order and add nodes to the ArrayList
            while (target != null) {
                path.add(target);
                target = target.parent;
            }
//            Scanner scanner = new Scanner(System.in);
            // Actions
            for (int i = 0; i < path.size() - 1; i++) {
                PathNode current = path.get(i);
                PathNode next = path.get(i + 1);
                int deltaX = next.x - current.x;
                int deltaY = next.y - current.y;
                if (deltaX == 1 && deltaY == 0) {
                    // Move right
                    if (agent.canMoveRight()) {
                        agent.moveRight();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == -1 && deltaY == 0) {
                    // Move left
                    if (agent.canMoveLeft()) {
                        agent.moveLeft();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == -1) {
                    // Move down
                    if (agent.canMoveDown()) {
                        agent.moveDown();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == 1) {
                    // Move up
                    if (agent.canMoveUp()) {
                        agent.moveUp();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
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
            }
            if (agent.getX() != 100 && agent.getY() != 100) {
                gridWorld.clearVisited();
            }
//            System.out.println("Enter 'n' to view next step.");
//            String line = scanner.nextLine();
//            if (line.equals("n")) {
//                gridWorld.display_small();
//            }
        }
        double executionTime = (System.currentTimeMillis() - startTime) / 1000.0;
        gridWorld.display();
        System.out.println("Execution time: " + executionTime + " seconds");
        if (agent.getX() == 100 && agent.getY() == 100) {
            System.out.println("Found target.");
            return true;
        }
        System.out.println("Could not find target.");
        return false;
    }

    public static boolean adaptive_a_star(GridWorld gridWorld) {
        long startTime = System.currentTimeMillis();
        Agent agent = gridWorld.agent;
        // Because the agent is always in the bottom left corner at the start we only need to check if the agent can move up or right
        if (!agent.canMoveUp()) {
            gridWorld.get(0,1).knownBlocked = true;
        }
        if (!agent.canMoveRight()) {
            gridWorld.get(1,0).knownBlocked = true;
        }
        gridWorld.get(0,0).visited = true;
//        gridWorld.display_small();
        boolean solvable = true;

        //Initialize a persistent hashmap to store each h value to make it easier to update
        HashMap<Integer, Integer> h_vals = new HashMap<>();

        while ((agent.getX() != 100 || agent.getY() != 100) && solvable) {
            HashMap<Integer, PathNode> closedList = new HashMap<>();
            PriorityQueue<PathNode> openList = new PriorityQueue<>((a, b) -> { // Top priority is smallest f-value and largest g-value (which also mean smallest h-value)
                if (a.f == b.f) {
                    return Integer.compare(b.g, a.g);
                }
                return Integer.compare(a.f, b.f);
            });
            if(h_vals.containsKey(PathNode.coordsToId(agent.getX(), agent.getY()))) {   // if we computed a new h value for this node, use that, else, calculate the default one for now
                openList.add(new PathNode(null, agent.getX(), agent.getY(), h_vals.get(PathNode.coordsToId(agent.getX(), agent.getY()))));
            } else {
                openList.add(new PathNode(null, agent.getX(), agent.getY()));
            }
            // while the closedList does not contain the id of the target square
            while (closedList.get(PathNode.coordsToId(100,100)) == null) {
                PathNode current = openList.poll();
                if (current == null) {
                    solvable = false;
                    break;
                }
                closedList.put(current.id, current);

                // If the current node is not at x = 0 and the left node is not known blocked and the closed list does not contain the key
                if (current.x != 0 && !gridWorld.get(current.x - 1, current.y).knownBlocked && !closedList.containsKey(PathNode.coordsToId(current.x - 1, current.y))) {
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x - 1, current.y)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        if(h_vals.containsKey(PathNode.coordsToId(current.x - 1, current.y))) { //if we already computed a new h value, use that, else use the default
                            openList.add(new PathNode(current, current.x - 1, current.y, h_vals.get(PathNode.coordsToId(current.x - 1, current.y))));
                        } else {
                            openList.add(new PathNode(current, current.x - 1, current.y));
                        }
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
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x + 1, current.y)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        if(h_vals.containsKey(PathNode.coordsToId(current.x + 1, current.y))) { //if we already computed a new h value, use that, else use the default
                            openList.add(new PathNode(current, current.x + 1, current.y, h_vals.get(PathNode.coordsToId(current.x + 1, current.y))));
                        } else {
                            openList.add(new PathNode(current, current.x + 1, current.y));
                        }
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
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x, current.y - 1)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        if(h_vals.containsKey(PathNode.coordsToId(current.x, current.y - 1))) { //if we already computed a new h value, use that, else use the default
                            openList.add(new PathNode(current, current.x, current.y - 1, h_vals.get(PathNode.coordsToId(current.x, current.y - 1))));
                        } else {
                            openList.add(new PathNode(current, current.x, current.y - 1));
                        }
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
                    PathNode matchingNode = openList.stream().filter(node -> node.id == PathNode.coordsToId(current.x, current.y + 1)).findFirst().orElse(null);
                    if (matchingNode == null) {
                        // If node is not in openList add it
                        if(h_vals.containsKey(PathNode.coordsToId(current.x, current.y + 1))) { //if we already computed a new h value, use that, else use the default
                            openList.add(new PathNode(current, current.x, current.y + 1, h_vals.get(PathNode.coordsToId(current.x, current.y + 1))));
                        } else {
                            openList.add(new PathNode(current, current.x, current.y + 1));
                        }
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
            }
            // Calculate new h values if it found the target
            if(closedList.containsKey(PathNode.coordsToId(100, 100))) {
                int g_val_goal = closedList.get(PathNode.coordsToId(100, 100)).g;   //find the g value of the goal

                // Iterate over closed list
                for(Map.Entry<Integer, PathNode> entry : closedList.entrySet()) {   // for each entry in the closed list
                    h_vals.put(entry.getKey(), (g_val_goal - entry.getValue().g));  // calculate new h value, and store for next search
                }
            }

            PathNode target = closedList.get(PathNode.coordsToId(100, 100));
            ArrayList<PathNode> path = new ArrayList<>();
            // Traverse the linked list in reverse order and add nodes to the ArrayList
            while (target != null) {
                path.add(target);
                target = target.parent;
            }
            // Reverse the ArrayList to get the path in forward order
            Collections.reverse(path);
//            Scanner scanner = new Scanner(System.in);
            // Actions
            for (int i = 0; i < path.size() - 1; i++) {
                PathNode current = path.get(i);
                PathNode next = path.get(i + 1);
                int deltaX = next.x - current.x;
                int deltaY = next.y - current.y;
                if (deltaX == 1 && deltaY == 0) {
                    // Move right
                    if (agent.canMoveRight()) {
                        agent.moveRight();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == -1 && deltaY == 0) {
                    // Move left
                    if (agent.canMoveLeft()) {
                        agent.moveLeft();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == -1) {
                    // Move down
                    if (agent.canMoveDown()) {
                        agent.moveDown();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
                        break;
                    }

                } else if (deltaX == 0 && deltaY == 1) {
                    // Move up
                    if (agent.canMoveUp()) {
                        agent.moveUp();
                        gridWorld.get(agent.getX(), agent.getY()).visited = true;
                    } else {
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
            }
            if (agent.getX() != 100 && agent.getY() != 100) {
                gridWorld.clearVisited();
            }
//            System.out.println("Enter 'n' to view next step.");
//            String line = scanner.nextLine();
//            if (line.equals("n")) {
//                gridWorld.display_small();
//            }
        }
        double executionTime = (System.currentTimeMillis() - startTime) / 1000.0;
        gridWorld.display();
        System.out.println("Execution time: " + executionTime + " seconds");
        if (agent.getX() == 100 && agent.getY() == 100) {
            System.out.println("Found target.");
            return true;
        }
        System.out.println("Could not find target.");
        return false;
    }
}
