package ir.ac.kntu.Ghosts;

public class Ghost {
    protected int x, y;
    protected boolean isActive;

    public Ghost(char[][] Map) {
        init(Map);
    }

    public void init(char[][] Map) {
        do {
            x = (int) (Math.random() * 15) + 1;
            y = (int) (Math.random() * 10) + 1;
        } while (Map[x][y] == '#');
        isActive = true;
    }

    public void move(char[][] Map) {
    }

    public boolean isGhostActive() {
        return isActive;
    }

    public void setActive(boolean status) {
        isActive = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
