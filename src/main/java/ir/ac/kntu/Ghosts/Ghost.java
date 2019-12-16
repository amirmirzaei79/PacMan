package ir.ac.kntu.Ghosts;

public class Ghost {
    protected int x, y;
    protected boolean isActive;
    protected String imagePath;

    public Ghost(char[][] Map) {
        init(Map);
    }

    public void init(char[][] Map) {
        do {
            x = (int) (Math.random() * (Map.length - 2)) + 1;
            y = (int) (Math.random() * (Map[0].length - 2)) + 1;
        } while (Map[x][y] == '#');
        isActive = true;
    }

    protected char[][] ghostToBlock(char[][] Map, Ghost[] ghosts) {
        if (Map == null)
            return null;
        if (ghosts == null)
            return Map;

        char[][] newMap = new char[Map.length][Map[0].length];
        for(int i = 0; i < Map.length; ++i)
            System.arraycopy(Map[i], 0, newMap[i], 0, Map[i].length);

        for(Ghost ghost : ghosts) {
            newMap[ghost.getX()][ghost.getY()] = '#';
        }

        return newMap;
    }

    public void move(char[][] Map, Ghost[] ghosts) {
    }

    public boolean isGhostActive() {
        return isActive;
    }

    public void setActive(boolean status) {
        isActive = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImagePath() {
        return imagePath;
    }
}
