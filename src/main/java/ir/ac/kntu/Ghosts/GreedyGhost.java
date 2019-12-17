package ir.ac.kntu.Ghosts;

public class GreedyGhost extends Ghost {
    public GreedyGhost(char[][] Map, int x, int y) {
        super(Map, x, y);
        imagePath = "file:src/main/java/ir/ac/kntu/Images/Orange_Ghost.png";
    }

    public void move(char[][] Map, Ghost[] ghosts) {
        Map = ghostToBlock(Map, ghosts);

        //Closest PacMan x & y
        int PX = 0, PY = 0;

        for (int i = 0; i < Map.length; ++i)
            for (int j = 0; j < Map[i].length; ++j)
                if (Map[i][j] == 'P' && ((PX == 0 && PY == 0) ||
                        (Math.abs(x - PX) + Math.abs(y - PY)) > (Math.abs(x - i) + Math.abs(y - j)))) {
                    PX = i;
                    PY = j;
                }
        if (PX == 0 && PY == 0) {
            PX = x;
            PY = y;
        }
        int directionY = 0, directionX = 0, minDist = Math.abs(PX - (x)) + Math.abs(PY - (y));
        ;
        if (Map[x + 1][y] != '#' && Math.abs(PX - (x + 1)) + Math.abs(PY - (y)) < minDist) {
            directionX = 1;
            minDist = Math.abs(PX - (x + 1)) + Math.abs(PY - (y));
        }
        if (Map[x - 1][y] != '#' && Math.abs(PX - (x - 1)) + Math.abs(PY - (y)) < minDist) {
            directionX = -1;
            minDist = Math.abs(PX - (x - 1)) + Math.abs(PY - (y));
        }
        if (Map[x][y + 1] != '#' && Math.abs(PX - (x)) + Math.abs(PY - (y + 1)) < minDist) {
            directionY = 1;
            minDist = Math.abs(PX - (x)) + Math.abs(PY - (y + 1));
        }
        if (Map[x][y - 1] != '#' && Math.abs(PX - (x)) + Math.abs(PY - (y - 1)) < minDist) {
            directionY = -1;
            minDist = Math.abs(PX - (x)) + Math.abs(PY - (y - 1));
        }

        if (isActive && Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }
}
