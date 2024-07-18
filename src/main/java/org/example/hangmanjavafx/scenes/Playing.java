package org.example.hangmanjavafx.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.hangmanjavafx.Hangman;
import org.example.hangmanjavafx.helperMethods.DownloadSave;
import org.example.hangmanjavafx.helperMethods.GameData;
import org.example.hangmanjavafx.main.GameStates;
import org.example.hangmanjavafx.ui.CustomButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import static org.example.hangmanjavafx.main.GameStates.MENU;
import static org.example.hangmanjavafx.main.GameStates.setGameState;

/**
 * Represents the playing scene in the Hangman game.
 * Manages game mechanics such as word selection, guessing,
 * handling button clicks, and updating game state.
 */
public class Playing implements SceneMethods {
    private Hangman hangman;
    private Image hangmanImage;
    private String guessedWord;
    private List<CustomButton> letterButtons;
    private int missCount = 0;
    private final int MAX_MISS = GameData.MAX_MISS;
    private int lastMissCount = -1; // Track the last updated miss count
    private String hiddenWord;
    private CustomButton menuButton;
    private static final Logger LOGGER = Logger.getLogger(Playing.class.getName());

    /**
     * Constructor for initializing the playing scene.
     * Initializes word selection, guessed word, hangman image,
     * and buttons for letter selection.
     * @param hangman The Hangman instance controlling the game.
     */
    public Playing(Hangman hangman) {
        this.hangman = hangman;
        chooseWord();
        initGuessedWord();
        guessedWord  = getGuessedWord();
        initHangmanImage();
        initButtons();
    }

    /**
     * Initializes letter buttons and menu button for the playing scene.
     */
    private void initButtons() {
        initLetterButtons();
        initMenuButton();
    }

    /**
     * Initializes the menu button for navigating to the main menu.
     */
    private void initMenuButton() {
        menuButton = new CustomButton("Menu", 670,30, 100, 30);
    }

    /**
     * Initializes letter buttons for letter selection in the game.
     */
    private void initLetterButtons() {
        letterButtons = new ArrayList<>();
        int buttonWidth = 47;
        int buttonHeight = buttonWidth / 2;
        int xOffset = 10;
        int yOffset = 10;
        int startX = 37;
        int startY = 470;
        int x = startX;
        int y = startY;

        String LETTERS = GameData.LETTERS;

        for (int i = 0; i < LETTERS.length(); i++) {
            char letter = LETTERS.charAt(i);
            CustomButton letterButton = new CustomButton(String.valueOf(letter), x, y, buttonWidth, buttonHeight);

            letterButton.setEnabled(true);
            letterButtons.add(letterButton);

            x += buttonWidth + xOffset;
            if ((i + 1) % 13 == 0) {
                x = startX;
                y += buttonHeight + yOffset;
            }
        }
    }

    /**
     * Chooses a random word from the list of hidden words.
     */
    private void chooseWord() {
        Random random = new Random();
        String[] HIDDEN_WORDS = GameData.HIDDEN_WORDS;
        int randomWordIndex = random.nextInt(HIDDEN_WORDS.length);
        hiddenWord = HIDDEN_WORDS[randomWordIndex];
    }

    /**
     * Initializes the guessed word with dots representing unguessed letters.
     */
    private void initGuessedWord() {
        guessedWord = ".".repeat(hiddenWord.length());
    }


    /**
     * Updates the guessed word based on the player's guess.
     * @param guess The character guessed by the player.
     * @return true if the guessed letter is found in the hidden word, false otherwise.
     */
    private boolean updateGuessedWord(char guess) {
        boolean found = false;
        StringBuilder newGuessedWord = new StringBuilder(guessedWord);

        for (int i = 0; i < hiddenWord.length(); i++)
            if (hiddenWord.charAt(i) == guess) {
                newGuessedWord.setCharAt(i, guess);
                found = true;
            }

        if (!found) {
            incrementMissCount();
            isMissCountMoreThanMaxMiss();
        }

        guessedWord = newGuessedWord.toString();

        disableClickedButton(guess);

        isWordGuessed();

        return found;
    }

    /**
     * Checks if the entire word has been guessed correctly.
     * If true, sets the game state to GAME_WON.
     */
    private void isWordGuessed() {
        if (hiddenWord.equals(guessedWord)) {
            setGameState(GameStates.GAME_WON);
            LOGGER.info("Game won scene opened");
        }
    }

    /**
     * Checks if the miss count exceeds the maximum allowed misses.
     * If true, sets the game state to GAME_OVER.
     */
    private void isMissCountMoreThanMaxMiss() {
        if (missCount > MAX_MISS) {
            setGameState(GameStates.GAME_OVER);
            LOGGER.info("Game over scene opened");
        }
    }

    /**
     * Disables the letter button corresponding to the guessed letter.
     * @param guess The guessed letter.
     */
    private void disableClickedButton(char guess) {
        for (CustomButton letterButton: letterButtons)
            if (letterButton.getText().equals(String.valueOf(guess)))
                letterButton.setEnabled(false);
    }

    /**
     * Resets the game state and initializes everything for a new game session.
     */
    public void resetEverything() {
        initButtons();
        initHangmanImage();

        chooseWord();
        initGuessedWord();
        missCount = 0;
    }

    /**
     * Updates the game state when the miss count changes.
     */
    public void update() {
        if (missCount  != lastMissCount && missCount <= GameData.MAX_MISS) {
            hangmanImage = DownloadSave.getImage("hangman_" + getMissCount() + ".png");
            lastMissCount = missCount;
        }
    }

    /**
     * Initializes the hangman image at the beginning of the game.
     */
    private void initHangmanImage() {
        hangmanImage = DownloadSave.getImage("hangman_0.png");
    }

    /**
     * Renders the playing scene by drawing the hangman image, guessed word,
     * letter buttons, and menu button on the canvas.
     * @param gc The graphics context used for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(242, 244, 250));
        gc.fillRect(0, 0, hangman.getGameWindow().getWindowWidth(), hangman.getGameWindow().getWindowHeight());
        drawHangmanImage(gc);
        drawGuessedWord(gc);
        drawButtons(gc);
    }

    /**
     * Draws the letter buttons on the playing scene.
     * @param gc The graphics context used for rendering.
     */
    private void drawButtons(GraphicsContext gc) {
        drawLetterButtons(gc);
        drawMenuButton(gc);
    }

    /**
     * Draws the menu button on the playing scene.
     * @param gc The graphics context used for rendering.
     */
    private void drawMenuButton(GraphicsContext gc) {
        menuButton.draw(gc);
    }

    /**
     * Draws the letter buttons on the playing scene.
     * @param gc The graphics context used for rendering.
     */
    private void drawLetterButtons(GraphicsContext gc) {
        for (CustomButton button : letterButtons) {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(16));
            button.draw(gc);
        }
    }

    /**
     * Draws the hangman image on the playing scene.
     * @param gc The graphics context used for rendering.
     */
    private void drawHangmanImage(GraphicsContext gc) {
        gc.drawImage(hangmanImage, 314, 30);
    }


    /**
     * Draws the guessed word on the playing scene.
     * @param gc The graphics context used for rendering.
     */
    private void drawGuessedWord(GraphicsContext gc) {
        if (guessedWord != null) {
            gc.setFont(Font.font(50));
            gc.setFill(Color.BLACK);

            // Create a Text object to measure the width of the guessed word
            Text text = new Text(guessedWord);
            text.setFont(Font.font(50));
            double textWidth = text.getLayoutBounds().getWidth();

            // Calculate the x position to center the text
            double x = (hangman.getGameWindow().getWindowWidth() - textWidth) / 2;
            double y = 400;

            gc.fillText(guessedWord, x, y);
        }
    }

    /**
     * Handles mouse click events on the playing scene.
     * Checks if a letter button or menu button was clicked and updates game state accordingly.
     * @param x The x-coordinate of the mouse click.
     * @param y The y-coordinate of the mouse click.
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (menuButton.getBounds().contains(x, y))
            setGameState(MENU);

        for (CustomButton letterButton: letterButtons)
            if (letterButton.getBounds().contains(x, y) && letterButton.isEnabled()) {
                char guessedLetter = letterButton.getText().charAt(0);
                if (updateGuessedWord(guessedLetter)) {
                    guessedWord = getGuessedWord();
                    LOGGER.info("Letter: " + guessedLetter + " was clicked");

                    letterButton.setEnabled(false);
                } else
                    LOGGER.info("Letter" + guessedLetter + " was incorrect");
            } else {
                // when button is disabled we click on it
            }

    }

    /**
     * Handles mouse movement events on the playing scene.
     * Updates the hover state of menu button and letter buttons based on mouse coordinates.
     * @param x The x-coordinate of the mouse cursor.
     * @param y The y-coordinate of the mouse cursor.
     */
    @Override
    public void mouseMoved(int x, int y) {
        menuButton.setButtonHovered(menuButton.getBounds().contains(x, y));

        for (CustomButton letterButton: letterButtons)
            letterButton.setButtonHovered(letterButton.getBounds().contains(x, y));
    }

    // Getters
    private int getMissCount() {
        return missCount;
    }

    private String getGuessedWord() {
        return guessedWord;
    }

    private void incrementMissCount() {
        this.missCount++;
    }
}
