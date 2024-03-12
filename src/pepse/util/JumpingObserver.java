package pepse.util;

/**
 * Functional interface to implement the observer design pattern
 *
 * @author aviv.shemesh, ram3108_
 */
@FunctionalInterface
public interface JumpingObserver {
    /**
     * used to announce to observers that the player jumped
     */
    public void onPlayerJump();
}
