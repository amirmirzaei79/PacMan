package ir.ac.kntu.Ghosts;

public class HorizontalRandomGhost extends Ghost {
    public HorizontalRandomGhost(char[][] Map) {
        super(Map);
        while (Map[x + 1][y] == '#' && Map[x - 1][y] == '#') {
            x = (int) (Math.random() * 15) + 1;
            y = (int) (Math.random() * 10) + 1;
        }

        imagePath = "file:src/main/java/ir/ac/kntu/Images/Blue_Ghost.png";
    }

    public void move(char[][] Map, Ghost[] ghosts) {
        Map = ghostToBlock(Map, ghosts);

        double RV = Math.random();
        int direction;
        if (RV < 0.5)
            direction = -1;
        else
            direction = 1;

        if (Map[x + direction][y] == '#')
            direction *= -1;

        if (isActive && Map[x + direction][y] != '#')
            x += direction;
    }
}
