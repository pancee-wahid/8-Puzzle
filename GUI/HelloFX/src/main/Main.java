package main;

import main.enums.AlgorithmName;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PuzzleSolver p = new PuzzleSolver();

        // set initial state
        p.setInitialState("125340678");
        // or
        // generate random state
//        p.setInitialState();


        // Uncomment one to test
//        Solution s = p.runAlgorithm(AlgorithmName.BFS);
//        Solution s = p.runAlgorithm(AlgorithmName.DFS);
        Solution s = p.runAlgorithm(AlgorithmName.AStarEuclidean);
//        Solution s = p.runAlgorithm(AlgorithmName.AStarManhattan);

        System.out.println("Moves:");
        for (Move move : s.getMoves()) {
            System.out.println("Next state: " + move.getState() + "\tdirection: " + move.getDirection());
        }
        System.out.println("-----------------------------------------");
        System.out.println("Path Cost: " + s.getPathCost());
        System.out.println("Expanded nodes: " + s.getExpandedNodes().size());
        System.out.println("Search Depth: " + s.getSearchDepth());
        System.out.println("Running Time: " + s.getRunningTime() + " ms");

    }
}
