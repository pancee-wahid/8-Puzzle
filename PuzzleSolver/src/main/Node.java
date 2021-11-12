package main;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private boolean visited;
    private String state;
    private Node parent;
    private List<Node> children;
    private List<String> pathFromRoot;
    private int cost;
    private int estimatedCostToGoal; // A*
    private int totalCost; // A*
    private int depth;

    // create root node
    public Node(String state) {
        setState(state);
        children = new ArrayList<>();
        pathFromRoot = new ArrayList<>();
        pathFromRoot.add(state);
    }

    // create non-root node
    public Node(String state, Node parent) {
        setState(state);
        children = new ArrayList<>();
        setParent(parent);
        setPathFromRoot(parent.pathFromRoot);
    }

    public void setPathFromRoot(List<String> pathFromRoot) {
        this.pathFromRoot = pathFromRoot;
        this.pathFromRoot.add(state);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
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

    public void setParent(Node parent) {
        this.parent = parent;
        setPathFromRoot(parent.pathFromRoot);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getEstimatedCostToGoal() {
        return estimatedCostToGoal;
    }

    public void setEstimatedCostToGoal(int estimatedCostToGoal) {
        this.estimatedCostToGoal = estimatedCostToGoal;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalCost(int cost, int estimatedCostToGoal) {
        this.totalCost = cost + estimatedCostToGoal;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<String> getPathFromRoot() {
        return pathFromRoot;
    }


}
