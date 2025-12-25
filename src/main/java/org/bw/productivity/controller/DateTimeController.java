package org.bw.productivity.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import static org.bw.productivity.util.Number.formatNumber;

public class DateTimeController {

    @FXML
    private Label timeLabel;

    @FXML
    private Label hourOfDayLabel;

    @FXML
    private Label minuteOfHourLabel;

    @FXML
    private Label secondOfMinuteLabel;

    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private Label monthOfYearLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private VBox dateContainer;

    public void initialize() {
        Calendar calendar = Calendar.getInstance();
        LocalDateTime localTime = LocalDateTime.now();
        int day = calendar.get(Calendar.DATE);
        String month = localTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        int year = calendar.get(Calendar.YEAR);

        updateTime(calendar);

        Timeline clock = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> updateTime(Calendar.getInstance())),
            new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.playFromStart();

        Rectangle rectangle = new Rectangle();
        rectangle.widthProperty().bind(dateContainer.widthProperty());
        rectangle.heightProperty().bind(dateContainer.heightProperty());
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        dateContainer.setClip(rectangle);

        Rectangle timeClip = new Rectangle();
        timeClip.widthProperty().bind(timeLabel.widthProperty());
        timeClip.heightProperty().bind(timeLabel.heightProperty());
        timeClip.setArcWidth(10);
        timeClip.setArcHeight(10);
        timeLabel.setClip(timeClip);

        dayOfMonthLabel.setText(formatNumber(day));
        monthOfYearLabel.setText(month);
    }

    private void updateTime(Calendar timeInstance) {
        String hour = formatNumber(timeInstance.get(Calendar.HOUR_OF_DAY));
        String minute = formatNumber(timeInstance.get(Calendar.MINUTE));
        String second = formatNumber(timeInstance.get(Calendar.SECOND));

        timeLabel.setText(hour + ":" + minute + ":" + second);
    }
}