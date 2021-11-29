package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * An object of this class is instantiated on collision of ball with a brick with a change camera strategy.
 * It checks ball's collision counter every frame, and once the it finds the ball has collided countDownValue
 * times since instantiation, it calls the strategy to reset the camera to normal.
 */
public class BallCollisionCountdownAgent extends GameObject {
    private final int toNotify;
    private final Ball ball;
    private final ChangeCameraStrategy owner;

    /**
     *
     * @param ball thr ball
     * @param owner the collision strategy that create this object (camera strategy in this game)
     * @param countDownValue on how much collision to follow until notify owner (tht is camera in this object)
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(Vector2.ZERO,Vector2.ZERO,null);
        this.ball = ball;
        this.owner = owner;
        this.toNotify = ball.getCollisionCount() + countDownValue;

    }

    /**
     * make camera to tern off if the ball collided "countDownValue" times
     * @param deltaTime deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(ball.getCollisionCount() >= toNotify){

            owner.turnOffCameraChange();
            owner.getGameObjectCollection().removeGameObject(this);
        }
    }
}
