package src.gameobjects;

import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * it presents new ball that go in random direction to down (and not cause the game to and).
 * note: its should disappear when it crosses the window, but there is no window dimension because the API,
 * so it continues existing
 */
public class Puck extends Ball{
    private static final int BALL_SPEED = 200;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionSound the sound for collision when happen
     * */
    public Puck(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 Sound collisionSound){

        super(topLeftCorner, dimensions, renderable, collisionSound);

        Random random = new Random();
        int x_direction = BALL_SPEED;
        if (random.nextBoolean()) {
            x_direction *= -1;
        }
        this.setVelocity(new Vector2(x_direction, BALL_SPEED));
    }
}
