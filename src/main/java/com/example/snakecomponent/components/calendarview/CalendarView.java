package com.example.snakecomponent.components.calendarview;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

public class CalendarView extends GridPane {
    final int CALENDAR_ENTRY_WIDTH = 7;
    final int CALENDAR_ENTRY_HEIGHT = 6;
    private Game game;
    private CalendarEntry[][] calendarEntries;
    public CalendarView() {
        this.setPadding(new Insets(5, 0, 5, 0));
        this.setVgap(4);
        this.setHgap(4);
        this.calendarEntries = new CalendarEntry[CALENDAR_ENTRY_HEIGHT][CALENDAR_ENTRY_WIDTH];
        Label titleLabel = this.createTitleLabel();
        GridPane gridPane = this.getGridPane();
        Label extraLabel = this.createExtraLabel();
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println("Hey");
            }
        });
        this.requestFocus();
        this.add(titleLabel, 0, 1);
        this.add(gridPane, 0, 2);
        this.add(extraLabel, 0, 3);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addSpacePressHandler();
            }
        }, 1000);
    }

    public void startGame() {
        this.game = new Game(this.calendarEntries);
        this.game.start();
    }

    private void addSpacePressHandler() {
        getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() != KeyCode.SPACE) return;
                startGame();
            }
        });
    }

    private Label createTitleLabel() {
        Label label = new Label("Snake calendar");
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Float.POSITIVE_INFINITY);
        return label;
    }

    private Label createExtraLabel () {
        Label label = new Label("Press [space] to play");
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Float.POSITIVE_INFINITY);
        return label;
    }

    private GridPane getGridPane() {
        CalendarData data = new CalendarData();
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: black;" + "-fx-border-width: 20;" + "-fx-border-color: black");
        pane.setHgap(20);
        pane.setVgap(20);
        for (int i = 0; i < this.CALENDAR_ENTRY_WIDTH; ++i) {
            for (int j = 0; j < this.CALENDAR_ENTRY_HEIGHT; ++j) {
                CalendarEntry entry = new CalendarEntry(data.getDateString(i, j));
                this.calendarEntries[j][i] = entry;
                pane.add(entry, i, j);
            }
        }
        return pane;
    }


}


class CalendarEntry extends Label {

    final String styleProperties = "";
    private String colorProperty;

    private int getRandomColorValue() {
        return (int) Math.floor(Math.random() * 255);
    }

    private String getRandomColor() {
        return String.format("rgb(%d, %d, %d)", this.getRandomColorValue(), this.getRandomColorValue(), this.getRandomColorValue());
    }

    public CalendarEntry(String labelText) {
        this.setMinSize(50, 50);
        this.setAlignment(Pos.CENTER);
        this.colorProperty = "-fx-background-color: rgb(255, 255, 255);";
        this.style();
        this.setText(labelText);
    }

    public void setBackgroundColor(String colorProperty) {
        this.colorProperty = "-fx-background-color: " + colorProperty;
        this.style();
    }
    private void style() {
        this.setStyle(this.styleProperties + this.colorProperty);
    }
}
class CalendarData {
    String[] labels;
    int[][] dateNumbers;
    final int DATE_NUMBERS_WIDTH = 7;
    final int DATE_NUMBERS_HEIGHT = 6;

    public CalendarData() {
        this.labels = generateLabels();
        this.dateNumbers = generateDateNumbers();
    }

    public String getLabel(int index) { return this.labels[index]; }

    public int getDateNumber(int i, int j) { return this.dateNumbers[j][i]; }

    public String getDateString(int i, int j) { return this.dateNumbers[j][i] == 0 ? "" : Integer.toString(this.dateNumbers[j][i]);}

    private String[] generateLabels() {
        return new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    }

    private int[][] generateDateNumbers() {

        int[][] calendarData =
                new int[this.DATE_NUMBERS_HEIGHT][this.DATE_NUMBERS_WIDTH];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int max_number = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeekNumber = cal.get(Calendar.DAY_OF_WEEK);
        int dayOfWeekIndex = dayOfWeekNumber == 1 ? 0 : dayOfWeekNumber - 2;
        System.out.println(dayOfWeekIndex);
        for (int i = 0; i < this.DATE_NUMBERS_WIDTH; ++i) {
            for (int j = 0; j < this.DATE_NUMBERS_HEIGHT; ++j) {
                int number =
                        i + j * this.DATE_NUMBERS_WIDTH - dayOfWeekIndex + 1;
                if (number < 0 || number > max_number)
                    continue;

                calendarData[j][i] = number;
            }
        }

        return calendarData;
    }

    public void print() {
        System.out.println(Arrays.toString(this.labels));
        Arrays.stream(dateNumbers)
                .forEach(row -> System.out.println(Arrays.toString(row)));
    }
}
