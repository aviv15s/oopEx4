package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.JumpingObserver;
import pepse.world.Avatar;

import java.awt.*;
import java.util.Random;

/**
 * Class that create a fruit
 *
 * @author aviv.shemesh, ram3108_
 */
public class Fruit extends GameObject implements JumpingObserver {
    public static final String FRUIT_TAG = "fruit";
    private static final int WAIT_TIME = 30;
    private static final float FRUIT_ENERGY_BONUS = 10f;
    private static final Color[] POSSIBLE_COLORS = new Color[]{
            Color.red, Color.yellow
    };
    private int myColor;

    /**
     * @param topLeftCorner
     * @param dimensions
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions,
                new OvalRenderable(POSSIBLE_COLORS[0]));
        myColor = 0;
        this.setTag(FRUIT_TAG);
    }

    /**
     * Checks if the apple is unseen and if so does nothing.
     * Else, if so make him invisible and do ScheduledTask.
     *
     * @param other     The collision partner.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionStay(GameObject other, Collision collision) {
        super.onCollisionStay(other, collision);
        if (other.getTag().equals(Avatar.AVATAR_TAG) && this.renderer().getRenderable() != null) {
            this.renderer().setRenderable(null);
            new ScheduledTask(this,
                    WAIT_TIME,
                    false,
                    () -> transitionsActivate(this));

            Avatar avatar = (Avatar) other;
            avatar.addEnergy(FRUIT_ENERGY_BONUS);
        }
    }

    /**
     * function that is called by the scheduler.
     *
     * @param gameObject object to act on.
     */
    private void transitionsActivate(GameObject gameObject) {
        gameObject.renderer().setRenderable(new OvalRenderable(POSSIBLE_COLORS[myColor]));
    }

    /**
     * when avatar jumps change color and if apple is visible change the renderer.
     */
    @Override
    public void onPlayerJump() {
        myColor = 1 - myColor;
        if (this.renderer().getRenderable() != null) {
            renderer().setRenderable(new OvalRenderable(POSSIBLE_COLORS[myColor]));
        }
    }

}
