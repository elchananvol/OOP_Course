package src;

import src.brick_strategies.BrickStrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.*;
import danogl.gui.UserInputListener;

import java.awt.*;
import java.util.Random;

/**
 *
 */
public class BrickerGameManager extends GameManager {
    public static int BORDER_WIDTH = 20;
    private static final Color BORDER_COLOR = Color.CYAN;
    private static final int BALL_SPEED = 200;
    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLS = 8;
    private static final int BRICK_HEIGHT = 15;
    private static final int SPACE_BETWEEN_BRICKS = 1;
    private static final int SIDE_BAR = 0;
    private static final int NUM_OF_LIVES = 4;
    private static final int LIVES_FONT_SIZE = 20;
    private static final int BALL_SIZE = 40;

    private Ball ball;
    private WindowController windowController;
    private Vector2 windowDimension;
    private final Counter bricksCounter = new danogl.util.Counter();
    private final danogl.util.Counter livesCounter = new danogl.util.Counter(0);

    /**
     * default constructor.
     *
     * @param windowTitle      title
     * @param windowDimensions pixel dimensions for game window height x width
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {

        super(windowTitle, windowDimensions);
    }

    /**
     * Calling this function should initialize the game window. It initializes objects in the game window
     * - ball, paddle, walls, life counters, bricks. This version of the game has 5 rows, 8 columns of bricks.
     *
     * @param imageReader      an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader      a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener    an InputListener instance for reading user input.
     * @param windowController controls visual rendering of the game window and object renderables.
     */

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimension = windowController.getWindowDimensions();
        //windowController.setTargetFramerate(40);
        createBall(imageReader, soundReader);
        createPaddle(imageReader, inputListener);
        createBorders();
        BrickStrategyFactory p = new BrickStrategyFactory(gameObjects(), this, imageReader, soundReader,
                inputListener, windowController, windowDimension);
        createBricks(imageReader, p);
        createLivesCounter(imageReader);


    }

    /**
     * create Lives graphic symbol as NUM_OF_LIVES times at left down corner of window.
     * above the graphic create number symbol.
     *
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     */
    private void createLivesCounter(ImageReader imageReader) {
        livesCounter.reset();
        livesCounter.increaseBy(NUM_OF_LIVES);
        Vector2 size_of_widget = new Vector2(LIVES_FONT_SIZE, LIVES_FONT_SIZE);
        ImageRenderable liveImage = imageReader.readImage("src/assets/heart.png", true);
        new GraphicLifeCounter(new Vector2(BORDER_WIDTH + SIDE_BAR, windowDimension.y() - LIVES_FONT_SIZE
                - BORDER_WIDTH), size_of_widget, livesCounter, liveImage, this.gameObjects(), NUM_OF_LIVES);
        new NumericLifeCounter(livesCounter, new Vector2(BORDER_WIDTH + SIDE_BAR, windowDimension.y()
                - LIVES_FONT_SIZE * 2 - BORDER_WIDTH), size_of_widget, this.gameObjects());


    }

    /**
     * create paddle in midlle of window down above border. in size dimension (x,y) = (100,15)
     *
     * @param imageReader   an ImageReader instance for reading images from files for rendering of objects.
     * @param inputListener an InputListener instance for reading user input.
     */

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        ImageRenderable paddleImage = imageReader.readImage("src/assets/paddle.png", true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                windowDimension, BORDER_WIDTH);
        paddle.setCenter(new Vector2(windowDimension.x() / 2, windowDimension.y() - BORDER_WIDTH - 7));
        this.gameObjects().addGameObject(paddle);

    }

    /**
     * create ball in "BALL_SIZE" at center of window in random direction with 45 degree angel
     *
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader object that can read wev files and then to play it. create automatically by GameManager
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Sound sound_of_ball = soundReader.readSound("src/assets/Bubble5_4.wav");
        ImageRenderable ball_image = imageReader.readImage("src/assets/ball.png", true);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ball_image, sound_of_ball);
        setBallAtCenter();
        this.gameObjects().addGameObject(ball);
    }

    /**
     * set ball at center of window in random direction with 45 degree angel
     */
    private void setBallAtCenter() {


        Random random = new Random();
        int x_direction = BALL_SPEED;
        int y_direction = BALL_SPEED;
        if (random.nextBoolean()) {
            x_direction *= -1;
        }
        if (random.nextBoolean()) {
            y_direction *= -1;
        }
        ball.setVelocity(new Vector2(x_direction, y_direction));
        ball.setCenter(windowDimension.mult(0.5f));
    }

    /**
     * check every deltaTime if game end
     *
     * @param deltaTime time between updates. For internal use by game engine.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkEndGame();
    }

    /**
     * if ball touch the down border, then gamer lose one life. if he lost all life - game over.
     * if all bricks are gone then game end with win.
     */
    private void checkEndGame() {
        float ballLocation = ball.getTopLeftCorner().y() + ball.getDimensions().y();
        String prompt = "";
        if (ballLocation > windowController.getWindowDimensions().y() - BORDER_WIDTH) {
            livesCounter.decrement();
            setBallAtCenter();
            if (livesCounter.value() == 0) {
                prompt += "you lose";
            }
        }
        if (bricksCounter.value() <= 0) {
            prompt += "you win";
        }

        if (!prompt.isEmpty()) {
            prompt += ", play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }

    }

    private void createBorders() {
        RectangleRenderable color = new RectangleRenderable(BORDER_COLOR);
        Vector2 sideWall = new Vector2(BORDER_WIDTH, windowDimension.y());
        Vector2 upDownWall = new Vector2(windowDimension.x(), BORDER_WIDTH);

        GameObject rightBorder = new GameObject(Vector2.ZERO.add(new Vector2(SIDE_BAR, 0)), sideWall, color);
        GameObject upBorder = new GameObject(Vector2.ZERO.add(new Vector2(SIDE_BAR, 0)), upDownWall, color);
        GameObject downBorder = new GameObject(new Vector2(0, windowDimension.y() - BORDER_WIDTH),
                upDownWall, color);
        GameObject leftBorder = new GameObject(new Vector2(windowDimension.x() - BORDER_WIDTH, 0),
                sideWall, color);
        this.gameObjects().addGameObject(upBorder, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(rightBorder, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(downBorder, Layer.BACKGROUND);
        this.gameObjects().addGameObject(leftBorder, Layer.STATIC_OBJECTS);

    }

    /**
     * create bricks insides the borders.
     *
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     */
    private void createBricks(ImageReader imageReader, BrickStrategyFactory factory) {

        bricksCounter.reset();
        ImageRenderable brickImage = imageReader.readImage("src/assets/brick.png", true);
        float brick_width = (windowDimension.x() - SIDE_BAR - BORDER_WIDTH * 2 - SPACE_BETWEEN_BRICKS *
                (BRICK_COLS - 1)) / BRICK_COLS;
        Vector2 brickDimension = new Vector2(brick_width, BRICK_HEIGHT);

        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLS; col++) {
                Vector2 location = new Vector2(BORDER_WIDTH + SIDE_BAR + (brick_width
                        + SPACE_BETWEEN_BRICKS) * col, BORDER_WIDTH + row * BRICK_HEIGHT);
                GameObject brick = new Brick(location, brickDimension, brickImage, factory.getStrategy(),
                        bricksCounter);
                bricksCounter.increment();
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Entry point for game. contain:
     * 1. An instantiation call to BrickerGameManager constructor.
     * 2. A call to run() method of instance of BrickerGameManager.
     * initialize game window of dimensions (x,y) = (700,500).
     *
     * @param args -
     */
    public static void main(String[] args) {
        BrickerGameManager game = new BrickerGameManager("Brick", new Vector2(700, 500));
        game.run();

    }
}
