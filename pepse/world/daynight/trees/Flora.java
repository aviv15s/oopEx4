package world.daynight.trees;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import danogl.GameObject;
public class Flora {
    public static final int BLOCK_SIZE = 30;
    private List<Tree> TreesArray;
    private Function<Float,Float> funcToGetY;

    public Flora(Function<Float,Float> funcToGetY) {
        this.funcToGetY = funcToGetY;
        TreesArray = new ArrayList<Tree>();
    }

    public List<Tree> createInRange(int minX, int maxX) {
        Random random = new Random();
        Double probability;
        int NumCols = (maxX - minX) / BLOCK_SIZE ;
        for (int colInd = 0; colInd < NumCols; colInd++) {
            probability = random.nextDouble();
            if (probability < 0.1) {
                float xValue = minX + BLOCK_SIZE * colInd;
                TreesArray.add(new Tree(xValue,funcToGetY.apply(xValue)));
            }
        }
        return TreesArray;
    }
}
