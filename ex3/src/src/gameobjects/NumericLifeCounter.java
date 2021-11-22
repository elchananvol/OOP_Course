package src.gameobjects;


import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;


/**
 * handles the numeric lives of the game.
 */
public class NumericLifeCounter extends danogl.GameObject {
    private final Counter livesCounter;
    private int numOfLivesChanged;

    /**
     * Constructor.
     *
     * @param livesCounter         global lives counter of game.
     * @param topLeftCorner        top left corner of renderable
     * @param dimensions           dimensions of renderable
     * @param gameObjectCollection global game object collection
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                              danogl.util.Vector2 topLeftCorner,
                              danogl.util.Vector2 dimensions,
                              danogl.collisions.GameObjectCollection gameObjectCollection) {

        super(topLeftCorner,dimensions,null);
        gameObjectCollection.addGameObject(this,Layer.BACKGROUND);
        this.livesCounter = livesCounter;
        this.numOfLivesChanged =livesCounter.value()+1;



    }

    /**
     * Overrides update in class danogl.GameObject
     * every time that numOfLivesChanged parameter bigger than global counter is mean that global counter
     * decrease because the gamer lose life. so in update we will change the slide to current num of lives.
     */
    @Override
    public void update(float deltaTime) {
        if (livesCounter.value()<numOfLivesChanged)
        {
            TextRenderable t = new TextRenderable("");
            t.setString(Integer.toString(livesCounter.value()));
            this.renderer().setRenderable(t);
            numOfLivesChanged--;
        }
    }


}
