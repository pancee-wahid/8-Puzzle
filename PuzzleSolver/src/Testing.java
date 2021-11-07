import enums.*;

public class Testing {
    public static void main(String[] args) {
        PuzzleSolver solver = new PuzzleSolver();
        /*
        sequence for now:
            call setInitialState() in case of generating a random one
            call setInitialState(String initialState) in case of passing one as parameter
            runAlgorithm -> to run the selected algorithm
                takes AlgorithmName value as a parameter
         */

        solver.runAlgorithm(AlgorithmName.BFS);
    }
}
