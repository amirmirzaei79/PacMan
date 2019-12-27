package ir.ac.kntu;

import ir.ac.kntu.GameModes.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

enum GameModeName {SinglePlayer, SinglePlayer_AI, TwoPlayer_COOP, TwoPlayer_Compete}

enum Status {PreGame, Load, LoadFailed, ModeSelect, MapSelect, DifficultSelect, InitGame, Running, Paused, GameOver}

enum Difficulty {
    EASY(0), MEDIUM(1), HARD(2);

    int value;

    Difficulty(int i) {
        value = i;
    }
}

public class Main extends Application {
    private GameModeName mode;
    private Status gameStatus;
    private Difficulty difficulty;
    private int mapSelect;
    private GameMode GM;
    private Timeline tl;
    private static final int SCREEN_HEIGHT = 400, SCREEN_WIDTH = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        preGameScreen(stage);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (gameStatus == Status.Running)
                GM.keyEventHandler(event);

            if (gameStatus == Status.Running && (event.getCode() == KeyCode.P || event.getCode() == KeyCode.SPACE)) {
                gameStatus = Status.Paused;
                tl.pause();
                pauseScreen(stage);
            } else if (gameStatus == Status.Paused && (event.getCode() == KeyCode.P || event.getCode() == KeyCode.SPACE)) {
                gameStatus = Status.Running;
                tl.play();
            }

            if (gameStatus == Status.Paused && event.getCode() == KeyCode.S) {
                saveGame();
            }
        });

        stage.show();
    }

    private void run(Stage stage) {
        GM.runOneCycle();
        GM.show(stage);

        if (GM.isDone()) {
            gameStatus = Status.GameOver;
        }
    }

    private void preGameScreen(Stage stage) {
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(70);
        newGameButton.setPrefWidth(100);
        newGameButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.ModeSelect;
            modeSelect(stage);
        });

        Button loadGameButton = new Button("Load Game");
        loadGameButton.setPrefHeight(70);
        loadGameButton.setPrefWidth(100);
        loadGameButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.Load;
            loadGame(stage);
        });

        Text text = new Text();
        if (gameStatus == Status.LoadFailed) {
            text.setText("No Save File!");
            text.setFill(Color.RED);
        } else {
            text.setText("Welcome to PacMan Game");
            text.setFill(Color.DARKCYAN);
        }
        text.setFont(Font.font("FreeSans", FontWeight.BOLD, 40));

        VBox vBox = new VBox(text, newGameButton, loadGameButton);
        vBox.setPrefHeight(SCREEN_HEIGHT);
        vBox.setPrefWidth(SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(25);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private void modeSelect(Stage stage) {
        Button singlePlayerButton = new Button("Single Player");
        singlePlayerButton.setPrefHeight(70);
        singlePlayerButton.setPrefWidth(200);
        singlePlayerButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.DifficultSelect;
            mode = GameModeName.SinglePlayer;
            difficultySelect(stage);
        });

        Button singlePlayer_AI_Button = new Button("Single Player with AI");
        singlePlayer_AI_Button.setPrefHeight(70);
        singlePlayer_AI_Button.setPrefWidth(200);
        singlePlayer_AI_Button.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.DifficultSelect;
            mode = GameModeName.SinglePlayer_AI;
            difficultySelect(stage);
        });

        HBox TopButtons = new HBox(singlePlayerButton, singlePlayer_AI_Button);
        TopButtons.setAlignment(Pos.CENTER);
        TopButtons.setPadding(new Insets(15));
        TopButtons.setSpacing(25);

        Button COOP_Button = new Button("Two Player Co-Op");
        COOP_Button.setPrefHeight(70);
        COOP_Button.setPrefWidth(200);
        COOP_Button.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.DifficultSelect;
            mode = GameModeName.TwoPlayer_COOP;
            difficultySelect(stage);
        });

        Button CompButton = new Button("Two Player Competitive");
        CompButton.setPrefHeight(70);
        CompButton.setPrefWidth(200);
        CompButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.DifficultSelect;
            mode = GameModeName.TwoPlayer_Compete;
            difficultySelect(stage);
        });

        HBox BottomButtons = new HBox(COOP_Button, CompButton);
        BottomButtons.setAlignment(Pos.CENTER);
        BottomButtons.setPadding(new Insets(15));
        BottomButtons.setSpacing(25);

        Text text = new Text();
        text.setText("Select your game mode");
        text.setFill(Color.DARKCYAN);
        text.setFont(Font.font("FreeSans", FontWeight.BOLD, 50));

        VBox vBox = new VBox(text, TopButtons, BottomButtons);
        vBox.setPrefHeight(SCREEN_HEIGHT);
        vBox.setPrefWidth(SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(25);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private void difficultySelect(Stage stage) {
        Button easyButton = new Button("EASY");
        easyButton.setPrefHeight(70);
        easyButton.setPrefWidth(100);
        easyButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.MapSelect;
            difficulty = Difficulty.EASY;
            mapSelect(stage);
        });

        Button mediumButton = new Button("MEDIUM");
        mediumButton.setPrefHeight(70);
        mediumButton.setPrefWidth(100);
        mediumButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.MapSelect;
            difficulty = Difficulty.MEDIUM;
            mapSelect(stage);
        });

        Button hardButton = new Button("HARD");
        hardButton.setPrefHeight(70);
        hardButton.setPrefWidth(100);
        hardButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.MapSelect;
            difficulty = Difficulty.HARD;
            mapSelect(stage);
        });

        Text text = new Text();
        text.setText("Select Difficulty");
        text.setFill(Color.DARKCYAN);
        text.setFont(Font.font("FreeSans", FontWeight.BOLD, 50));

        VBox vBox = new VBox(text, easyButton, mediumButton, hardButton);
        vBox.setPrefHeight(SCREEN_HEIGHT);
        vBox.setPrefWidth(SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(25);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private void mapSelect(Stage stage) {
        Button ButtonOne = new Button("Map 1");
        ButtonOne.setPrefHeight(70);
        ButtonOne.setPrefWidth(100);
        ButtonOne.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.InitGame;
            mapSelect = 1;
            newGame(stage);
        });

        Button ButtonTwo = new Button("Map 2");
        ButtonTwo.setPrefHeight(70);
        ButtonTwo.setPrefWidth(100);
        ButtonTwo.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.InitGame;
            mapSelect = 2;
            newGame(stage);
        });

        HBox TopButtons = new HBox(ButtonOne, ButtonTwo);
        TopButtons.setAlignment(Pos.CENTER);
        TopButtons.setPadding(new Insets(15));
        TopButtons.setSpacing(25);

        Button ButtonThree = new Button("Map 3");
        ButtonThree.setPrefHeight(70);
        ButtonThree.setPrefWidth(100);
        ButtonThree.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.InitGame;
            mapSelect = 3;
            newGame(stage);
        });

        Button ButtonFour = new Button("Map 4");
        ButtonFour.setPrefHeight(70);
        ButtonFour.setPrefWidth(100);
        ButtonFour.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.InitGame;
            mapSelect = 4;
            newGame(stage);
        });

        HBox BottomButtons = new HBox(ButtonThree, ButtonFour);
        BottomButtons.setAlignment(Pos.CENTER);
        BottomButtons.setPadding(new Insets(15));
        BottomButtons.setSpacing(25);

        Text text = new Text();
        text.setText("Select your map");
        text.setFill(Color.DARKCYAN);
        text.setFont(Font.font("FreeSans", FontWeight.BOLD, 50));

        VBox vBox = new VBox(text, TopButtons, BottomButtons);
        vBox.setPrefHeight(SCREEN_HEIGHT);
        vBox.setPrefWidth(SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(25);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private void pauseScreen(Stage stage) {
        Text text1 = new Text("Game Paused");
        Text text2 = new Text("Press space or P to continue");
        Text text3 = new Text("Press S to save game");

        text1.setFont(Font.font("FreeSans", FontWeight.BOLD, 75));
        text1.setFill(Color.DARKCYAN);
        text2.setFont(Font.font("FreeSans", 50));
        text2.setFill(Color.DARKCYAN);
        text3.setFont(Font.font("FreeSans", 50));
        text3.setFill(Color.DARKCYAN);

        VBox vBox = new VBox(text1, text2, text3);
        vBox.setPrefHeight(SCREEN_HEIGHT);
        vBox.setPrefWidth(SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(25);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private void gameOverScreen(Stage stage) {
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(70);
        newGameButton.setPrefWidth(100);
        newGameButton.setOnMouseClicked(mouseEvent -> {
            gameStatus = Status.PreGame;
            preGameScreen(stage);
        });

        Button exitButton = new Button("Exit");
        exitButton.setPrefHeight(70);
        exitButton.setPrefWidth(100);
        exitButton.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        Text text = new Text();
        text.setText("Game Over");
        text.setFill(Color.RED);
        text.setFont(Font.font("FreeSans", FontWeight.BOLD, 50));

        int[] scores = GM.getScores();

        Text WinLose = new Text();
        Text Scores = new Text();
        if (mode != GameModeName.TwoPlayer_Compete) {
            if (GM.isDoneByWin())
                WinLose.setText("You Win");
            else
                WinLose.setText("You Lose");
        } else {
            if (scores[1] > scores[0])
                WinLose.setText("Left Player Wins");
            else if (scores[1] < scores[0])
                WinLose.setText("Right Player Wins");
            else
                WinLose.setText("Tie");
        }
        WinLose.setFill(Color.BLACK);
        WinLose.setFont(Font.font("FreeSans", FontWeight.BOLD, 40));

        switch (mode) {
            case SinglePlayer:
                Scores.setText("Score: " + scores[0]);
                break;
            case SinglePlayer_AI:
                Scores.setText("Player Score: " + scores[0] + " --- AI Score: " + scores[1]);
                break;
            case TwoPlayer_COOP:
            case TwoPlayer_Compete:
                Scores.setText("Right Player Score: " + scores[0] + " --- Left Player Score: " + scores[1]);
        }
        Scores.setFill(Color.BLACK);
        Scores.setFont(Font.font("FreeSans", FontWeight.BOLD, 40));

        VBox vBox = new VBox(text, WinLose, Scores, newGameButton, exitButton);
        vBox.setPrefHeight(SCREEN_HEIGHT);
        vBox.setPrefWidth(SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(25);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    private void newGame(Stage stage) {
        try {
            switch (mode) {
                case SinglePlayer:
                    GM = new SinglePlayer();
                    break;
                case SinglePlayer_AI:
                    GM = new SinglePlayer_AI();
                    break;
                case TwoPlayer_COOP:
                    GM = new TwoPlayer();
                    break;
                case TwoPlayer_Compete:
                    GM = new TwoPlayer_comp();
            }

            GM.init(difficulty.value, mapSelect);
            gameStatus = Status.Running;

            tl = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                if (gameStatus == Status.Running)
                    run(stage);

                if (gameStatus == Status.GameOver) {
                    tl.stop();
                    gameOverScreen(stage);
                }
            }));
            tl.setCycleCount(Timeline.INDEFINITE);
            GM.show(stage);
            tl.play();
            stage.show();
        } catch (FileNotFoundException exp) {
            System.out.println("Necessary file missing!");
            System.out.println(exp.getMessage());
            stage.close();
        }
    }

    private void saveGame() {
        try {
            FileOutputStream outFile = new FileOutputStream("./SaveFile.ser");
            ObjectOutputStream fileOutStream = new ObjectOutputStream(outFile);

            fileOutStream.writeObject(GM);

            fileOutStream.close();
            outFile.close();
        } catch (IOException exp) {
            System.out.println(exp.getMessage());
        }
    }

    private void loadGame(Stage stage) {
        try {
            File file = new File("./SaveFile.ser");
            if (!file.exists()) {
                gameStatus = Status.LoadFailed;
                System.out.println("Save file does not exist");
                preGameScreen(stage);
                return;
            }

            FileInputStream inFile = new FileInputStream("./SaveFile.ser");
            ObjectInputStream fileInStream = new ObjectInputStream(inFile);

            GM = (GameMode) fileInStream.readObject();

            fileInStream.close();
            inFile.close();

            gameStatus = Status.Running;
            tl = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                if (gameStatus == Status.Running)
                    run(stage);

                if (gameStatus == Status.GameOver) {
                    tl.stop();
                    gameOverScreen(stage);
                }
            }));
            tl.setCycleCount(Timeline.INDEFINITE);
            GM.show(stage);
            tl.play();
            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            gameStatus = Status.LoadFailed;
        }
    }
}
