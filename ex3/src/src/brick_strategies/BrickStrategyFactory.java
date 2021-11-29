package src.brick_strategies;

import src.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

import java.util.Random;

/**
 * factory of strategy for brick collision with ball.
 */
public class BrickStrategyFactory {

    private final GameObjectCollection gameObjectCollection;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private final Random generator = new Random();

    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager, ImageReader imageReader,
                                SoundReader soundReader, UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions) {
        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;


    }

    /**
     * method randomly selects between 6 strategies and returns one CollisionStrategy object which is a
     * RemoveBrickStrategy decorated by one of the decorator strategies, or decorated by two randomly
     * selected strategies, or decorated by one of the decorator strategies and a pair of additional two
     * decorator strategies.
     *
     * @return CollisionStrategy object that was chosen randomly
     */
    public CollisionStrategy getStrategy() {
        //choose randomly between the possible brick strategies

        CollisionStrategy none = new RemoveBrickStrategy(gameObjectCollection);
        CollisionStrategy puckStrategy = new PuckStrategy(none, imageReader, soundReader);
        CollisionStrategy addPaddleStrategy = new AddPaddleStrategy(none, imageReader, inputListener, windowDimensions);
        CollisionStrategy changeCameraStrategy = new ChangeCameraStrategy(none, windowController, gameManager);
        CollisionStrategy changePaddleLength = new ChangePaddleLengthStrategy(none, imageReader, windowDimensions);
        CollisionStrategy[] arrayForDouble = {changePaddleLength, addPaddleStrategy, puckStrategy, changeCameraStrategy};
        CollisionStrategy doubleStrategy = new DoubleStrategy(none, arrayForDouble);
        CollisionStrategy[] myArray = {none, doubleStrategy, changePaddleLength, changeCameraStrategy,
                                            addPaddleStrategy, puckStrategy};
        int randomIndex = generator.nextInt(myArray.length);

        return myArray[randomIndex];
    }


}
