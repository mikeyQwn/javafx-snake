package com.example.snakecomponent.components.calendarview;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.Calendar;

public class CalendarView extends Pane {

    public CalendarView() {
        this.getChildren().add(this.getGridPane());
    }



    private GridPane getGridPane() {
        CalendarData data = new CalendarData();
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: black;" + "-fx-border-width: 20;" + "-fx-border-color: black");
        pane.setHgap(20);
        pane.setVgap(20);
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 6; ++j) {
                pane.add(new CalendarEntry(data.getDateString(i, j)), i, j);
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
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: rgb(255, 255, 255);"
//                + "-fx-border-width: 2;"
//                + "-fx-border-color: black"
        );
        this.setText(labelText);
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
