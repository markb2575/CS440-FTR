import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner; 
class FTR {

    public static int expanded = 0;
    public static int num_found = 0;

    public static void main(String[] args) {
        GridWorld[] gridWorlds;
        try {
            //if gridworlds exist, load them
            FileInputStream fs = new FileInputStream("gridworlds.txt");
            ObjectInputStream ois = new ObjectInputStream(fs);
            gridWorlds = (GridWorld[]) ois.readObject();
            ois.close();
            System.out.println("Successfully loaded gridworlds from file.");
        } catch(Exception e) {
            //if gridworlds does not exist, create them
            gridWorlds = new GridWorld[50];
            for (int i = 0; i < 50; i++) {
                System.out.print("\r");
                System.out.print("Generating Grid "+ (i+1) + "/50");
                gridWorlds[i] = new GridWorld();
            }
            //add blocks to each gridworld
            System.out.println("\nCreated fresh gridworlds.");
            try {
                FileOutputStream fos = new FileOutputStream("gridworlds.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gridWorlds);
                oos.close();
                // System.out.println("Successfully saved gridworlds to a file.");
            } catch(Exception e1) {
                System.out.println("Could not save grid worlds to a file.");
            }
        }
        int selectedGridWorld = -1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (selectedGridWorld == -1) {
                System.out.println("Select a grid-world from 1 - 50 OR Enter 'a' to test all grids OR Enter 'e' to exit:");
                String line = scanner.nextLine();
                if (line.equals("e")) {
                    break;
                }
                if (line.equals("a")) {
                    expanded = 0;
                    num_found = 0;
                    double total_time = 0;
                    for (int i = 0; i < 50; i ++) {
                        System.out.print("\r");
                        System.out.print("repeated_forward_a_star_favor_small_g: "+ (i + 1) + "/50");
                        total_time += AStarSearch.repeated_forward_a_star_favor_small_g(gridWorlds[i], false);
                    }
                    for (int i = 0; i < 50; i ++) {
                        gridWorlds[i].clearKnowledge();
                    }
                    System.out.print("\r");
                    System.out.println("Average Execution Time (in seconds) of repeated_forward_a_star_favor_small_g: " + (total_time / num_found) + " Average Expanded Cells: " + (expanded / num_found));
                    System.out.println("Found the target " + num_found + "/50 times");
                    System.out.println();

                    expanded = 0;
                    num_found = 0;
                    total_time = 0;
                    for (int i = 0; i < 50; i ++) {
                        System.out.print("\r");
                        System.out.print("repeated_forward_a_star_favor_large_g: "+ (i + 1) + "/50");
                        total_time += AStarSearch.repeated_forward_a_star_favor_large_g(gridWorlds[i], false);
                    }
                    for (int i = 0; i < 50; i ++) {
                        gridWorlds[i].clearKnowledge();
                    }
                    System.out.print("\r");
                    System.out.println("Average Execution Time (in seconds) of repeated_forward_a_star_favor_large_g: " + (total_time / num_found) + " Average Expanded Cells: " + (expanded / num_found));
                    System.out.println("Found the target " + num_found + "/50 times");
                    System.out.println();

                    expanded = 0;
                    num_found = 0;
                    total_time = 0;
                    for (int i = 0; i < 50; i ++) {
                        System.out.print("\r");
                        System.out.print("repeated_backward_a_star: "+ (i + 1) + "/50");
                        total_time += AStarSearch.repeated_backward_a_star(gridWorlds[i], false);
                    }
                    for (int i = 0; i < 50; i ++) {
                        gridWorlds[i].clearKnowledge();
                    }
                    System.out.print("\r");
                    System.out.println("Average Execution Time (in seconds) of repeated_backward_a_star: " + (total_time / num_found) + " Average Expanded Cells: " + (expanded / num_found));
                    System.out.println("Found the target " + num_found + "/50 times");
                    System.out.println();

                    expanded = 0;
                    num_found = 0;
                    total_time = 0;
                    for (int i = 0; i < 50; i ++) {
                        System.out.print("\r");
                        System.out.print("adaptive_a_star: "+ (i + 1) + "/50");
                        total_time += AStarSearch.adaptive_a_star(gridWorlds[i], false);
                    }
                    for (int i = 0; i < 50; i ++) {
                        gridWorlds[i].clearKnowledge();
                    }
                    System.out.print("\r");
                    System.out.println("Average Execution Time (in seconds) of adaptive_a_star: " + (total_time / num_found) + " Average Expanded Cells: " + (expanded / num_found));
                    System.out.println("Found the target " + num_found + "/50 times");
                    System.out.println();
                } else {
                    try {
                        if (Integer.valueOf(line) > 50 || Integer.valueOf(line) < 1) {
                            System.out.print("Invalid Input - ");
                            continue;
                        }
                        selectedGridWorld = Integer.valueOf(line) - 1;
                        continue;
                    } catch (NumberFormatException e) {
                        System.out.print("Invalid Input - ");
                    }
                }

            } else {
                gridWorlds[selectedGridWorld].clearKnowledge();
                System.out.println("- Enter 'v' to view the real maze.\n- Enter 'p' to start visualization.\n- Enter 'b' to go back to grid-world selection.");
                String line = scanner.nextLine();
                if (line.equals("v")) {
                    gridWorlds[selectedGridWorld].display();
                } else if (line.equals("p")) {
                    while (true) {
                        gridWorlds[selectedGridWorld].clearKnowledge();
                        System.out.println("- Select an algorithm:\n- '1': Repeated Forward A* (Favor small g-value).\n- '2': Repeated Forward A* (Favor large g-value).\n- '3': Repeated Backward A*.\n- '4': Adaptive A*.\n- 'b': to go back");
                        String selectedAlgorithm = scanner.nextLine();
                        if (selectedAlgorithm.equals("1")) {
                            AStarSearch.repeated_forward_a_star_favor_small_g(gridWorlds[selectedGridWorld], true);
                        } else if (selectedAlgorithm.equals("2")) {
                            AStarSearch.repeated_forward_a_star_favor_large_g(gridWorlds[selectedGridWorld], true);
                        } else if (selectedAlgorithm.equals("3")) {
                            AStarSearch.repeated_backward_a_star(gridWorlds[selectedGridWorld], true);
                        } else if (selectedAlgorithm.equals("4")) {
                            AStarSearch.adaptive_a_star(gridWorlds[selectedGridWorld], true);
                        } else if (selectedAlgorithm.equals("b")) {
                            break;
                        } else {
                            System.out.println("Invalid Input");
                        }
                    }
//                    try {
//                        FileOutputStream fos = new FileOutputStream("gridworlds.txt");
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                        oos.writeObject(gridWorlds);
//                        oos.close();
//                        // System.out.println("Successfully saved gridworlds to a file.");
//                    } catch(Exception e) {
//                        System.out.println("Could not save grid worlds to a file.");
//                    }
                } else if (line.equals("b")) {
                    selectedGridWorld = -1;
                } else {
                    System.out.println("Not a valid command");
                }
            }
        }
        scanner.close();
    }
}