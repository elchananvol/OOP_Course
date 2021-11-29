package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.MockPaddle;
import src.gameobjects.Puck;

/**
 * this strategy make to camera follow after the ball (just the main ball!) when collision
 * happen between that ball end brick with this strategy
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private static final int NUMOFCOLLISION = 4;
    private static boolean isInstantiated = false;


    /**
     * @param toBeDecorated    - Collision strategy object to be decorated.
     * @param windowController - danogl object
     * @param gameManager      - main class of game.
     */
    ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                         WindowController windowController, BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * To be called on brick collision.
     *
     * @param thisObj  should be the brick obj. for removing from board.
     * @param otherObj if is main ball then change camera, else do nothing
     * @param counter  - global brick counter. for decrement
     */
    public void onCollision(danogl.GameObject thisObj, GameObject otherObj, danogl.util.Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (otherObj instanceof Ball && !(otherObj instanceof Puck)) {
            if (!isInstantiated) {
                isInstantiated = true;
                Ball myBall = (Ball) otherObj;
                gameManager.setCamera(
                        new Camera(otherObj, Vector2.ZERO,windowController.getWindowDimensions().mult(1.2f),
                                windowController.getWindowDimensions()));
                getGameObjectCollection().addGameObject(new BallCollisionCountdownAgent(myBall,
                        this, NUMOFCOLLISION));
            }
        }

    }

    /**
     * this method make to camera stop moving. it intended to be used by agent that follow ball collision.
     */
    public void turnOffCameraChange() {

        gameManager.setCamera(null);
        isInstantiated = false;

    }


}
