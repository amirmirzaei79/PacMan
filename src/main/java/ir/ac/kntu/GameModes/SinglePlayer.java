package ir.ac.kntu.GameModes;

import ir.ac.kntu.Ghosts.*;
import ir.ac.kntu.PacMans.PacMan;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;

public class SinglePlayer extends GameMode {
    private Ghost[] ghosts;
    private PacMan pacman;
    private static int deadCycle, freezeCycle;

    //Init Block
    @Override
    public void init() throws FileNotFoundException {
        initMap();
        initGhosts();
        initPacMan();
        setDotCount();
        freezeCycle = 0;
        deadCycle = 0;
    }

    private void initGhosts() {
        {
            ghosts = new Ghost[5];
            ghosts[0] = new HorizontalRandomGhost(Map);
            ghosts[1] = new VerticalRandomGhost(Map);
            ghosts[2] = new RandomGhost(Map);
            ghosts[3] = new GreedyGhost(Map);
            ghosts[4] = new SmartGhost(Map);
        }
    }

    private void initPacMan() {
        pacman = new PacMan();
        pacman.init(Map, Map.length / 2, Map[0].length / 2, 'N');
    }
    //End of Init Block

    //Game Block
    @Override
    public void runOneCycle() {
        if (deadCycle == 0) {
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

            for (Ghost ghost : ghosts) {
                if (ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()) {
                    pacman.die(Map);
                    deadCycle = 5;
                }

                if (ghost.isGhostActive())
                    ghost.move(Map, ghosts);

                if (ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()) {
                    pacman.die(Map);
                    deadCycle = 5;
                }
            }

            if (freezeCycle > 0) {
                --freezeCycle;
                if (freezeCycle == 0) {
                    for (Ghost ghost : ghosts) ghost.setActive(true);
                }
            }
        } else {
            for (Ghost ghost : ghosts) ghost.move(Map, ghosts);
            --deadCycle;

            if (deadCycle == 0)
                pacman.init(Map, Map.length / 2, Map[0].length / 2, 'N');
        }

        if (!pacman.isAlive())
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
        if (deadCycle == 0) {
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

        for (int i = 0; i < pacman.getLives(); ++i) {
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
