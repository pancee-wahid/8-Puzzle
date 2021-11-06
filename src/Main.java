import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String initialState = generateRandomInitialState();

    }

    private String generateRandomInitialState() {
        List<Integer> randomState = new ArrayList<Integer>();
        String initialState = "";
        for (int i = 0; i < 9; i++)
            randomState.add(i);

        Collections.shuffle(randomState);

        for (int i = 0; i < 9; i++)
            initialState += randomState.get(i);

        return initialState;
    }

    private void BFS() {

    }

    private void DFS() {

    }

    private void AStarEuclidean() {

    }

    private void AStarManhattan() {

    }
}
