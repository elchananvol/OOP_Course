package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

/**
 * this strategy make 3 small balls to go out from broken brick (from right down, center down and left).
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator {
    private final Sound sound;
    private final ImageRenderable image;
    private static final int NUMOFPUCK = 3;

    /**
     * Constructor
     *
     * @param toBeDecorated - Collision strategy object to be decorated.
     * @param imageReader   imageReader object from danogl.gui
     * @param soundReader   soundReader object from danogl.gui
     */
    PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.sound = soundReader.readSound("src/assets/Bubble5_4.wav");
        this.image = imageReader.readImage("src/assets/mockBall.png", true);

    }


    /**
     * add 3 puck to board game
     *
     * @param thisObj  should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter  - global brick counter. for decrement
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        Vector2 topLeftCornerBrick = thisObj.getTopLeftCorner();
        Vector2 dimensions = thisObj.getDimensions();
        for (int i = 0; i < NUMOFPUCK; i++) {
            Vector2 topLeftCorner = new Vector2(i * (dimensions.x() / (NUMOFPUCK - 1)) + topLeftCornerBrick.x(),
                    topLeftCornerBrick.y() + dimensions.y());
            Puck ball = new Puck(topLeftCorner, new Vector2(dimensions.x() / NUMOFPUCK,
                    dimensions.x() / NUMOFPUCK), image, sound);
            getGameObjectCollection().addGameObject(ball);
        }

    }
}
