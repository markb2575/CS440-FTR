import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner; 
class FTR {

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
                System.out.println("Select a grid-world from 1 - 50 OR Enter 'e' to exit:");
                String line = scanner.nextLine();
                if (line.equals("e")) {
                    break;
                }
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
            } else {
                gridWorlds[selectedGridWorld].clearKnowledge();
                System.out.println("- Enter 'v' to view the real maze.\n- Enter 'p' to start visualization.\n- Enter 'b' to go back to grid-world selection.");
                String line = scanner.nextLine();
                if (line.equals("v")) {
                    gridWorlds[selectedGridWorld].display();
                } else if (line.equals("p")) {
                    while (true) {
                        gridWorlds[selectedGridWorld].clearKnowledge();
                        System.out.println("- Select an algorithm:\n- '1': Repeated Forward A* (Favor small g-value).\n- '2': Repeated Forward A* (Favor large g-value).\n- '3': Repeated Backward A*.\n- 'b': to go back");
                        String selectedAlgorithm = scanner.nextLine();
                        if (selectedAlgorithm.equals("1")) {
                            AStarSearch.repeated_forward_a_star_favor_small_g(gridWorlds[selectedGridWorld]);
                        } else if (selectedAlgorithm.equals("2")) {
                            AStarSearch.repeated_forward_a_star_favor_large_g(gridWorlds[selectedGridWorld]);
                        } else if (selectedAlgorithm.equals("3")) {
                            AStarSearch.repeated_backward_a_star(gridWorlds[selectedGridWorld]);
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