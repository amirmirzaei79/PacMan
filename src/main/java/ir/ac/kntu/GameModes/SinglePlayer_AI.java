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

public class SinglePlayer_AI extends GameMode {
    private Ghost[] ghosts;
    private PacMan pacman;
    private PacMan_AI pacman_AI;
    private static int deadCycle, deadCycleAI, freezeCycle;
    private static int initialPacManX, initialPacManY, initialAIX, initialAIY;

    //Init Block
    @Override
    public void init() throws FileNotFoundException {
        initMap();
        initGhosts();
        initPacMan();
        setDotCount();
        freezeCycle = 0;
        deadCycle = 0;
        deadCycleAI = 0;
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
        initialPacManX = in.nextInt();
        initialPacManY = in.nextInt();
        pacman = new PacMan();
        pacman.init(Map, initialPacManX, initialPacManY, 'N');
        initialAIX = in.nextInt();
        initialAIY = in.nextInt();
        pacman_AI = new PacMan_AI();
        pacman_AI.init(Map, initialAIX, initialAIY, 'N');
    }
    //End of Init Block

    //Game Block
    @Override
    public void runOneCycle() {
        if (deadCycle == 0 && pacman.isAlive()) {
            char tile = pacman.move(Map);
            if (tile == '.') {
                pacman.eat();
                --dotCount;
                if (dotCount == 0) {
                    doneByWin = true;
                }
            } else if (tile == 'O') {
                freezeCycle = 5;
                for (Ghost ghost : ghosts) ghost.setActive(false);
            }
        } else if (pacman.isAlive()) {
            --deadCycle;
            if (deadCycle == 0)
                pacman.init(Map, initialPacManX, initialPacManY, 'N');
        }

        if (deadCycleAI == 0 && pacman_AI.isAlive()) {
            pacman_AI.chooseDirection(Map, ghosts);
            char tile = pacman_AI.move(Map);
            if (tile == '.') {
                pacman_AI.eat();
                --dotCount;
                if (dotCount == 0) {
                    doneByWin = true;
                }
            } else if (tile == 'O') {
                freezeCycle = 5;
                for (Ghost ghost : ghosts) ghost.setActive(false);
            }
        } else if (pacman_AI.isAlive()) {
            --deadCycleAI;
            if (deadCycleAI == 0)
                pacman_AI.init(Map, initialAIX, initialAIY, 'N');
        }

        if (freezeCycle == 0) {
            for (Ghost ghost : ghosts) {
                if (deadCycle == 0 && ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()) {
                    pacman.die(Map);
                    deadCycle = 5;
                }
                if (deadCycleAI == 0 && ghost.getX() == pacman_AI.getX() && ghost.getY() == pacman_AI.getY()) {
                    pacman_AI.die(Map);
                    deadCycleAI = 5;
                }

                if (ghost.isGhostActive())
                    ghost.move(Map, ghosts);

                if (deadCycle == 0 && ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()) {
                    pacman.die(Map);
                    deadCycle = 5;
                }
                if (deadCycleAI == 0 && ghost.getX() == pacman_AI.getX() && ghost.getY() == pacman_AI.getY()) {
                    pacman_AI.die(Map);
                    deadCycleAI = 5;
                }
            }
        } else {
            --freezeCycle;
            if (freezeCycle == 0) {
                for (Ghost ghost : ghosts) ghost.setActive(true);
            }

            for (Ghost ghost : ghosts) {
                if (deadCycle == 0 && ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()) {
                    pacman.die(Map);
                    deadCycle = 5;
                }
                if (deadCycleAI == 0 && ghost.getX() == pacman_AI.getX() && ghost.getY() == pacman_AI.getY()) {
                    pacman_AI.die(Map);
                    deadCycleAI = 5;
                }
            }
        }

        if (!pacman.isAlive() && !pacman_AI.isAlive())
            doneByLose = true;
    }

    //End of Game Block

    //Event Block
    @Override
    public void keyEventHandler(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                if (Map[pacman.getX()][pacman.getY() - 1] != '#')
                    pacman.setDirection('U');
                break;
            case DOWN:
                if (Map[pacman.getX()][pacman.getY() + 1] != '#')
                    pacman.setDirection('D');
                break;
            case RIGHT:
                if (Map[pacman.getX() + 1][pacman.getY()] != '#')
                    pacman.setDirection('R');
                break;
            case LEFT:
                if (Map[pacman.getX() - 1][pacman.getY()] != '#')
                    pacman.setDirection('L');
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
        if (deadCycle == 0 && pacman.isAlive()) {
            Image PacManImage;
            ImageView PacManView;
            PacManImage = new Image("file:src/main/java/ir/ac/kntu/Images/pacman_2.png");
            PacManView = new ImageView(PacManImage);
            PacManView.setFitWidth(PIC_SIZE);
            PacManView.setFitHeight(PIC_SIZE);
            PacManView.setX((pacman.getX() - 1) * PIC_SIZE);
            PacManView.setY((pacman.getY() - 1) * PIC_SIZE);

            switch (pacman.getDirection()) {
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

        if (deadCycleAI == 0 && pacman_AI.isAlive()) {
            Image PacManImage;
            ImageView PacManView;
            PacManImage = new Image("file:src/main/java/ir/ac/kntu/Images/pacman.png");
            PacManView = new ImageView(PacManImage);
            PacManView.setFitWidth(PIC_SIZE);
            PacManView.setFitHeight(PIC_SIZE);
            PacManView.setX((pacman_AI.getX() - 1) * PIC_SIZE);
            PacManView.setY((pacman_AI.getY() - 1) * PIC_SIZE);

            switch (pacman_AI.getDirection()) {
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

        for (int i = 0; i < pacman.getLives() - 1; ++i) {
            block = new Image("file:src/main/java/ir/ac/kntu/Images/pacman_2.png");
            blockView = new ImageView(block);
            blockView.setFitWidth(PIC_SIZE);
            blockView.setFitHeight(PIC_SIZE);
            blockView.setX((i) * PIC_SIZE);
            blockView.setY((Map[0].length - 2) * PIC_SIZE);
            pane.getChildren().add(blockView);
        }

        for (int i = Map.length - 2 - pacman_AI.getLives() + 1; i < Map.length - 1; ++i) {
            block = new Image("file:src/main/java/ir/ac/kntu/Images/pacman.png");
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
