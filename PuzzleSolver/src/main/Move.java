package main;

import main.enums.Direction;

public class Move {
    private String nextState;
    private Direction direction;

    public Move(String nextState, Direction direction) {
        this.nextState = nextState;
        this.direction = direction;
    }

}
