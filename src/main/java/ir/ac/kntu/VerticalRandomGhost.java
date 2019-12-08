package ir.ac.kntu;

public class VerticalRandomGhost extends Ghost {
    public VerticalRandomGhost(char[][] Map) {
        super(Map);
        while (Map[x][y + 1] == '#' && Map[x][y - 1] == '#') {
            x = (int) (Math.random() * 15) + 1;
            y = (int) (Math.random() * 10) + 1;
        }
    }

    public void move(char[][] Map) {
        double RV = Math.random();
        int direction;
        if (RV < 0.5)
            direction = -1;
        else
            direction = 1;

        if (isActive && Map[x][y + direction] != '#')
            y += direction;
    }
}
