package main;

import main.enums.*;
import java.util.*;

public class PuzzleSolver {
    final private String goalState = "012345678";
    private String initialState = "";
    private Node root;

    /**
     * sets the initialState to the value of the parameter
     * @param initialState initial state entered
     */
    private void setInitialState(String initialState) {
        this.initialState = initialState;

        // create the root node with state = initialState
        root = new Node(initialState);
    }

    /**
     * generates a random initial state
     */
    private void setInitialState() {
        // generate list of numbers 0 to 8
        List<Integer> randomState = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            randomState.add(i);

        // shuffle the list
        Collections.shuffle(randomState);

        // set initialState to the string value of the list
        initialState = "";
        for (int i = 0; i < 9; i++)
            initialState += randomState.get(i);

        // create the root node with state = initialState
        root = new Node(initialState);
    }

    /**
     * runs the chosen algorithm to solve the puzzle
     * @param algorithmName the name of algorithm to apply
     * @return object of type Solution that contains the path, expandedNodes, cost, runningTime & searchDepth
     */
    public Solution runAlgorithm(AlgorithmName algorithmName) {
        return switch (algorithmName) {
            case BFS -> BFS();
            case DFS -> DFS();
            case AStarEuclidean -> AStar(Heuristic.EUCLIDEAN);
            case AStarManhattan -> AStar(Heuristic.MANHATTAN);
        };
    }

    // Todo -> to be tested
    /**
     * applying BFS Algorithm
     * @return object of type Solution that contains the path, expandedNodes, cost, runningTime & searchDepth
     */
    private Solution BFS() {
        Solution solution = new Solution();
        List<String> expandedNodes = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // BFS Algorithm
        int totalCost = 0;
        Node currentNode = root;
        String currentState;
        Queue<Node> frontier = new LinkedList<>();
        Set<String> explored = new HashSet<>();
        frontier.add(root);
        while (!frontier.isEmpty()) {
            currentNode = frontier.remove();
            currentState = currentNode.getState();
            explored.add(currentState);
            expandedNodes.add(currentState);
            if (currentState.equals(goalState)) {
                break;
            }
            List<String> successorStates = getSuccessors(currentState);
            for (String successorState : successorStates) {
                if (!explored.contains(successorState)) {
                    Node newNode = new Node(successorState, currentNode);
                    currentNode.addChild(newNode);
                    frontier.add(newNode);
                }
            }
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPath(tracePath(currentNode));
        solution.setPathCost(solution.getPath().size());
        solution.setSearchDepth(solution.getPath().size());
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

    /**
     * get the path by tracing the leaf (goal) to the root (initial)
     * @param currentNode the leaf node
     * @return list containing the nodes of the path
     */
    private List<String> tracePath(Node currentNode) {
        List<String> path = new ArrayList<>();
        int cost = 0;
        while (currentNode != null) {
            path.add(currentNode.getState());
            if (currentNode.getParent() != null)
                currentNode = currentNode.getParent();
            else
                break;
        }
        return path;
    }

    // Todo -> to be tested
    /**
     * get the successor states of the current state
     * @param currentState current state
     * @return list of successor states
     */
    private List<String> getSuccessors(String currentState) {
        List<String> successors = new ArrayList<>();
        int zeroPosition = currentState.indexOf("0");

        switch (zeroPosition) {
            case 0:
                successors.add(swapCharacters(currentState, 1));
                successors.add(swapCharacters(currentState, 3));
                break;
            case 1:
                successors.add(swapCharacters(currentState, 0));
                successors.add(swapCharacters(currentState, 2));
                successors.add(swapCharacters(currentState, 4));
                break;
            case 2:
                successors.add(swapCharacters(currentState, 1));
                successors.add(swapCharacters(currentState, 5));
                break;
            case 3:
                successors.add(swapCharacters(currentState, 0));
                successors.add(swapCharacters(currentState, 4));
                successors.add(swapCharacters(currentState, 6));
                break;
            case 4:
                successors.add(swapCharacters(currentState, 1));
                successors.add(swapCharacters(currentState, 3));
                successors.add(swapCharacters(currentState, 5));
                successors.add(swapCharacters(currentState, 7));
                break;
            case 5:
                successors.add(swapCharacters(currentState, 2));
                successors.add(swapCharacters(currentState, 4));
                successors.add(swapCharacters(currentState, 8));
                break;
            case 6:
                successors.add(swapCharacters(currentState, 3));
                successors.add(swapCharacters(currentState, 7));
                break;
            case 7:
                successors.add(swapCharacters(currentState, 4));
                successors.add(swapCharacters(currentState, 6));
                successors.add(swapCharacters(currentState, 8));
                break;
            case 8:
                successors.add(swapCharacters(currentState, 5));
                successors.add(swapCharacters(currentState, 7));
                break;
        }
        return successors;
    }

    private String swapCharacters(String state, int index) {
        state.replace('0', state.charAt(index));
        state.replace(state.charAt(index), '0');
        return state;
    }


    private Solution DFS() {
        Solution solution = new Solution();
        long startTime = System.currentTimeMillis();

        //Todo



        //solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        //Todo

        return solution;
    }

    private Solution AStar(Heuristic heuristic) {
        Solution solution = new Solution();
        //Todo

        /*
        if (heuristic.equals(Heuristic.EUCLIDEAN)) {
            //calculate using euclidean distance
        } else {
            //calculate using manhattan distance
        }
        */

        return solution;
    }

}
