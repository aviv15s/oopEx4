package world.daynight.trees;

import java.util.List;
import java.util.Random;

import pepse

public class Flora {
    private List<Tree> TreesArray;

    public Flora() {
    }

    public List<Tree> createInRange(int minX, int maxX) {
        Random random = new Random();
        Double probability;
        int NumCols = (int) (maxX - minX) / BLOCK_SIZE;
        for (int colInd = 0; colInd < NumCols; colInd++) {
            probability = random.nextDouble();
            if (probability < 0.1) {
                TreesArray.add(new Tree(minX + BLOCK_SIZE * colInd,));
            }
        }


    }
}
