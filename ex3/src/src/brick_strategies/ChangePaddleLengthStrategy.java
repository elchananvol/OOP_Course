package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.BuffChangeSizeOfPaddle;

import java.util.Random;


/**
 * this strategy cause to one image to appear at board and when this image
 * touch un the paddle its make it grow or to be shortened depend on image.
 */
public class ChangePaddleLengthStrategy extends RemoveBrickStrategyDecorator {
    private static final Random random = new Random();
    private static final String NARROWIMAGEPATH = "src/assets/buffNarrow.png";
    private static final String WIDENIMAGEPATH = "src/assets/buffWiden.png";
    private static final Vector2 DIMENSION = new Vector2(20, 20);
    private static final float SIZENARROW = 0.7f;
    private static final float SIZEWIDEN = 1.3f;
    private final Vector2 windowDimensions;
    private final ImageRenderable image;
    private boolean narrow = false;


    /**
     * Constructor
     * @param toBeDecorated    - Collision strategy object to be decorated.
     * @param imageReader      imageReader imageReader object from danogl.gui
     * @param windowDimensions window dimensions.
     */
    public ChangePaddleLengthStrategy(CollisionStrategy toBeDecorated, danogl.gui.ImageReader imageReader,
                                      Vector2 windowDimensions) {
        super(toBeDecorated);
        this.windowDimensions = windowDimensions;
        if (random.nextBoolean()) {
            this.image = imageReader.readImage(NARROWIMAGEPATH, true);
            this.narrow = true;
        } else {
            this.image = imageReader.readImage(WIDENIMAGEPATH, true);
        }
    }


    /**
     * To be called on brick collision.
     * it adds image with behavior that describe above.
     *
     * @param thisObj  should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter  - global brick counter. for decrement
     */
    public void onCollision(danogl.GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        GameObject object;
        if (narrow) {
            object = new BuffChangeSizeOfPaddle(Vector2.ZERO, DIMENSION, image, getGameObjectCollection(),
                    thisObj, windowDimensions, SIZENARROW);
        } else {
            object = new BuffChangeSizeOfPaddle(Vector2.ZERO, DIMENSION, image, getGameObjectCollection(), thisObj,
                    windowDimensions, SIZEWIDEN);
        }
        getGameObjectCollection().addGameObject(object, Layer.STATIC_OBJECTS);
    }

}
