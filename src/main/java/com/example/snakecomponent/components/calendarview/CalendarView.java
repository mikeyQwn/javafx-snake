package com.example.snakecomponent.components.calendarview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CalendarView extends Pane {

    public CalendarView() {
        this.getChildren().add(this.getGridPane());
    }

    private int[][] getCalendarData() {
        final int CALENDAR_DATA_WIDTH = 7;
        final int CALENDAR_DATA_HEIGHT = 7;
        int[][] calendarData = new int[CALENDAR_DATA_HEIGHT][CALENDAR_DATA_WIDTH];

    }

    private GridPane getGridPane() {
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: black;" + "-fx-border-width: 20;" + "-fx-border-color: black");
        pane.setHgap(20);
        pane.setVgap(20);
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 6; ++j) {
                pane.add(new CalendarEntry(String.format("i: %d, j: %d", i, j)), i, j);
            }
        }
        return pane;
    }


}

class CalendarEntry extends Label {
    final String blackBackgroundStyle = "-fx-background-color: #f3f3f3; -fx-border-width: 2; -fx-border-radius: 5;";

    private int getRandomColorValue() {
        return (int) Math.floor(Math.random() * 255);
    }

    private String getRandomColor() {
        return String.format("rgb(%d, %d, %d)", this.getRandomColorValue(), this.getRandomColorValue(), this.getRandomColorValue());
    }

    public CalendarEntry(String labelText) {
        this.setMinSize(50, 50);
        this.setStyle("-fx-background-color: rgb(255, 255, 255);"
//                + "-fx-border-width: 2;"
//                + "-fx-border-color: black"
        );
        this.setText(labelText);
    }
}
