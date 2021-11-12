package main;
import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    private String state;
    private Node parent;
    private List<Node> children; // list of all children of the node
    private List<Move> moves; // list of moves from root to current node
    private double cost; // the cost of the path from root to the node
    private double totalCost; // the sum of the estimated cost (using heuristic) and the cost
    private int depth;

    // create root node
    public Node(String state) {
        setState(state);
        children = new ArrayList<>();
        moves = new ArrayList<>();
    }

    // create internal(or leaf) node
    // add move to list of moves and set parent
    public Node(Node parent, Move move) {
        setState(move.getState());
        children = new ArrayList<>();
        setParent(parent, move);
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
        setMoves(parent.moves, move);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.totalCost, o.totalCost);
    }
}
