import java.io.Serializable;

public class Agent implements Serializable {
    private int x;
    private int y;
    public Agent() {
        this.x = 0;
        this.y = 0;
    }
    public void moveLeft () {
        if (x == 0) return;
        x -= 1;
    }
    public void moveRight () {
        if (x == 100) return;
        x += 1;
    }
    public void moveDown () {
        if (y == 0) return;
        y -= 1;
    }
    public void moveUp () {
        if (y == 100) return;
        y += 1;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}