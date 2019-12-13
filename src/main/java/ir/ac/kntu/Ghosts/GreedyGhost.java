package ir.ac.kntu.Ghosts;

public class GreedyGhost extends Ghost {
    public GreedyGhost(char[][] Map) {
        super(Map);
    }

    public void move(char[][] Map) {
        //Closest PacMan x & y
        int PX = 0, PY = 0;

        for (int i = 0; i < Map.length; ++i)
            for (int j = 0; j < Map[i].length; ++j)
                if (Map[i][j] == 'P' && ((PX == 0 && PY == 0) ||
                        (Math.abs(x - PX) + Math.abs(y - PY)) > (Math.abs(x - i) + Math.abs(y - j)))) {
                    PX = i;
                    PY = j;
                }

        int directionY, directionX;
        directionX = Integer.compare(PX, x);
        directionY = Integer.compare(PY, y);

        if (isActive && Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }
}
