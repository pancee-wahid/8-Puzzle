package main;

import main.enums.Direction;

public class Move {
    private String state;
    private Direction direction;

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
