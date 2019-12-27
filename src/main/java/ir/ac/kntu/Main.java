package ir.ac.kntu;

import ir.ac.kntu.GameModes.GameMode;
import ir.ac.kntu.GameModes.SinglePlayer;
import ir.ac.kntu.GameModes.SinglePlayer_AI;
import ir.ac.kntu.GameModes.TwoPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

enum GameModeName {SinglePlayer, SinglePlayer_AI, TwoPlayer, TwoPlayer_Compete}

public class Main extends Application {
    private static GameMode GM;
    private Timeline tl;

    public static void main(String[] args) throws FileNotFoundException {
        GM = new TwoPlayer();
        GM.init();
        launch(args);
    }

    private void newGame() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (tl.getStatus() == Animation.Status.RUNNING)
                GM.keyEventHandler(event);
        });

        tl = new Timeline(new KeyFrame(Duration.millis(300), e -> run(stage)));
        tl.setCycleCount(Timeline.INDEFINITE);
        GM.show(stage);
        tl.play();
    }

    private void run(Stage stage) {
        GM.runOneCycle();
        GM.show(stage);
        if (GM.isDone())
//            stage.close();
            tl.stop();
    }
}
