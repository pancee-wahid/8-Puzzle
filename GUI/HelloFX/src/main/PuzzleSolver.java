package main;

import main.enums.*;
import java.util.*;

public class PuzzleSolver {
    final private String goalState = "012345678"; // the given goal state
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
            if (currentState.equals(goalState)) {
                break;
            }
            explored.add(currentState);
            expandedNodes.add(currentState);
            List<Move> successorStates = getSuccessors(currentState);
            for (Move move : successorStates) {
                if (!explored.contains(move.getState())) {
                    Node newNode = new Node(currentNode, move);
                    currentNode.addChild(newNode);
                    frontier.add(newNode);
                }
            }
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPathCost(currentNode.getMoves().size());
        solution.setMoves(currentNode.getMoves());
        solution.setSearchDepth(solution.getPathCost());
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

    /**
     * applying DFS Algorithm
     * @return object of type Solution that contains the path, expandedNodes, cost, runningTime & searchDepth
     */
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
            if (currentState.equals(goalState))
                break;
            if (searchDepth < currentNode.getDepth())
                searchDepth = currentNode.getDepth();
            explored.add(currentState);
            expandedNodes.add(currentState);
            List<Move> successorStates = getSuccessors(currentState);
            for (Move move : successorStates) {
                if (!explored.contains(move.getState())) {
                    Node newNode = new Node(currentNode, move);
                    currentNode.addChild(newNode);
                    frontier.push(newNode);
                }
            }
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPathCost(currentNode.getMoves().size());
        solution.setMoves(currentNode.getMoves());
        solution.setSearchDepth(solution.getPathCost());
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

    /**
     * applying A* Algorithm using the given heuristic
     * @return object of type Solution that contains the path, expandedNodes, cost, runningTime & searchDepth
     */
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
            List<Move> successorStates = getSuccessors(currentState);
            for (Move move : successorStates) {
                double h;
                if (heuristic == Heuristic.EUCLIDEAN) {
                    h = euclideanDistance(move.getState(),goalState);
                } else {
                    h = manhattanDistance(move.getState(),goalState);
                }
                double g = currentNode.getCost() + 1;
                double f = g + h;
                if (!explored.contains(move.getState())) {
                    Node newNode = new Node(currentNode, move);
                    newNode.setTotalCost(f);
                    newNode.setCost(g);
                    currentNode.addChild(newNode);
                    frontier.add(newNode);
                } else {
                    Iterator i = frontier.iterator();
                    while (i.hasNext()) {
                        if (move.getState() == ((Node) i.next()).getState()) {
                            if (f < ((Node) i.next()).getTotalCost()) {
                                ((Node) i.next()).setTotalCost(f);
                                ((Node) i.next()).setCost(g);
                                ((Node) i.next()).setParent(currentNode, move);
                            }
                            break;
                        }
                    }
                }
            }
        }

        // solution preparation
        solution.setRunningTime(System.currentTimeMillis() - startTime); // in milliseconds
        solution.setPathCost(currentNode.getMoves().size());
        solution.setMoves(currentNode.getMoves());
        solution.setSearchDepth(searchDepth);
        solution.setExpandedNodes(expandedNodes);
        return solution;
    }

    private double euclideanDistance(String state, String goalState) {
        double d = 0;
        for (int i = 0; i < state.length(); i += 1) {
            for (int j = 0; j < goalState.length(); j += 1) {
                if (state.charAt(i) == goalState.charAt(j)) {
                    int x1 = i % 3;
                    int x2 = j % 3;
                    int y1 = i / 3;
                    int y2 = j / 3;
                    d = d + Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                }
            }
        }
        return d;
    }

    private double manhattanDistance(String state, String goalState){
        double d = 0;
        for (int i = 0; i < state.length(); i += 1) {
            for (int j = 0; j < goalState.length(); j += 1) {
                if (state.charAt(i) == goalState.charAt(j)) {
                    int x1 = i % 3;
                    int x2 = j % 3;
                    int y1 = i / 3;
                    int y2 = j / 3;
                    d = d + ((Math.abs(x1 - x2)) + Math.abs(y1 - y2));
                }
            }
        }
        return d;
    }

    /**
     * get the successor states of the current state
     * @param currentState current state
     * @return list of successor states
     */
    private List<Move> getSuccessors(String currentState) {
        List<Move> successors = new ArrayList<>();
        int zeroPosition = currentState.indexOf("0");

        switch (zeroPosition) {
            case 0:
                successors.add(new Move(swapCharacters(currentState, 1), Direction.RIGHT));
                successors.add(new Move(swapCharacters(currentState, 3), Direction.DOWN));
                break;
            case 1:
                successors.add(new Move(swapCharacters(currentState, 0), Direction.LEFT));
                successors.add(new Move(swapCharacters(currentState, 2), Direction.RIGHT));
                successors.add(new Move(swapCharacters(currentState, 4), Direction.DOWN));
                break;
            case 2:
                successors.add(new Move(swapCharacters(currentState, 1), Direction.LEFT));
                successors.add(new Move(swapCharacters(currentState, 5), Direction.DOWN));

                break;
            case 3:
                successors.add(new Move(swapCharacters(currentState, 4), Direction.RIGHT));
                successors.add(new Move(swapCharacters(currentState, 0), Direction.UP));
                successors.add(new Move(swapCharacters(currentState, 6), Direction.DOWN));
                break;
            case 4:
                successors.add(new Move(swapCharacters(currentState, 3), Direction.LEFT));
                successors.add(new Move(swapCharacters(currentState, 5), Direction.RIGHT));
                successors.add(new Move(swapCharacters(currentState, 1), Direction.UP));
                successors.add(new Move(swapCharacters(currentState, 7), Direction.DOWN));
                break;
            case 5:
                successors.add(new Move(swapCharacters(currentState, 4), Direction.LEFT));
                successors.add(new Move(swapCharacters(currentState, 2), Direction.UP));
                successors.add(new Move(swapCharacters(currentState, 8), Direction.DOWN));
                break;
            case 6:
                successors.add(new Move(swapCharacters(currentState, 7), Direction.RIGHT));
                successors.add(new Move(swapCharacters(currentState, 3), Direction.UP));
                break;
            case 7:
                successors.add(new Move(swapCharacters(currentState, 6), Direction.LEFT));
                successors.add(new Move(swapCharacters(currentState, 8), Direction.RIGHT));
                successors.add(new Move(swapCharacters(currentState, 4), Direction.UP));
                break;
            case 8:
                successors.add(new Move(swapCharacters(currentState, 7), Direction.LEFT));
                successors.add(new Move(swapCharacters(currentState, 5), Direction.UP));
                break;
        }
        return successors;
    }

    /**
     * swaps character at specified index with zero in the given state
     * @param state state to be edited
     * @param index index of character to be swapped with zero
     * @return the new state after swapping
     */
    private String swapCharacters(String state, int index) {
        int indexOfZero = state.indexOf('0');
        String updated = state.substring(0, indexOfZero) + state.charAt(index) + state.substring(indexOfZero + 1);
        updated = updated.substring(0, index) + "0" + updated.substring(index + 1);
        return updated;
    }

    // Todo -> Make sure we don't want it then delete
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
}
