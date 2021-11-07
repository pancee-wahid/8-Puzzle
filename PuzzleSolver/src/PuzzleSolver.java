import enums.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleSolver {
    final private String goalState = "012345678";
    private String initialState = "";


    public void setInitialState(InitialStateSetting choice) {
        if (choice.equals(InitialStateSetting.ENTER_STATE)) {

        }
    }

    public void runAlgorithm(AlgorithmName algorithmName) {
        generateRandomInitialState();
//        if (algorithmName.equals(enums.AlgorithmName.BFS))
//            BFS();
//        else if (algorithmName.equals(enums.AlgorithmName.DFS))
//            DFS();
//        else if (algorithmName.equals(enums.AlgorithmName.AStarEuclidean))
//            AStar();



    }

    private void generateRandomInitialState() {
        // generate list of numbers 0 to 8
        List<Integer> randomState = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++)
            randomState.add(i);

        // shuffle the list
        Collections.shuffle(randomState);

        // set initialState to the string value of the list
        initialState = "";
        for (int i = 0; i < 9; i++)
            initialState += randomState.get(i);
//        System.out.println(initialState);
    }

    private void BFS() {

    }

    private void DFS() {

    }

    private void AStar() {

    }

    private void AStarEuclidean() {

    }

    private void AStarManhattan() {

    }
}
