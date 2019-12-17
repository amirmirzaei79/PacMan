package ir.ac.kntu.Ghosts;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SmartGhost extends Ghost {
    public SmartGhost(char[][] Map, int x, int y) {
        super(Map, x, y);
        imagePath = "file:src/main/java/ir/ac/kntu/Images/Red_Ghost.png";
    }

    private static class State implements Comparable<State> {
        private int distance, heuristic;
        private int x, y;
        private String path;

        State(int distance, int x, int y, int px, int py, String path) {
            this.distance = distance;
            this.heuristic = Math.abs(x - px) + Math.abs(y - py);
            this.path = path;
            this.x = x;
            this.y = y;
        }

        int getDistance() {
            return distance;
        }

        int getHeuristic() {
            return heuristic;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        String getPath() {
            return path;
        }

        @Override
        public int compareTo(State S) {
            return Integer.compare(getDistance() + getHeuristic(), S.getDistance() + S.getHeuristic());
        }
    }

    private State chooseDirection(char[][] Map, int px, int py) {
        PriorityQueue<State> PQ = new PriorityQueue<>();
        State currentState = new State(0, x, y, px, py, "");
        State newState;
        int currentX, currentY;
        boolean[][] mark = new boolean[Map.length][Map[0].length];

        for (boolean[] booleans : mark) {
            Arrays.fill(booleans, true);
        }

        while (currentState != null && currentState.getHeuristic() != 0) {
            currentX = currentState.getX();
            currentY = currentState.getY();
            if (Map[currentX][currentY - 1] != '#' && mark[currentX][currentY - 1]) {
                newState = new State(currentState.getDistance() + 1, currentX, currentY - 1, px, py, currentState.getPath() + "U");
                PQ.add(newState);
                mark[currentX][currentY - 1] = false;
            }
            if (Map[currentX][currentY + 1] != '#' && mark[currentX][currentY + 1]) {
                newState = new State(currentState.getDistance() + 1, currentX, currentY + 1, px, py, currentState.getPath() + "D");
                PQ.add(newState);
                mark[currentX][currentY + 1] = false;
            }
            if (Map[currentX - 1][currentY] != '#' && mark[currentX - 1][currentY]) {
                newState = new State(currentState.getDistance() + 1, currentX - 1, currentY, px, py, currentState.getPath() + "L");
                PQ.add(newState);
                mark[currentX - 1][currentY] = false;
            }
            if (Map[currentX + 1][currentY] != '#' && mark[currentX + 1][currentY]) {
                newState = new State(currentState.getDistance() + 1, currentX + 1, currentY, px, py, currentState.getPath() + "R");
                PQ.add(newState);
                mark[currentX + 1][currentY] = false;
            }

            currentState = PQ.poll();
        }

        return currentState;
    }

    public void move(char[][] Map, Ghost[] ghosts) {
        Map = ghostToBlock(Map, ghosts);

        char direction;
        State bestPath = null, t;

        for (int i = 0; i < Map.length; ++i)
            for (int j = 0; j < Map[i].length; ++j)
                if (Map[i][j] == 'P') {
                    t = chooseDirection(Map, i, j);
                    if (t != null && (bestPath == null || bestPath.getDistance() > t.getDistance()))
                        bestPath = t;
                }

        if (bestPath != null && bestPath.getPath().length() > 0)
            direction = bestPath.getPath().charAt(0);
        else
            direction = 'N';

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

        if (isActive && Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }
}
