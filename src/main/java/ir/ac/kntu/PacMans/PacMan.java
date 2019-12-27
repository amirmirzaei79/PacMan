package ir.ac.kntu.PacMans;

import java.io.Serializable;

public class PacMan implements Serializable {
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
            Map[x][y] = 'P';
            return true;
        }
        else
            return false;
    }

    public char move(char[][] Map) {
        int directionX = 0, directionY = 0;
        char returnValue = ' ';
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
            Map[x][y] = ' ';
            x += directionX;
            y += directionY;
            returnValue = Map[x][y];
            Map[x][y] = 'P';
        }

        return returnValue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPoints() {
        return points;
    }

    public char getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getLives() {
        return lives;
    }

    public void die(char[][] Map) {
        Map[x][y] = ' ';
        --lives;
        if (lives <= 0)
            isAlive = false;
    }

    public void eat() {
        ++points;
    }

    public void addLife() { ++lives; }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
