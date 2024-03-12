package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class which creates the object which darkens the screen on night time
 *
 * @author aviv.shemesh, ram3108_
 */
public class Night {
    public static final String NIGHT_TAG = "night";
    private static final float MIDNIGHT_DARKNESS = 0.5f;

    /**
     * private constructor to prevent creation of instances of this class
     */
    private Night() {
    }

    /**
     * A static function used to create the object of the night which changes it's darkness based on the cycle
     * of day and night.
     *
     * @param windowDimensions dimensions of the screen
     * @param cycleLength      length of day and night cycle
     * @return the gameObject which sets the night
     */

    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        Renderable rectangleRenderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        Transition<Float> transition = new Transition<>(
                night,
                night.renderer()::setOpaqueness,
                0f,
                MIDNIGHT_DARKNESS,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
        return night;

    }

}
