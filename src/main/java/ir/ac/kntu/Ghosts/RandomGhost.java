package ir.ac.kntu.Ghosts;

public class RandomGhost extends Ghost {
    public RandomGhost(char[][] Map) {
        super(Map);
        imagePath = "file:src/main/java/ir/ac/kntu/Images/Pink_Ghost.png";
    }

    public void move(char[][] Map, Ghost[] ghosts) {
        Map = ghostToBlock(Map, ghosts);

        class Move {
            int x, y;
        }

        Move[] possibleMoves = new Move[4];
        int possibleMovesCount = 0;

        if (Map[x][y - 1] != '#') {
            possibleMoves[possibleMovesCount] = new Move();
            possibleMoves[possibleMovesCount].x = 0;
            possibleMoves[possibleMovesCount].y = -1;
            ++possibleMovesCount;
        }
        if (Map[x][y + 1] != '#') {
            possibleMoves[possibleMovesCount] = new Move();
            possibleMoves[possibleMovesCount].x = 0;
            possibleMoves[possibleMovesCount].y = 1;
            ++possibleMovesCount;
        }
        if (Map[x - 1][y] != '#') {
            possibleMoves[possibleMovesCount] = new Move();
            possibleMoves[possibleMovesCount].x = -1;
            possibleMoves[possibleMovesCount].y = 0;
            ++possibleMovesCount;
        }
        if (Map[x + 1][y] != '#') {
            possibleMoves[possibleMovesCount] = new Move();
            possibleMoves[possibleMovesCount].x = 1;
            possibleMoves[possibleMovesCount].y = 0;
            ++possibleMovesCount;
        }

        Move candidateMove;
        if (possibleMovesCount > 0)
            candidateMove = possibleMoves[(int) (Math.random() * possibleMovesCount)];
        else {
            candidateMove = new Move();
            candidateMove.x = 0;
            candidateMove.y = 0;
        }
        int directionX = candidateMove.x, directionY = candidateMove.y;

        if (isActive && Map[x + directionX][y + directionY] != '#') {
            x += directionX;
            y += directionY;
        }
    }
}
