import java.io.Serializable;

public class Grid implements Serializable {
    int x;
    int y;
    boolean blocked;
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // public int getX() {
    //     return x;
    // }
    // public int getY() {
    //     return y;
    // }
    // public void setBlocked(boolean blocked) {
    //     this.blocked = blocked;
    // }
    // public boolean getBlocked() {
    //     return blocked;  
    // }
}
