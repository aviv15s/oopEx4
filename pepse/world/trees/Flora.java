package world.trees;

import util.JumpingObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class that create a forest and pass to the created objects the consumer
 *
 * @author aviv.shemesh, ram3108_
 */
public class Flora {
    public static final int BLOCK_SIZE = 30;
    private static final double LEAF_PROB = 0.1;
    private List<Tree> TreesArray;
    private Function<Float, Float> funcToGetY;
    private Consumer<JumpingObserver> observerTarget;

    /**
     * default constructor
     *
     * @param funcToGetY     func to call when wants to get y value of the ground at x place.
     * @param observerTarget function to pass that the objects created will get.
     */
    public Flora(Function<Float, Float> funcToGetY, Consumer<JumpingObserver> observerTarget) {
        this.funcToGetY = funcToGetY;
        TreesArray = new ArrayList<Tree>();
        this.observerTarget = observerTarget;
    }

    /**
     * func to call when wants to create several trees in range in certainty probability;
     *
     * @param minX
     * @param maxX
     * @return
     */
    public List<Tree> createInRange(int minX, int maxX) {
        Random random = new Random();
        Double probability;
        int NumCols = (maxX - minX) / BLOCK_SIZE;
        for (int colInd = 0; colInd < NumCols; colInd++) {
            probability = random.nextDouble();
            if (probability < LEAF_PROB) {
                float xValue = minX + BLOCK_SIZE * colInd;
                TreesArray.add(new Tree(xValue, funcToGetY.apply(xValue), observerTarget));
            }
        }
        return TreesArray;
    }
}
