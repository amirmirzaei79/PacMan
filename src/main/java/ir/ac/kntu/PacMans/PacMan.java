package ir.ac.kntu.PacMans;

public class PacMan {
    protected int x, y;
    protected char direction;
    protected boolean isAlive;
    protected int lives;
    protected int points;

    public PacMan() {
        isAlive = true;
        points = 0;
        lives = 3;
    }

    public boolean init(char[][] Map, int x, int y, char direction) {
        if (isAlive() && Map[x][y] != '#') {
            this.x = x;
            this.y = y;
            this.direction = direction;
            return true;
        }
        else
            return false;
    }

    public void move(char[][] Map) {
        int directionX = 0, directionY = 0;
        switch (direction) {
            case 'U':
                directionX = 0;
                directionY = -1;
                break;
            case 'D':
                directionX = 0;
                directionY = 1;
                break;
            case 'L':
                directionX = -1;
                directionY = 0;
                break;
            case 'R':
                directionX = 1;
                directionY = 0;
                break;
        }

        if (Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPoint() {
        return points;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die(char[][] Map) {
        --lives;
        if (lives <= 0)
            isAlive = false;
    }

    public void eat() {
        ++points;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
