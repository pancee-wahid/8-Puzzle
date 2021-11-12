package main;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<String> path;
    private List<Move> moves;
    private int pathCost;
    private List<String> expandedNodes;
    private int searchDepth;
    private long runningTime;



    public Solution() {
//        this.path = new ArrayList<>();
//        this.expandedNodes = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.pathCost = 0;
        this.searchDepth = 0;
        this.runningTime = 0;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void incrementPathCost() {
        this.pathCost++;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
    public List<String> getExpandedNodes() {
        return expandedNodes;
    }

    public void setExpandedNodes(List<String> expandedNodes) {
        this.expandedNodes = expandedNodes;
    }

    public int getSearchDepth() {
        return searchDepth;
    }

    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }
}

