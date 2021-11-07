import enums.*;

import java.util.*;

public class PuzzleSolver {
    final private String goalState = "012345678";
    private String initialState = "";
    private Node root;

    /**
     * sets the initialState to the value of the parameter
     * @param initialState
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
        List<Integer> randomState = new ArrayList<Integer>();
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
     * @param algorithmName
     * @return object of type Solution that contains the path, expandedNodes, cost, runningTime & searchDepth
     */
    public Solution runAlgorithm(AlgorithmName algorithmName) {
        switch (algorithmName) {
            case BFS:
                return BFS();
            case DFS:
                return DFS();
            case AStarEuclidean:
                return AStarEuclidean();
            case AStarManhattan:
                return AStarManhattan();
            default:
                return null;
        }
    }

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
            for (int i = 0; i < successorStates.size(); i++) {
                if (!explored.contains(successorStates.get(i))) {
                    Node newNode = new Node(successorStates.get(i), currentNode);
                    currentNode.addChild(newNode);
                    frontier.add(newNode);
                }
            }
            frontier.poll();
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPath(tracePath(currentNode));
        solution.setPathCost(solution.getPath().size());
        solution.setSearchDepth(solution.getPath().size());
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

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

    private List<String> getSuccessors(String currentNode) {
        List<String> successors = new ArrayList<>();
        //Todo
        //code of getting successors

        return successors;
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

//    private Solution AStar()  {
//
//    }

    private Solution AStarEuclidean() {
        Solution solution = new Solution();
        //Todo


        return solution;
    }

    private Solution AStarManhattan() {
        Solution solution = new Solution();
        //Todo


        return solution;
    }
}
