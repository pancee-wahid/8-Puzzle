package main;

import main.enums.Direction;

public class Move {
    private String state; // the state reached by this move
    private Direction direction; // the direction of moving the 0

    public Move(String state, Direction direction) {
        this.state = state;
        this.direction = direction;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
