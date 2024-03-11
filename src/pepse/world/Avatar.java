package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;
import util.JumpingObserver;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;

/**
 * class for the player's character
 *
 * @author aviv.shemesh, ram3108_
 */
public class Avatar extends GameObject {
    public static final String AVATAR_TAG = "avatar";
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    public static final Vector2 DIMENSIONS = new Vector2(50, 100);
    private static final float MOVEMENT_ENERGY_COST = 0.5f;
    private static final float JUMP_ENERGY_COST = 10f;
    private static final float ANIMATION_SPEED = 0.2f;
    private static final float ENERGY_REPLENISH_RATE = 1f;
    private final UserInputListener listener;

    private static final float MAX_ENERGY = 100f;
    private float energy = MAX_ENERGY;

    private static final String[] IDLE_SPRITES = new String[]{
            "assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png",};
    private final AnimationRenderable idleRenderable;
    private static final String[] RUNNING_SPRITES = new String[]{
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png",};

    private final AnimationRenderable runningRenderable;
    private static final String[] JUMPING_SPRITES = new String[]{
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png",};
    private final AnimationRenderable jumpingRenderable;
    private final List<JumpingObserver> jumpObserversList = new ArrayList<>();

    /**
     * constructor for the class
     *
     * @param pos           initial position of the character
     * @param inputListener listener for user's events
     * @param imageReader   image reader for animations
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, DIMENSIONS, null);
        listener = inputListener;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        idleRenderable = new AnimationRenderable(IDLE_SPRITES, imageReader, true, ANIMATION_SPEED);

        runningRenderable = new AnimationRenderable(RUNNING_SPRITES, imageReader, true, ANIMATION_SPEED);

        jumpingRenderable = new AnimationRenderable(JUMPING_SPRITES, imageReader, true, ANIMATION_SPEED);

        renderer().setRenderable(idleRenderable);
        setTag(AVATAR_TAG);
    }

    /**
     * basic update function for the class. updates movements and energy.
     *
     * @param deltaTime time since last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        transform().setVelocityX(0);
        float xVel = 0;
        if (listener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= VELOCITY_X;
        }
        if (listener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += VELOCITY_X;
        }
        if (xVel != 0 && energy >= MOVEMENT_ENERGY_COST) {
            energy -= MOVEMENT_ENERGY_COST;
            transform().setVelocityX(xVel);
            renderer().setRenderable(runningRenderable);
        }
        if (listener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy > JUMP_ENERGY_COST) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= JUMP_ENERGY_COST;
            for (JumpingObserver observer : jumpObserversList) {
                observer.onPlayerJump();
            }
        }

        if (getVelocity().isZero()) {
            energy = Math.min(MAX_ENERGY, energy + ENERGY_REPLENISH_RATE);
            renderer().setRenderable(idleRenderable);
        } else if (getVelocity().x() != 0) {
            renderer().setRenderable(runningRenderable);
            renderer().setIsFlippedHorizontally(getVelocity().x() < 0);
        } else {
            renderer().setRenderable(jumpingRenderable);
        }
    }

    /**
     * getter function for energy level
     *
     * @return energy
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * additive function for the energy. cannot exceed max value.
     *
     * @param value how much energy to add
     */
    public void addEnergy(float value) {
        energy = Math.min(MAX_ENERGY, energy + value);
    }

    /**
     * subscribe to event notifications when the player jumps
     *
     * @param observer the observer implementation
     */
    public void subscribeToPlayerJumping(JumpingObserver observer) {
        jumpObserversList.add(observer);
    }

    /**
     * unsubscribe to event notifications when the player jumps
     *
     * @param observer the observer implementation
     */
    public void unsubscribeToPlayerJumping(JumpingObserver observer) {
        jumpObserversList.remove(observer);
    }
}
