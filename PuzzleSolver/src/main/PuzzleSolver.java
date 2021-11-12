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
    public void setInitialState(String initialState) {
        this.initialState = initialState;

        // create the root node with state = initialState
        root = new Node(initialState);
    }

    /**
     * generates a random initial state
     */
    public void setInitialState() {
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
            currentNode = frontier.poll();
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
        solution.setPath(currentNode.getPathFromRoot());
        solution.setPathCost(solution.getPath().size() - 1);
        solution.setSearchDepth(solution.getPath().size() - 1);
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

    // Todo -> Make sure we don't want it  then delete
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
                successors.add(swapCharacters(currentState, 1)); //R
                successors.add(swapCharacters(currentState, 3)); //D
                break;
            case 1:
                successors.add(swapCharacters(currentState, 0)); //L
                successors.add(swapCharacters(currentState, 2)); //R
                successors.add(swapCharacters(currentState, 4)); //D
                break;
            case 2:
                successors.add(swapCharacters(currentState, 1)); //L
                successors.add(swapCharacters(currentState, 5)); //D
                break;
            case 3:
                successors.add(swapCharacters(currentState, 4)); //R
                successors.add(swapCharacters(currentState, 0)); //U
                successors.add(swapCharacters(currentState, 6)); //D
                break;
            case 4:
                successors.add(swapCharacters(currentState, 3)); //L
                successors.add(swapCharacters(currentState, 5)); //R
                successors.add(swapCharacters(currentState, 1)); //U
                successors.add(swapCharacters(currentState, 7)); //D
                break;
            case 5:
                successors.add(swapCharacters(currentState, 4)); //L
                successors.add(swapCharacters(currentState, 2)); //U
                successors.add(swapCharacters(currentState, 8)); //D
                break;
            case 6:
                successors.add(swapCharacters(currentState, 7)); //R
                successors.add(swapCharacters(currentState, 3)); //U
                break;
            case 7:
                successors.add(swapCharacters(currentState, 6)); //L
                successors.add(swapCharacters(currentState, 8)); //R
                successors.add(swapCharacters(currentState, 4)); //U
                break;
            case 8:
                successors.add(swapCharacters(currentState, 7)); //L
                successors.add(swapCharacters(currentState, 5)); //U
                break;
        }
        return successors;
    }

    private String swapCharacters(String state, int index) {
        int indexOfZero = state.indexOf('0');
        String updated = state.substring(0, indexOfZero) + state.charAt(index) + state.substring(indexOfZero + 1);
        updated = updated.substring(0, index) + "0" + updated.substring(index + 1);
        return updated;
    }


    private Solution DFS() {
        Solution solution = new Solution();
        List<String> expandedNodes = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // BFS Algorithm
        int totalCost = 0;
        Node currentNode = root;
        String currentState;
        Stack<Node> frontier = new Stack<>();
        Set<String> explored = new HashSet<>();
        int searchDepth = -1;

        frontier.push(root);
        while (!frontier.isEmpty()) {
            currentNode = frontier.pop();
            currentState = currentNode.getState();
            if (searchDepth < currentNode.getDepth())
                searchDepth = currentNode.getDepth();
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
                    frontier.push(newNode);
                }
            }
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPath(currentNode.getPathFromRoot());
        solution.setPathCost(solution.getPath().size() - 1);
        solution.setSearchDepth(searchDepth);
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

    private Solution AStar(Heuristic heuristic) {
        Solution solution = new Solution();
        List<String> expandedNodes = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // A* Algorithm
        int totalCost = 0;
        Node currentNode = root;
        String currentState;
        int searchDepth = -1;

        PriorityQueue<Node> frontier = new PriorityQueue<>();
        Set<String> explored = new HashSet<>();
        frontier.add(root);
        while (!frontier.isEmpty()) {
            currentNode = frontier.poll();
            if (searchDepth < currentNode.getDepth())
                searchDepth = currentNode.getDepth();
            currentState = currentNode.getState();
            explored.add(currentState);
            expandedNodes.add(currentState);
            if (currentState.equals(goalState)) {
                break;
            }
            List<String> successorStates = getSuccessors(currentState);
            for (String successorState : successorStates) {
                double h;
                if (heuristic == Heuristic.EUCLIDEAN) {
                    h = euclideanDistance(successorState,goalState);
                } else {
                    h = manhattanDistance(successorState,goalState);
                }
                double g = currentNode.getCost() + 1;
                double f = g + h;
                if (!explored.contains(successorState)) {
                    Node newNode = new Node(successorState, currentNode);
                    newNode.setTotalCost(f);
                    newNode.setCost(g);
                    newNode.setEstimatedCostToGoal(h);
                    currentNode.addChild(newNode);
                    frontier.add(newNode);
                } else {
                    Iterator i = frontier.iterator();
                    while (i.hasNext()) {
                        if (successorState == ((Node) i.next()).getState()) {
                            if (f < ((Node) i.next()).getTotalCost()) {
                                ((Node) i.next()).setTotalCost(f);
                                ((Node) i.next()).setCost(g);
                                ((Node) i.next()).setEstimatedCostToGoal(h);
                                ((Node) i.next()).setParent(currentNode);
                            }
                            break;
                        }
                    }
                }
            }
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPath(currentNode.getPathFromRoot());
        solution.setPathCost(solution.getPath().size() - 1);
        solution.setSearchDepth(searchDepth);
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }
    private double euclideanDistance(String state, String goalState){
        double d=0;
        for (int i = 0; i < state.length(); i += 1)
            for (int j = 0; j < goalState.length(); j += 1)
                if (state.charAt(i) == goalState.charAt(j)){
                    int x1=i%3;int x2=j%3;int y1=i/3;int y2=j/3;
                    d = d + Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
                }
        return d;
    }
    private double manhattanDistance(String state, String goalState){
        double d=0;
        for (int i = 0; i < state.length(); i += 1)
            for (int j = 0; j < goalState.length(); j += 1)
                if (state.charAt(i) == goalState.charAt(j)) {
                    int x1=i%3;int x2=j%3;int y1=i/3;int y2=j/3;
                    d = d + ((Math.abs(x1 - x2)) + Math.abs(y1 - y2));
                }
        return d;
    }

}
