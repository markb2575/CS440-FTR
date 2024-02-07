import java.io.Serializable;

public class Grid implements Serializable {
    int x;
    int y;
    boolean blocked;
    boolean visited = false;
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
