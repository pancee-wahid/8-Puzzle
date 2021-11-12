package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node implements Comparable<Node>{
    private String state;
    private Node parent;
    private List<Node> children;
//    private List<String> pathFromRoot;
    private List<Move> moves;
    private double cost;
    private double estimatedCostToGoal; // A*
    private double totalCost; // A*
    private int depth;

    // create root node
    public Node(String state) {
        setState(state);
        children = new ArrayList<>();
//        pathFromRoot = new ArrayList<>();
//        pathFromRoot.add(state);
        moves = new ArrayList<>();
    }

    // create non-root node
    public Node(Node parent, Move move) {
        setState(move.getState());
        children = new ArrayList<>();
//        pathFromRoot = new ArrayList<>();
        setParent(parent, move);
    }

//    public void setPathFromRoot(List<String> pathFromRoot) {
//        this.pathFromRoot.addAll(pathFromRoot);
//        this.pathFromRoot.add(state);
//    }

    public void setMoves(List<Move> moves, Move nextMove) {
        this.moves = new ArrayList<>();
        if (moves != null && !moves.isEmpty())
            this.moves.addAll(moves);
        this.moves.add(nextMove);
        depth = this.moves.size();
        cost = depth;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent, Move move) {
        this.parent = parent;
//        setPathFromRoot(parent.getPathFromRoot());
        setMoves(parent.moves, move);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getEstimatedCostToGoal() {
        return estimatedCostToGoal;
    }

    public void setEstimatedCostToGoal(double estimatedCostToGoal) {
        this.estimatedCostToGoal = estimatedCostToGoal;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

//    public List<String> getPathFromRoot() {
//        return pathFromRoot;
//    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.totalCost, o.totalCost);
    }
}
