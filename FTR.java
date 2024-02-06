import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream; 
class FTR {

    public static void main(String[] args) {
        GridWorld[] gridWorlds;
        try {
            //if gridworlds exist, load them
            FileInputStream fs = new FileInputStream("gridworlds.txt");
            ObjectInputStream ois = new ObjectInputStream(fs);
            gridWorlds = (GridWorld[]) ois.readObject();
            ois.close();
            System.out.println("Successfully loaded gridworlds from a file.");
        } catch(Exception e) {
            //if gridworlds does not exist, create them
            gridWorlds = new GridWorld[50];
            for (int i = 0; i < 50; i++) {
                gridWorlds[i] = new GridWorld();
            }
            //add blocks to each gridworld
            System.out.println("Created fresh gridworlds.");
        }

        gridWorlds[3].display();

        try {
            FileOutputStream fos = new FileOutputStream("gridworlds.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gridWorlds);
            oos.close();
            System.out.println("Successfully saved gridworlds to a file.");
        } catch(Exception e) {
            System.out.println("Could not save grid worlds to a file.");
        }

    }
}