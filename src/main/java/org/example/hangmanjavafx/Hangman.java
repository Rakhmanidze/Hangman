package org.example.hangmanjavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.hangmanjavafx.inputs.MyMouseListener;
import org.example.hangmanjavafx.main.GameStates;
import org.example.hangmanjavafx.main.GameWindow;
import org.example.hangmanjavafx.main.Render;
import org.example.hangmanjavafx.scenes.GameOver;
import org.example.hangmanjavafx.scenes.GameWon;
import org.example.hangmanjavafx.scenes.Menu;
import org.example.hangmanjavafx.scenes.Playing;
import java.util.logging.Logger;

/**
 * The main class responsible for managing the game window, rendering, updating, and input handling.
 */
public class Hangman extends Application {
    GameWindow gameWindow = new GameWindow();
    private long lastSecondTime;
    private int frames = 0;
    private long lastFrame;
    private long lastUpdate;
    private double timePerFrame;
    private double timePerUpdate;
    private Render render;
    Menu menu = new Menu();
    Playing playing = new Playing(this);
    GameWon gameWon  = new GameWon(this);
    GameOver gameOver = new GameOver(this);
    private static final Logger LOGGER = Logger.getLogger(Hangman.class.getName());

    /**
     * Initializes the game and starts the game loop.
     */
    @Override
    public void start(Stage stage) {
        render = new Render(this);
        Canvas canvas = new Canvas(gameWindow.getWindowWidth(), gameWindow.getWindowHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, gameWindow.getWindowWidth(), gameWindow.getWindowHeight());

        initInputListeners(scene);

        stage.setScene(scene);
        stage.setTitle("Hangman");
        stage.setResizable(false);
        stage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                long now = System.nanoTime();

                // Render
                if (now - lastFrame >= timePerFrame) {
                    render.render(gc);
                    updateGame();
                    lastFrame = now;
                    frames++;
                }

                // Update
                if (now - lastUpdate >= timePerUpdate) {
                    gameWindow.update();
                    lastUpdate = now;
                }

                if (now - lastSecondTime >= 1_000_000_000) {
                    LOGGER.info("FPS: " + frames + " | UPS: " + gameWindow.getUpdates());
                    frames = 0;
                    gameWindow.resetUpdates();
                    lastSecondTime = now;
                }
            }
        };
        animationTimer.start();
    }

    /**
     * Updates the game state based on the current game state.
     * If the game state is PLAYING, updates the playing scene.
     */
    private void updateGame() {
        if (GameStates.gameState == GameStates.PLAYING)
            playing.update();
    }

    /**
     * Initializes input listeners for keyboard and mouse events.
     */
    private void initInputListeners(Scene scene) {
        MyMouseListener myMouseListener = new MyMouseListener(this);
        scene.setOnMouseClicked(myMouseListener);
        scene.setOnMouseMoved(myMouseListener);
    }

    // Getters
    public Menu getMenu() {
        return menu;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public Playing getPlaying() {
        return playing;
    }

    public GameWon getGameWon() {
        return gameWon;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public static void main(String[] args) {
        launch();
    }
}
