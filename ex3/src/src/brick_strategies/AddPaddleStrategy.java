package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * this strategy create mock puddle at center of window. the mock puddle limit by border,
 * and disappear after 4 collision with balls
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    private static final int numCollisionsToDisappear = 4;
    private static final Vector2 PADDLESIZE = new Vector2(100, 15);
    public static final int BORDER_WIDTH = 20;
    private final ImageRenderable paddleImage;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final static String IMAGEPATH = "src/assets/paddle.png";

    /**
     * Constructor
     *
     * @param toBeDecorated - Collision strategy object to be decorated.
     * @param imageReader     imageReader object from danogl.gui
     * @param inputListener   inputListener object from danogl.gui
     * @param windowDimensions window dimensions.
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             danogl.gui.ImageReader imageReader,
                             danogl.gui.UserInputListener inputListener,
                             danogl.util.Vector2 windowDimensions) {

        super(toBeDecorated);

        this.paddleImage = imageReader.readImage(IMAGEPATH, true);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;


    }

    /**
     * To be called on brick collision. implantation: create mock puddle and add it to board.
     * @param thisObj  should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter  - global brick counter. for decrement
     */
    public void onCollision(danogl.GameObject thisObj, GameObject otherObj, danogl.util.Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (!MockPaddle.isInstantiated) {
            MockPaddle mockPaddle = new MockPaddle(new Vector2(BORDER_WIDTH,
                    windowDimensions.y() / 2), PADDLESIZE, paddleImage, inputListener,
                    windowDimensions, getGameObjectCollection(), BORDER_WIDTH, numCollisionsToDisappear);
            getGameObjectCollection().addGameObject(mockPaddle);
        }

    }

}
