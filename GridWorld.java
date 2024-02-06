import java.io.Serializable;

public class GridWorld implements Serializable {
    Grid[][] gridWorld = new Grid[101][101];
    //maybe store target and starting location
    //maybe store heap
    public GridWorld() {
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                gridWorld[i][j] = new Grid(i,j);
            }
        }
    }
    public void addBlocks() {
        
    }
    //for now this displays the actual board and not the players view
    public void display() {
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                if (gridWorld[i][j].blocked) {
                    System.out.print("◼");
                } else {
                    System.out.print("◻");
                }
            }
            System.out.println();
        }
    }
}