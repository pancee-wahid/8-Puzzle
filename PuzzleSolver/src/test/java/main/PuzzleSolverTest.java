package main;

import main.enums.AlgorithmName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleSolverTest {

    @Test
    void BFS_test_1() {
        PuzzleSolver p = new PuzzleSolver();
        List<String> correctPath = new ArrayList<>(Arrays.asList());
        int noExpandedNodes = 16;
        int pathCost = 3;
        p.setInitialState("125340678");
        Solution s = p.runAlgorithm(AlgorithmName.DFS);
        assertEquals(correctPath, s.getPath());
        assertEquals(noExpandedNodes, s.getExpandedNodes().size());
        assertEquals(pathCost, s.getPathCost());
        System.out.println(s.getRunningTime());
    }


}