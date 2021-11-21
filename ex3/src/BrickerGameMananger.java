import brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Brick;
import src.gameobjects.Paddle;
import danogl.gui.UserInputListener;

import java.awt.*;
import java.util.Random;

public class BrickerGameMananger extends GameManager {
    public static int BORDER_WIDTH = 20;
    private static final Color BORDER_COLOR = Color.BLUE;
    private static final int BALL_SPEED = 300;
    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLS = 8;
    private static final int BRICK_HEIGHT = 15;
    private static final int SPACE_BETWEEN_BRICKS = 1;
    private Ball ball;
    private WindowController windowController;
    private final Counter counter = new danogl.util.Counter();

    //    BrickerGameManager.PADDLE_HEIGHT
//    BrickerGameManager.PADDLE_WIDTH
//    BrickerGameManager.BALL_RADIUS = 20

    //    BrickerGameManager.BRICK_HEIGHT
//    BrickerGameManager.BRICK_ROWS = 5
//    BrickerGameManager.BRICK_COLS = 8
//    BrickerGameManager.SPACE_BETWEEN_BRICKS
//    BrickerGameManager.HEART_SIZE
//    BrickerGameManager.NUM_OF_LIVES = 4
    public BrickerGameMananger(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        Vector2 windowDimension = windowController.getWindowDimensions();
        //ball
        Sound sound_of_ball = soundReader.readSound("assets/Bubble5_4.wav");
        ImageRenderable ball_image = imageReader.readImage("assets/ball.png", true);
        Ball ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ball_image, sound_of_ball);
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
        this.gameObjects().addGameObject(ball);
        this.ball = ball;

        //paddle
        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                windowDimension, BORDER_WIDTH);
        paddle.setCenter(new Vector2(windowDimension.x() / 2, windowDimension.y() - 20));
        this.gameObjects().addGameObject(paddle);
        //borders
        createBorders(windowDimension);
        //brick
        createBricks(imageReader, windowDimension);


    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballLocation = ball.getCenter().y();
        String prompt = "";
        if (ballLocation > windowController.getWindowDimensions().y()) {
            prompt += "you lose";
        }
        System.out.println(counter.value());
        if (counter.value() == 0) {
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

    private void createBorders(Vector2 windowDimension) {
        RectangleRenderable color = new RectangleRenderable(BORDER_COLOR);
        Vector2 sideWall = new Vector2(BORDER_WIDTH, windowDimension.y());
        Vector2 upDownWall = new Vector2(windowDimension.x(), BORDER_WIDTH);

        GameObject rightBorder = new GameObject(Vector2.ZERO, sideWall, color);
        GameObject upBorder = new GameObject(Vector2.ZERO, upDownWall, color);
        GameObject downBorder = new GameObject(new Vector2(0, windowDimension.y() - BORDER_WIDTH), upDownWall, color);
        GameObject leftBorder = new GameObject(new Vector2(windowDimension.x() - BORDER_WIDTH, 0), sideWall, color);
        this.gameObjects().addGameObject(upBorder);
        this.gameObjects().addGameObject(rightBorder);
//        this.gameObjects().addGameObject(downBorder);
        this.gameObjects().addGameObject(leftBorder);
    }

    private void createBricks(ImageReader imageReader, Vector2 windowDimension) {
        counter.reset();
        ImageRenderable brickImage = imageReader.readImage("assets/brick.png", true);
        float brick_width = (windowDimension.x() - BORDER_WIDTH * 2 - SPACE_BETWEEN_BRICKS * (BRICK_COLS - 1)) / BRICK_COLS;
        Vector2 brickDimension = new Vector2(brick_width, BRICK_HEIGHT);
        CollisionStrategy collisionStrategy = new CollisionStrategy(this.gameObjects());

        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLS; col++) {
                Vector2 location = new Vector2(BORDER_WIDTH + col * brick_width + SPACE_BETWEEN_BRICKS * col, BORDER_WIDTH + row * BRICK_HEIGHT);
                GameObject brick = new Brick(location, brickDimension, brickImage, collisionStrategy, counter);
                counter.increment();
                addGameObject(brick, true);
            }
        }
    }

    private void addGameObject(GameObject obj, boolean isStatic) {
        if (isStatic) {
            this.gameObjects().addGameObject(obj, Layer.STATIC_OBJECTS);
        } else {
            this.gameObjects().addGameObject(obj);
        }
    }


    public static void main(String[] args) {
        BrickerGameMananger game = new BrickerGameMananger("title", new Vector2(500, 700));
        game.run();

    }
}
