package world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

public class Avatar extends GameObject {
    private static final String IMAGE_PATH = "assets/idle_0.png";
    private static final Vector2 DIMENSIONS = new Vector2(100,100);
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos, DIMENSIONS, imageReader.readImage(IMAGE_PATH,true));
    }
}
