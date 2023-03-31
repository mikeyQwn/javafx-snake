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

    public void draw(CalendarEntry[][] calendarEntries);
}

class HeadSegment implements Segment {
    private Position position;

    public HeadSegment() {
        this.position = new Position(4, 4);
    }

    public void update() {
    }


    public void draw(CalendarEntry[][] calendarEntries) {
        CalendarEntry correspondingElement = calendarEntries[this.position.getY()][this.position.getX()];
        correspondingElement.setBackgroundColor("rgb(0, 255, 0)");
    }
}

class BodySegment implements Segment {
    private Position position;
    BodySegment(Position position) {
        this.position = position;
    }

    public void update() {
    }


    public void draw(CalendarEntry[][] calendarEntries) {
        CalendarEntry correspondingElement = calendarEntries[this.position.getY()][this.position.getX()];
        correspondingElement.setBackgroundColor("rgb(210,105,30)");

    }

}

class Snake {
    Direction direction;
    ArrayList<Segment> segments;

    public Snake() {
        this.direction = Direction.UP;
        this.segments = new ArrayList<>();
        this.segments.add(new HeadSegment());
        this.segments.add(new BodySegment(new Position(3, 3)));
    }

    public void draw(CalendarEntry[][] calendarEntries) {
        this.segments.forEach(segment -> segment.draw(calendarEntries));
    }
}

public class Game {
    CalendarEntry[][] calendarEntries;
    private Snake snake;
    public Game(CalendarEntry[][] calendarEntries) {
        this.calendarEntries = calendarEntries;
        this.snake = new Snake();
        this.snake.draw(calendarEntries);
    }
    public void start() {
        System.out.println("The game has started!");
    }
}
