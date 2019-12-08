package ir.ac.kntu;

public class GreedyGhost extends Ghost {
    public GreedyGhost(char[][] Map) {
        super(Map);
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

        int directionY, directionX;
        directionX = Integer.compare(PX, x);
        directionY = Integer.compare(PY, y);

        if (isActive && Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }
}
