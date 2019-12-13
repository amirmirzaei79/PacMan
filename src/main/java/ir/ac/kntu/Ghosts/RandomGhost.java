package ir.ac.kntu.Ghosts;

public class RandomGhost extends Ghost {
    public RandomGhost(char[][] Map) {
        super(Map);
    }

    public void move(char[][] Map) {
        double RVX = Math.random(), RVY = Math.random();
        int directionX, directionY;

        if (RVX < 0.5)
            directionX = -1;
        else
            directionX = 1;

        if (RVY < 0.5)
            directionY = -1;
        else
            directionY = 1;

        if (isActive && Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }
}
