package ir.ac.kntu.PacMans;

import ir.ac.kntu.Ghosts.Ghost;
import javafx.geometry.Pos;

import java.util.LinkedList;
import java.util.Queue;

public class PacMan_AI extends PacMan {
    public PacMan_AI() {
        super();
        lives = 1;
    }

    public void chooseDirection(char[][] Map, Ghost[] ghosts) {
        if (Map == null || Map.length == 0 || Map[0].length == 0)
            return;

        char[][] MapCopy = new char[Map.length][Map[0].length];
        int X, Y;
        for (int i = 0; i < Map.length; ++i)
            System.arraycopy(Map[i], 0, MapCopy[i], 0, Map[i].length);
        Map = MapCopy;
        for (Ghost ghost : ghosts) {
            X = ghost.getX();
            Y = ghost.getY();
            Map[X][Y] = '#';
            Map[X + 1][Y] = '#';
            Map[X - 1][Y] = '#';
            Map[X][Y + 1] = '#';
            Map[X][Y - 1] = '#';
        }

        class Position {
            private int x, y;
            private String path;
        }

        Queue<Position> BFSQ = new LinkedList<>();

        Position currentPos = new Position();
        Position childPos;
        currentPos.x = x;
        currentPos.y = y;
        currentPos.path = "";
        while (currentPos != null && Map[currentPos.x][currentPos.y] != '.') {
            X = currentPos.x;
            Y = currentPos.y;

            if (Map[X + 1][Y] != '#') {
                childPos = new Position();
                childPos.x = X + 1;
                childPos.y = Y;
                childPos.path = currentPos.path + 'L';
                BFSQ.add(childPos);
            }
            if (Map[X - 1][Y] != '#') {
                childPos = new Position();
                childPos.x = X - 1;
                childPos.y = Y;
                childPos.path = currentPos.path + 'R';
                BFSQ.add(childPos);
            }
            if (Map[X][Y + 1] != '#') {
                childPos = new Position();
                childPos.x = X;
                childPos.y = Y + 1;
                childPos.path = currentPos.path + 'D';
                BFSQ.add(childPos);
            }
            if (Map[X][Y - 1] != '#') {
                childPos = new Position();
                childPos.x = X;
                childPos.y = Y - 1;
                childPos.path = currentPos.path + 'U';
                BFSQ.add(childPos);
            }

            currentPos = BFSQ.poll();
        }

        //Change the direction if a reasonable path to a dot is available
        if (currentPos != null && currentPos.path.length() > 0)
            direction = currentPos.path.charAt(0);
    }
}
