package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * this class  inheritor from StatusDefines for object that cause paddle to change size
 * to be narrow/ or wide depends on size parameter
 */
public class BuffChangeSizeOfPaddle extends StatusDefines {
    private final float size;

    /**
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param renderable           The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection gameObjectCollection
     * @param brick                brick the collision make it appear.
     * @param windowDimensions     window dimensions
     */
    public BuffChangeSizeOfPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection
                                        gameObjectCollection, GameObject brick, Vector2 windowDimensions,float size) {
        super(topLeftCorner, dimensions, renderable, gameObjectCollection, brick, windowDimensions);
        this.size = size;
    }

    /**
     * @param other     should be puddle by default.
     * @param collision collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        other.setDimensions(new Vector2(other.getDimensions().x() * size, other.getDimensions().y()));
    }
}
