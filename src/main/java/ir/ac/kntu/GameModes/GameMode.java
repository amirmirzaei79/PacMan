package ir.ac.kntu.GameModes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class GameMode implements Serializable {
    protected char[][] Map;
    protected int cycle;
    protected static final int PIC_SIZE = 50;
    protected static int dotCount;
    protected boolean doneByLose, doneByWin;

    public GameMode() {
        cycle = 0;
        doneByLose = false;
        doneByWin = false;
    }

    //Init Block
    protected void initMap() throws FileNotFoundException {
        ArrayList<String> mapBuilder = new ArrayList<>();
        Scanner in = new Scanner(new File("src/main/java/ir/ac/kntu/Maps/DefaultMap.txt"));

        while (in.hasNextLine()) {
            mapBuilder.add(in.nextLine());
        }

        if (mapBuilder.size() != 0 && (mapBuilder.get(0)).length() != 0) {
            Map = new char[(mapBuilder.get(0)).length()][mapBuilder.size()];

            for (int j = 0; j < mapBuilder.size(); ++j) {
                for (int i = 0; i < (mapBuilder.get(0)).length(); ++i) {
                    Map[i][j] = (mapBuilder.get(j)).charAt(i);
                }
            }
        }
    }

    protected void initMap(int mapNumber) throws FileNotFoundException {
        ArrayList<String> mapBuilder = new ArrayList<>();
        Scanner in = new Scanner(new File("src/main/java/ir/ac/kntu/Maps/Map" + mapNumber + ".txt"));

        while (in.hasNextLine()) {
            mapBuilder.add(in.nextLine());
        }

        if (mapBuilder.size() != 0 && (mapBuilder.get(0)).length() != 0) {
            Map = new char[(mapBuilder.get(0)).length()][mapBuilder.size()];

            for (int j = 0; j < mapBuilder.size(); ++j) {
                for (int i = 0; i < (mapBuilder.get(0)).length(); ++i) {
                    Map[i][j] = (mapBuilder.get(j)).charAt(i);
                }
            }
        }
    }

    public void init() throws FileNotFoundException {
    }

    public void init(int difficulty, int mapNumber) throws FileNotFoundException {
    }

    protected void setDotCount() {
        dotCount = 0;
        for (char[] chars : Map)
            for (char aChar : chars)
                if (aChar == '.')
                    dotCount += 1;
    }
    //End of Init Block

    //Game Block
    public void runOneCycle() {
    }

    //End of Game Block

    //Event Block
    public void keyEventHandler(KeyEvent event) {
    }
    //End of Event Block

    //Show Block
    public void show(Stage stage) {

    }

    protected void showMap(Pane pane) {
        Image block;
        ImageView blockView;

        for (int i = 1; i < Map.length - 1; ++i)
            for (int j = 1; j < Map[i].length - 1; ++j) {
                switch (Map[i][j]) {
                    case '#':
                        block = new Image("file:src/main/java/ir/ac/kntu/Images/wall.png");
                        blockView = new ImageView(block);
                        blockView.setFitWidth(PIC_SIZE);
                        blockView.setFitHeight(PIC_SIZE);
                        blockView.setX((i - 1) * PIC_SIZE);
                        blockView.setY((j - 1) * PIC_SIZE);
                        pane.getChildren().add(blockView);
                        break;
                    case '.':
                        block = new Image("file:src/main/java/ir/ac/kntu/Images/Background.png");
                        blockView = new ImageView(block);
                        blockView.setFitWidth(PIC_SIZE);
                        blockView.setFitHeight(PIC_SIZE);
                        blockView.setX((i - 1) * PIC_SIZE);
                        blockView.setY((j - 1) * PIC_SIZE);
                        pane.getChildren().add(blockView);
                        block = new Image("file:src/main/java/ir/ac/kntu/Images/Dot.png");
                        blockView = new ImageView(block);
                        blockView.setFitWidth(PIC_SIZE);
                        blockView.setFitHeight(PIC_SIZE);
                        blockView.setX((i - 1) * PIC_SIZE);
                        blockView.setY((j - 1) * PIC_SIZE);
                        pane.getChildren().add(blockView);
                        break;
                    case 'O':
                        block = new Image("file:src/main/java/ir/ac/kntu/Images/Background.png");
                        blockView = new ImageView(block);
                        blockView.setFitWidth(PIC_SIZE);
                        blockView.setFitHeight(PIC_SIZE);
                        blockView.setX((i - 1) * PIC_SIZE);
                        blockView.setY((j - 1) * PIC_SIZE);
                        pane.getChildren().add(blockView);
                        block = new Image("file:src/main/java/ir/ac/kntu/Images/clock.png");
                        blockView = new ImageView(block);
                        blockView.setFitWidth(PIC_SIZE);
                        blockView.setFitHeight(PIC_SIZE);
                        blockView.setX((i - 1) * PIC_SIZE);
                        blockView.setY((j - 1) * PIC_SIZE);
                        pane.getChildren().add(blockView);
                        break;
                    case ' ':
                    case 'P':
                        block = new Image("file:src/main/java/ir/ac/kntu/Images/Background.png");
                        blockView = new ImageView(block);
                        blockView.setFitWidth(PIC_SIZE);
                        blockView.setFitHeight(PIC_SIZE);
                        blockView.setX((i - 1) * PIC_SIZE);
                        blockView.setY((j - 1) * PIC_SIZE);
                        pane.getChildren().add(blockView);
                        break;
                }
            }
    }

    //End of Show Block

    //Get Block
    public int getCycle() {
        return cycle;
    }

    public boolean isDone() {
        return doneByLose || doneByWin;
    }

    public boolean isDoneByLose() {
        return doneByLose;
    }

    public boolean isDoneByWin() {
        return doneByWin;
    }

    public int[] getScores() { return new int[2]; }
}
