package world.daynight.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Leaf extends GameObject{

    private static final Vector2 MIN_LEAF_SIZE = new Vector2(15,15);
    private static final Vector2 MAX_LEAF_SIZE = new Vector2(17,15);
    private static final float TIME_TRANSITION_ROTATE = 4;
    private static final float TIME_TRANSITION_SIZE = 1;
    private static final float  MAX_ANGLE = 40;


    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        Random random = new Random();
        setTransitionToLeaf(this, random.nextFloat());
    }

    private void setTransitionToLeaf(GameObject leaf, float waitTime){
        new ScheduledTask(leaf,waitTime,false, ()->transitionsActivate(leaf));

    }
    private void transitionsActivate(GameObject leaf){
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
}
