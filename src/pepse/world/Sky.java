package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class that generates the blueish background
 *
 * @author aviv.shemesh, ram3108_
 */
public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    public static final String SKY_TAG = "sky";

    /**
     * private constructor to prevent creating instances of this class
     */
    private Sky() {
    }

    /**
     * A static method which creates the Sky game object
     *
     * @param windowDimensions the window dimensions
     * @return gameobject for the sky
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(SKY_TAG);
        return sky;
    }
}
