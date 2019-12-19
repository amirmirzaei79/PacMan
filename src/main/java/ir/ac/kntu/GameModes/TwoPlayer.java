package ir.ac.kntu.GameModes;

import ir.ac.kntu.Ghosts.*;
import ir.ac.kntu.PacMans.PacMan;
import ir.ac.kntu.PacMans.PacMan_AI;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TwoPlayer extends GameMode {
    private Ghost[] ghosts;
    private PacMan pacman_1, pacman_2;
    private static int deadCycle_1, deadCycle_2, freezeCycle;
    private static int initialPacMan_1_X, initialPacMan_1_Y, initialPacMan_2_X, initialPacMan_2_Y;

    //Init Block
    @Override
    public void init() throws FileNotFoundException {
        initMap();
        initGhosts();
        initPacMan();
        setDotCount();
        freezeCycle = 0;
        deadCycle_1 = 0;
        deadCycle_2 = 0;
    }

    private void initGhosts() throws FileNotFoundException {
        {
            int x, y;
            Scanner in = new Scanner(new File("src/main/java/ir/ac/kntu/Maps/DefaultGhostPositions.txt"));

            ghosts = new Ghost[5];
            x = in.nextInt();
            y = in.nextInt();
            ghosts[0] = new HorizontalRandomGhost(Map, x, y);
            x = in.nextInt();
            y = in.nextInt();
            ghosts[1] = new VerticalRandomGhost(Map, x, y);
            x = in.nextInt();
            y = in.nextInt();
            ghosts[2] = new RandomGhost(Map, x, y);
            x = in.nextInt();
            y = in.nextInt();
            ghosts[3] = new GreedyGhost(Map, x, y);
            x = in.nextInt();
            y = in.nextInt();
            ghosts[4] = new SmartGhost(Map, x, y);
        }
    }

    private void initPacMan() throws FileNotFoundException {
        int t;
        Scanner in = new Scanner(new File("src/main/java/ir/ac/kntu/Maps/DefaultPacManPositions.txt"));
        t = in.nextInt();
        t = in.nextInt();
        initialPacMan_1_X = in.nextInt();
        initialPacMan_1_Y = in.nextInt();
        pacman_1 = new PacMan();
        pacman_1.init(Map, initialPacMan_1_X, initialPacMan_1_Y, 'N');
        initialPacMan_2_X = in.nextInt();
        initialPacMan_2_Y = in.nextInt();
        pacman_2 = new PacMan();
        pacman_2.init(Map, initialPacMan_2_X, initialPacMan_2_Y, 'N');
    }
    //End of Init Block

    //Game Block
    @Override
    public void runOneCycle() {
        if (deadCycle_1 == 0 && pacman_1.isAlive()) {
            char tile = pacman_1.move(Map);
            if (tile == '.') {
                pacman_1.eat();
                --dotCount;
                if (dotCount == 0) {
                    doneByWin = true;
                }
            } else if (tile == 'O') {
                freezeCycle = 5;
                for (Ghost ghost : ghosts) ghost.setActive(false);
            }
        } else if (pacman_1.isAlive()) {
            --deadCycle_1;
            if (deadCycle_1 == 0)
                pacman_1.init(Map, initialPacMan_1_X, initialPacMan_1_Y, 'N');
        }

        if (deadCycle_2 == 0 && pacman_2.isAlive()) {
            char tile = pacman_2.move(Map);
            if (tile == '.') {
                pacman_2.eat();
                --dotCount;
                if (dotCount == 0) {
                    doneByWin = true;
                }
            } else if (tile == 'O') {
                freezeCycle = 5;
                for (Ghost ghost : ghosts) ghost.setActive(false);
            }
        } else if (pacman_2.isAlive()) {
            --deadCycle_2;
            if (deadCycle_2 == 0)
                pacman_2.init(Map, initialPacMan_2_X, initialPacMan_2_Y, 'N');
        }

        if (freezeCycle == 0) {
            for (Ghost ghost : ghosts) {
                if (deadCycle_1 == 0 && ghost.getX() == pacman_1.getX() && ghost.getY() == pacman_1.getY()) {
                    pacman_1.die(Map);
                    deadCycle_1 = 5;
                }
                if (deadCycle_2 == 0 && ghost.getX() == pacman_2.getX() && ghost.getY() == pacman_2.getY()) {
                    pacman_2.die(Map);
                    deadCycle_2 = 5;
                }

                if (ghost.isGhostActive())
                    ghost.move(Map, ghosts);

                if (deadCycle_1 == 0 && ghost.getX() == pacman_1.getX() && ghost.getY() == pacman_1.getY()) {
                    pacman_1.die(Map);
                    deadCycle_1 = 5;
                }
                if (deadCycle_2 == 0 && ghost.getX() == pacman_2.getX() && ghost.getY() == pacman_2.getY()) {
                    pacman_2.die(Map);
                    deadCycle_2 = 5;
                }
            }
        } else {
            --freezeCycle;
            if (freezeCycle == 0) {
                for (Ghost ghost : ghosts) ghost.setActive(true);
            }

            for (Ghost ghost : ghosts) {
                if (deadCycle_1 == 0 && ghost.getX() == pacman_1.getX() && ghost.getY() == pacman_1.getY()) {
                    pacman_1.die(Map);
                    deadCycle_1 = 5;
                }
                if (deadCycle_2 == 0 && ghost.getX() == pacman_2.getX() && ghost.getY() == pacman_2.getY()) {
                    pacman_2.die(Map);
                    deadCycle_2 = 5;
                }
            }
        }

        if (!pacman_1.isAlive() && !pacman_2.isAlive())
            doneByLose = true;
    }

    //End of Game Block

    //Event Block
    @Override
    public void keyEventHandler(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                if (Map[pacman_1.getX()][pacman_1.getY() - 1] != '#')
                    pacman_1.setDirection('U');
                break;
            case DOWN:
                if (Map[pacman_1.getX()][pacman_1.getY() + 1] != '#')
                    pacman_1.setDirection('D');
                break;
            case RIGHT:
                if (Map[pacman_1.getX() + 1][pacman_1.getY()] != '#')
                    pacman_1.setDirection('R');
                break;
            case LEFT:
                if (Map[pacman_1.getX() - 1][pacman_1.getY()] != '#')
                    pacman_1.setDirection('L');
                break;
            case W:
                if (Map[pacman_2.getX()][pacman_2.getY() - 1] != '#')
                    pacman_2.setDirection('U');
                break;
            case S:
                if (Map[pacman_2.getX()][pacman_2.getY() + 1] != '#')
                    pacman_2.setDirection('D');
                break;
            case D:
                if (Map[pacman_2.getX() + 1][pacman_2.getY()] != '#')
                    pacman_2.setDirection('R');
                break;
            case A:
                if (Map[pacman_2.getX() - 1][pacman_2.getY()] != '#')
                    pacman_2.setDirection('L');
                break;
        }
    }
    //End of Event Block

    //Show Block
    @Override
    public void show(Stage stage) {
        stage.setResizable(false);
        Pane pane = new Pane();
        pane.setPrefHeight((Map[0].length - 2) * PIC_SIZE + PIC_SIZE);
        pane.setPrefWidth((Map.length - 2) * PIC_SIZE);

        showMap(pane);
        showPacMan(pane);
        showGhosts(pane);
        showLives(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void showGhosts(Pane pane) {
        Image ghostImage;
        ImageView ghostView;
        for (Ghost ghost : ghosts) {
            ghostImage = new Image(ghost.getImagePath());
            ghostView = new ImageView(ghostImage);
            ghostView.setFitWidth(PIC_SIZE);
            ghostView.setFitHeight(PIC_SIZE);
            ghostView.setX((ghost.getX() - 1) * PIC_SIZE);
            ghostView.setY((ghost.getY() - 1) * PIC_SIZE);
            pane.getChildren().add(ghostView);
        }
    }

    private void showPacMan(Pane pane) {
        if (deadCycle_1 == 0 && pacman_1.isAlive()) {
            Image PacManImage;
            ImageView PacManView;
            PacManImage = new Image("file:src/main/java/ir/ac/kntu/Images/pacman_1.png");
            PacManView = new ImageView(PacManImage);
            PacManView.setFitWidth(PIC_SIZE);
            PacManView.setFitHeight(PIC_SIZE);
            PacManView.setX((pacman_1.getX() - 1) * PIC_SIZE);
            PacManView.setY((pacman_1.getY() - 1) * PIC_SIZE);

            switch (pacman_1.getDirection()) {
                case 'R':
                    PacManView.setRotate(0);
                    break;
                case 'D':
                    PacManView.setRotate(90);
                    break;
                case 'L':
                    PacManView.setRotate(180);
                    break;
                case 'U':
                default:
                    PacManView.setRotate(270);
            }
            pane.getChildren().add(PacManView);
        }

        if (deadCycle_2 == 0 && pacman_2.isAlive()) {
            Image PacManImage;
            ImageView PacManView;
            PacManImage = new Image("file:src/main/java/ir/ac/kntu/Images/pacman_2.png");
            PacManView = new ImageView(PacManImage);
            PacManView.setFitWidth(PIC_SIZE);
            PacManView.setFitHeight(PIC_SIZE);
            PacManView.setX((pacman_2.getX() - 1) * PIC_SIZE);
            PacManView.setY((pacman_2.getY() - 1) * PIC_SIZE);

            switch (pacman_2.getDirection()) {
                case 'R':
                    PacManView.setRotate(0);
                    break;
                case 'D':
                    PacManView.setRotate(90);
                    break;
                case 'L':
                    PacManView.setRotate(180);
                    break;
                case 'U':
                default:
                    PacManView.setRotate(270);
            }
            pane.getChildren().add(PacManView);
        }
    }

    private void showLives(Pane pane) {
        Image block;
        ImageView blockView;

        for (int i = 0; i < Map.length - 2; ++i) {
            block = new Image("file:src/main/java/ir/ac/kntu/Images/Background_Lives.png");
            blockView = new ImageView(block);
            blockView.setFitWidth(PIC_SIZE);
            blockView.setFitHeight(PIC_SIZE);
            blockView.setX((i) * PIC_SIZE);
            blockView.setY((Map[0].length - 2) * PIC_SIZE);
            pane.getChildren().add(blockView);
        }

        for (int i = 0; i < pacman_1.getLives() - 1; ++i) {
            block = new Image("file:src/main/java/ir/ac/kntu/Images/pacman_1.png");
            blockView = new ImageView(block);
            blockView.setFitWidth(PIC_SIZE);
            blockView.setFitHeight(PIC_SIZE);
            blockView.setX((i) * PIC_SIZE);
            blockView.setY((Map[0].length - 2) * PIC_SIZE);
            pane.getChildren().add(blockView);
        }

        for (int i = Map.length - 2 - pacman_2.getLives() + 1; i < Map.length - 1; ++i) {
            block = new Image("file:src/main/java/ir/ac/kntu/Images/pacman_2.png");
            blockView = new ImageView(block);
            blockView.setFitWidth(PIC_SIZE);
            blockView.setFitHeight(PIC_SIZE);
            blockView.setX((i) * PIC_SIZE);
            blockView.setY((Map[0].length - 2) * PIC_SIZE);
            pane.getChildren().add(blockView);
        }
    }
    //End of Show Block
}
