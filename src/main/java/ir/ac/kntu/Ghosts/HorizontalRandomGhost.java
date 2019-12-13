package ir.ac.kntu.Ghosts;

import java.util.Map;

public class HorizontalRandomGhost extends Ghost {
    public HorizontalRandomGhost(char[][] Map) {
        super(Map);
        while (Map[x + 1][y] == '#' && Map[x - 1][y] == '#') {
            x = (int) (Math.random() * 15) + 1;
            y = (int) (Math.random() * 10) + 1;
        }
    }

    public void move(char[][] Map)
    {
        double RV = Math.random();
        int direction;
        if(RV < 0.5)
            direction = -1;
        else
            direction = 1;

        if(isActive && Map[x + direction][y] != '#')
            x += direction;
    }
}
