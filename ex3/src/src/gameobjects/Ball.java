package src.gameobjects;

import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.Sound;
import danogl.GameObject;

/**
 * holds a Ball object of the game.
 */
public class Ball extends GameObject{
    private Sound sound;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,Sound sound) {
        super(topLeftCorner, dimensions, renderable);
        this.sound = sound;
    }
    /**
     * On collision, object velocity is reflected about the normal vector of the surface it collides with
     * and make sound.
     * @param other other GameObject instance participating in collision.
     * @param collision Collision object.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.setVelocity(this.getVelocity().flipped(collision.getNormal()));
        sound.play();
    }
}
