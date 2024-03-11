package world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final String IMAGE_PATH = "assets/idle_0.png";
    public static final Vector2 DIMENSIONS = new Vector2(50,100);
    public static final float MOVEMENENT_ENERGY_COST = 0.5f;
    public static final float JUMP_ENERGY_COST = 10f;
    private final UserInputListener listener;

    private static final float MAX_ENERGY = 100f;
    private float energy = MAX_ENERGY;
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos, DIMENSIONS, imageReader.readImage(IMAGE_PATH,true));
        listener = inputListener;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(listener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= VELOCITY_X;
        }
        if(listener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += VELOCITY_X;
        }        transform().setVelocityX(xVel);
        if (xVel != 0 && energy > MOVEMENENT_ENERGY_COST){
            energy -= MOVEMENENT_ENERGY_COST;
            transform().setVelocityX(xVel);
        }
        if(listener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy > JUMP_ENERGY_COST) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= JUMP_ENERGY_COST;
        }

        if (getVelocity().isZero()){
            energy += 1f;
        }
    }
}
