package world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;
import util.JumpingObserver;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final String IMAGE_PATH = "assets/idle_0.png";
    public static final Vector2 DIMENSIONS = new Vector2(50,100);
    public static final float MOVEMENENT_ENERGY_COST = 0.5f;
    public static final float JUMP_ENERGY_COST = 10f;
    public static final float ANIMATION_SPEED = 0.2f;
    private final UserInputListener listener;

    private static final float MAX_ENERGY = 100f;
    private float energy = MAX_ENERGY;

    private final AnimationRenderable idleRenderable;
    private final AnimationRenderable runningRenderable;
    private final AnimationRenderable jumpingRenderable;
    private final List<JumpingObserver> jumpObserversList = new ArrayList<>();

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos, DIMENSIONS, null);
        listener = inputListener;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        idleRenderable = new AnimationRenderable(
                new String[]{"assets/idle_0.png", "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png",}
                , imageReader, true, ANIMATION_SPEED);

        runningRenderable = new AnimationRenderable(
                new String[]{"assets/run_0.png", "assets/run_1.png", "assets/run_2.png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png",}
                , imageReader, true, ANIMATION_SPEED);

        jumpingRenderable = new AnimationRenderable(
                new String[]{"assets/jump_0.png", "assets/jump_1.png", "assets/jump_2.png", "assets/jump_3.png",}
                , imageReader, true, ANIMATION_SPEED);

        renderer().setRenderable(idleRenderable);
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
            renderer().setRenderable(runningRenderable);
        }
        if(listener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy > JUMP_ENERGY_COST) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= JUMP_ENERGY_COST;
            for (JumpingObserver observer: jumpObserversList){
                observer.onPlayerJump();
            }
        }

        if (getVelocity().isZero()){
            energy = Math.min(MAX_ENERGY, energy+1f);
            renderer().setRenderable(idleRenderable);
        } else if (getVelocity().x() != 0) {
            renderer().setRenderable(runningRenderable);
            renderer().setIsFlippedHorizontally(getVelocity().x()<0);
        } else {
            renderer().setRenderable(jumpingRenderable);
        }
    }

    public float getEnergy(){
        return energy;
    }

    public void addEnergy(float value){
        energy = Math.min(MAX_ENERGY, energy + value);
    }

    public void subscribeToPlayerJumping(JumpingObserver observer){
        jumpObserversList.add(observer);
    }

    public void unsubscribeToPlayerJumping(JumpingObserver observer){
        jumpObserversList.remove(observer);
    }
}
