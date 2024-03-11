package world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private static final float SIZE = 100f;
    private static final float CYCLE_RADIUS_FACTOR = 0.4f;
    public static final String SUN_TAG = "sun";

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        float sunCycleRadius = Math.min(windowDimensions.x(), windowDimensions.y()) * CYCLE_RADIUS_FACTOR;
        Renderable ovalRenderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO, Vector2.ONES.mult(SIZE), ovalRenderable);
        sun.setCenter(new Vector2(windowDimensions.x()/2,windowDimensions.y()/2));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);

        Vector2 cycleCenter = new Vector2(windowDimensions.x()/2, windowDimensions.y()/2); //TODO understand how to set y value based on height at x=0
        Vector2 initialSunCenter = cycleCenter.add(Vector2.UP.mult(sunCycleRadius));

        Transition<Float> transition = new Transition<Float>(
                sun,
                (angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter)),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );
        return sun;
    }
}
