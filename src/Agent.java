import java.io.Serializable;

public class Agent implements Serializable {
    private int x;
    private int y;
    private Grid[][] gridWorld;
    public Agent(Grid[][] gridWorld) {
        this.gridWorld = gridWorld;
        this.x = 0;
        this.y = 0;
    }
    public void moveLeft () {
        if (canMoveLeft()) x -= 1;
//        System.out.println(x + " " + y);
    }
    public void moveRight () {
        if (canMoveRight()) x += 1;
//        System.out.println(x + " " + y);
    }
    public void moveDown () {
        if (canMoveDown()) y -= 1;
//        System.out.println(x + " " + y);
    }
    public void moveUp () {
        if (canMoveUp()) y += 1;
//        System.out.println(x + " " + y);
    }
    /* 
     * These look methods return true for unblocked and false for blocked
     */
    public boolean canMoveLeft () {
        if (x == 0) return false;
        return !gridWorld[x-1][y].blocked;
    }
    public boolean canMoveRight () {
        if (x == 100) return false;
        return !gridWorld[x+1][y].blocked;
    }
    public boolean canMoveDown () {
        if (y == 0) return false;
        return !gridWorld[x][y-1].blocked;
    }
    public boolean canMoveUp () {
        if (y == 100) return false;
        return !gridWorld[x][y+1].blocked;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}