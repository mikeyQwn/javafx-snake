package com.example.snakecomponent.components.calendarview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.skin.TextInputControlSkin;
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

    public Direction getOpposite() {
        switch (this) {
            case UP: return this.DOWN;
            case DOWN: return this.UP;
            case LEFT: return this.RIGHT;
            case RIGHT: return this.LEFT;
            default: return this.UP;
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

    public Position clone() {
        return new Position(this.x, this.y);
    }
}

interface Segment {
    public void update(Direction direction);

    public void draw(CalendarEntry[][] calendarEntries);

    public Position getPosition();

    public void setPosition(Position position);
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
}

class BodySegment implements Segment {
    private Position position;
    BodySegment(Position position) {
        this.position = position;
    }

    public void update(Direction direction) {
        this.position.move(direction);
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
    Direction direction;
    ArrayList<Segment> segments;

    public Snake() {
        this.direction = Direction.UP;
        this.segments = new ArrayList<>();
        Segment head = new HeadSegment();
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
        this.segments.get(0).update(this.direction);
    }
}

public class Game {
    CalendarEntry[][] calendarEntries;
    private Snake snake;

    public Game(CalendarEntry[][] calendarEntries) {
        this.calendarEntries = calendarEntries;
        this.snake = new Snake();
        this.snake.update();
        this.snake.draw(calendarEntries);
    }
    public void start() {
        System.out.println("The game has started!");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                snake.update();
                snake.draw(calendarEntries);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
