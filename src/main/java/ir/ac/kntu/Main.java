package ir.ac.kntu;

import ir.ac.kntu.GameModes.GameMode;
import ir.ac.kntu.GameModes.SinglePlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Main extends Application {
    private static GameMode GM;

    public static void main(String[] args) throws FileNotFoundException {
        GM = new SinglePlayer();
        GM.init();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            GM.keyEventHandler(event);
        });

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(259), e -> run(stage)));
        tl.setCycleCount(Timeline.INDEFINITE);
        GM.show(stage);
        tl.play();
    }

    private void run(Stage stage) {
        GM.runOneCycle();
        GM.show(stage);
        if (GM.isDone())
            stage.close();
    }
}
