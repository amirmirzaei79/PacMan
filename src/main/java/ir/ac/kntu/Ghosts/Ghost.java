package ir.ac.kntu.Ghosts;

import java.io.Serializable;
import java.util.Map;

public class Ghost implements Serializable {
    protected int x, y;
    protected boolean isActive;
    protected String imagePath;

    public Ghost(char[][] Map, int x, int y) {
        init(Map, x, y);
    }

    public void init(char[][] Map, int x, int y) {
        if (x >= Map.length)
            x = Map.length - 1;
        if (x < 0)
            x = 0;
        if (y >= Map[x].length)
            y = Map[x].length - 1;
        if (y < 0)
            y = 0;
        while (Map[x][y] == '#')
        {
            x += (int)(Math.random() * 3) - 1;
            y += (int)(Math.random() * 3) - 1;

            if (x >= Map.length)
                x = Map.length - 1;
            if (x < 0)
                x = 0;
            if (y >= Map[x].length)
                y = Map[x].length - 1;
            if (y < 0)
                y = 0;
        }
        this.x = x;
        this.y = y;

        isActive = true;
    }

    protected char[][] ghostToBlock(char[][] Map, Ghost[] ghosts) {
        if (Map == null)
            return null;
        if (ghosts == null)
            return Map;

        char[][] newMap = new char[Map.length][Map[0].length];
        for(int i = 0; i < Map.length; ++i)
            System.arraycopy(Map[i], 0, newMap[i], 0, Map[i].length);

        for(Ghost ghost : ghosts) {
            newMap[ghost.getX()][ghost.getY()] = '#';
        }

        return newMap;
    }

    public void move(char[][] Map, Ghost[] ghosts) {
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

    public String getImagePath() {
        return imagePath;
    }
}
