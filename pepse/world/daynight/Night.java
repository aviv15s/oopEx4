package world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    public static final String NIGHT_TAG = "night";
    public static final float MIDNIGHT_DARKNESS = 0.5f;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
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
