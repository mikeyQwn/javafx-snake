package com.example.snakecomponent.components.calendarview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public boolean isOpposite(Direction other) {
        switch (this) {
            case UP:
                return other == DOWN;
            case DOWN:
                return other == UP;
            case LEFT:
                return other == RIGHT;
            case RIGHT:
                return other == LEFT;
            default:
                return false;
        }
    }

    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return UP;
        }
    }
}

interface Segment {
    void draw(CalendarEntry[][] calendarEntries);

    Position getPosition();

    void setPosition(Position position);
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

    public void move(Direction direction) {
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
        }

    }

    public Position clone() {
        return new Position(this.x, this.y);
    }
}

class HeadSegment implements Segment {
    private Position position;

    public HeadSegment() {
        this.position = new Position(4, 4);
    }

    public void update(Direction direction) {
        this.position = this.position.clone();
        this.position.move(direction);
    }


    public void draw(CalendarEntry[][] calendarEntries) {
        CalendarEntry correspondingElement = calendarEntries[this.position.getY()][this.position.getX()];
        correspondingElement.setBackgroundColor("rgb(0, 255, 0)");
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isOutOfBounds(Position rightDownCorner) {
        return this.position.getX() < 0 || this.position.getX() > rightDownCorner.getX() || this.position.getY() < 0 || this.position.getY() > rightDownCorner.getY();
    }
}

class BodySegment implements Segment {
    private Position position;

    BodySegment(Position position) {
        this.position = position;
    }

    public void draw(CalendarEntry[][] calendarEntries) {
        CalendarEntry correspondingElement = calendarEntries[this.position.getY()][this.position.getX()];
        correspondingElement.setBackgroundColor("rgb(210,105,30)");

    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}

class Snake {
    private Direction direction;
    private ArrayList<Segment> segments;
    private HeadSegment head;

    public Snake() {
        this.direction = Direction.UP;
        this.segments = new ArrayList<>();
        HeadSegment head = new HeadSegment();
        this.head = head;
        this.segments.add(head);
        Position bodySegmentPosition = head.getPosition().clone();
        bodySegmentPosition.move(this.direction.getOpposite());
        this.segments.add(new BodySegment(bodySegmentPosition));
    }

    public void draw(CalendarEntry[][] calendarEntries) {
        Arrays.stream(calendarEntries).forEach(row -> Arrays.stream(row).forEach(item -> item.clearBackgroundStyle()));
        this.segments.forEach(segment -> segment.draw(calendarEntries));
    }

    public void update() {
        for (int i = 1; i < this.segments.size(); ++i) {
            this.segments.get(this.segments.size() - i).setPosition(this.segments.get(this.segments.size() - i - 1).getPosition());
        }
        this.head.update(this.direction);
    }

    public boolean isHeadOutOfBounds(Position bottomLeftCorner) {
        return this.head.isOutOfBounds(bottomLeftCorner);
    }
}

public class Game {
    private CalendarEntry[][] calendarEntries;
    private final Snake snake;
    private Position bottomRightCorner;
    private Timeline timeline;

    public Game(CalendarEntry[][] calendarEntries) {
        this.calendarEntries = calendarEntries;
        this.snake = new Snake();
        this.snake.update();
        this.snake.draw(calendarEntries);
        this.bottomRightCorner = new Position(calendarEntries[0].length, calendarEntries.length);
    }

    public void start() {
        System.out.println("The game has started!");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                snake.update();
                if (snake.isHeadOutOfBounds(bottomRightCorner)) {
                    endGame();
                    return;
                }
                System.out.println("Drawing");
                snake.draw(calendarEntries);
            }
        }));
        this.timeline = timeline;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void endGame() {
        System.out.println("The game has ended!");
        this.timeline.stop();
    }
}
