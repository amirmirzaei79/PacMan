package ir.ac.kntu;

import java.util.PriorityQueue;

public class SmartGhost extends Ghost {
    public SmartGhost(char[][] Map) {
        super(Map);
    }

    private static class State implements Comparable {
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
        public int compareTo(Object o) {
            if (o instanceof State) {
                State T = (State) o;

                return Integer.compare(getDistance() + getHeuristic(), T.getDistance() + T.getHeuristic());
            } else {
                return 0;
            }
        }
    }

    private char chooseDirection(char[][] Map, int px, int py) {
        PriorityQueue<State> PQ = new PriorityQueue<>();
        State currentState = new State(0, x, y, px, py, "");
        State newState;
        int currentX, currentY;
        PQ.add(currentState);

        while (currentState != null && currentState.getHeuristic() != 0) {
            currentX = currentState.getX();
            currentY = currentState.getY();
            if (Map[currentX][currentY - 1] != '#') {
                newState = new State(currentState.getDistance() + 1, currentX, currentY - 1, px, py, currentState.getPath() + "U");
                PQ.add(newState);
            }
            if (Map[currentX][currentY + 1] != '#') {
                newState = new State(currentState.getDistance() + 1, currentX, currentY + 1, px, py, currentState.getPath() + "D");
                PQ.add(newState);
            }
            if (Map[currentX - 1][currentY] != '#') {
                newState = new State(currentState.getDistance() + 1, currentX - 1, currentY, px, py, currentState.getPath() + "L");
                PQ.add(newState);
            }
            if (Map[currentX][currentY + 1] != '#') {
                newState = new State(currentState.getDistance() + 1, currentX + 1, currentY, px, py, currentState.getPath() + "R");
                PQ.add(newState);
            }

            currentState = PQ.poll();
        }

        if (currentState != null)
            return currentState.getPath().charAt(0);
        else
            return 'N';
    }

    public void move(char[][] Map) {
        //PacMan x & y
        int PX = 0, PY = 0;

        for (int i = 0; i < Map.length && PX == 0; ++i)
            for (int j = 0; j < Map[i].length && PX == 0; ++j)
                if (Map[i][j] == 'P') {
                    PX = i;
                    PY = j;
                }

        char direction = chooseDirection(Map, PX, PY);
        int directionX = 0, directionY = 0;
        switch (direction)
        {
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

        if(Map[x + directionX][y + directionY] != '#')
        {
            x += directionX;
            y += directionY;
        }
    }
}
