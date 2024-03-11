package world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class to create the halo effect on the sun
 *
 * @author aviv.shemesh, ram3108_
 */
public class SunHalo {
    private static final Color SUN_COLOR = new Color(255, 255, 0, 20);
    private static final float HALO_SIZE_FACTOR = 3;
    public static final String HALO_TAG = "halo";

    /**
     * private constructor to prevent instances of the class
     */
    private SunHalo() {
    }


    /**
     * static function to create the halo effect
     *
     * @param sun sun object for which the halo will follow
     * @return game object representing the halo effect
     */
    public static GameObject create(GameObject sun) {
        Renderable ovalRenderable = new OvalRenderable(SUN_COLOR);
        float haloSize = sun.getDimensions().magnitude() * HALO_SIZE_FACTOR;
        GameObject halo = new GameObject(Vector2.ZERO, Vector2.ONES.mult(haloSize), ovalRenderable);
        halo.setCenter(sun.getCenter());
        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        halo.setTag(HALO_TAG);
        halo.addComponent((deltaTime -> halo.setCenter(sun.getCenter())));
        return halo;
    }
}
