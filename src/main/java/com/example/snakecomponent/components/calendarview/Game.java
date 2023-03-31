package com.example.snakecomponent.components.calendarview;

import java.util.ArrayList;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public boolean isOpposite(Direction other) {
        switch (this) {
            case UP:
                return other == this.DOWN;
            case DOWN:
                return other == this.UP;
            case LEFT:
                return other == this.RIGHT;
            case RIGHT:
                return other == this.LEFT;
            default:
                return false;
        }
    }
}

class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move (Direction direction) {
        switch (direction) {
            case UP:
                this.y -= 1;
                return;
            case DOWN:
                this.y += 1;
                return;
            case LEFT:
                this.x -= 1;
                return;
            case RIGHT:
                this.x += 1;
                return;
        }

    }
}

interface Segment {
    public void update();

    public void draw();
}

class HeadSegment implements Segment {
    private Position position;

    public HeadSegment() {
        this.position = new Position(4, 4);
    }

    public void update() {
    }


    public void draw() {
    }
}

class BodySegment implements Segment {
    private Position position;
    BodySegment(Position position) {
        this.position = position;
    }

    public void update() {
    }


    public void draw() {
    }

}

class Snake {
    Direction direction;
    ArrayList<Segment> segments;

    public Snake() {
        this.direction = Direction.UP;
        this.segments = new ArrayList<>();
        this.segments.add(new HeadSegment());
    }
}

public class Game {
    public Game() {
    }
    public void start() {

    }
}
