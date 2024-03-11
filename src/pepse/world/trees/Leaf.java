package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.JumpingObserver;

import java.util.Random;

/**
 * Class that create a Leaf that is a jumping observer and GameObject
 *
 * @author aviv.shemesh, ram3108_
 */
public class Leaf extends GameObject implements JumpingObserver {

    private static final Vector2 MIN_LEAF_SIZE = new Vector2(30, 30);
    private static final Vector2 MAX_LEAF_SIZE = new Vector2(40, 40);
    private static final float TIME_TRANSITION_ROTATE = 4;
    private static final float TIME_TRANSITION_SIZE = 1;
    private static final float MAX_ANGLE = 40;

    /**
     * constructor
     *
     * @param topLeftCorner top left corner of leaf.
     * @param dimensions    dimensions of leaf.
     * @param renderable    render of leaf.
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        Random random = new Random();
        setTransitionToLeaf(random.nextFloat());
    }

    /**
     * activate the transition of one leaf.
     *
     * @param waitTime to activate after.
     */
    private void setTransitionToLeaf(float waitTime) {
        new ScheduledTask(this, waitTime, false, () -> transitionsActivate(this));

    }

    /**
     * function that is called by the scheduler.
     *
     * @param leaf object to act on.
     */
    private void transitionsActivate(GameObject leaf) {
        Transition<Float> transitionRotate = new Transition<Float>(
                leaf,
                (angle) -> leaf.renderer().setRenderableAngle(angle),
                0f,
                MAX_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                TIME_TRANSITION_ROTATE,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
        Transition<Vector2> transitionSize = new Transition<Vector2>(
                leaf,
                (dimension) -> leaf.setDimensions(dimension),
                MIN_LEAF_SIZE,
                MAX_LEAF_SIZE,
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                TIME_TRANSITION_SIZE,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    /**
     * when avatar jumps rotate by 90 degrees
     */
    @Override
    public void onPlayerJump() {
        Transition<Float> transitionRotate = new Transition<Float>(
                this,
                (angle) -> renderer().setRenderableAngle(angle),
                renderer().getRenderableAngle(),
                renderer().getRenderableAngle() + 90f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                0.5f,
                Transition.TransitionType.TRANSITION_ONCE,
                null
        );
    }
}
