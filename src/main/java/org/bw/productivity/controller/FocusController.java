package org.bw.productivity.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.bw.productivity.util.ConfigManager;
import org.bw.productivity.util.NavigationManager;
import org.bw.productivity.util.ViewLoader;

import java.io.IOException;

import static org.bw.productivity.util.Number.formatNumber;

public class FocusController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Button startTimerButton;

    @FXML
    private Button stopTimerButton;

    @FXML
    private Button resetTimerButton;

    @FXML
    private Button switchFocusBreakButton;

    @FXML
    private Label currentRoundLabel;

    @FXML
    private Button toggleMuteButton;

    private boolean showMillisecondLabel;

    private ConfigManager configManager;

    private boolean isMuted;

    private MediaPlayer sound;

    private Long totalMilliseconds;

    private Timeline timeline;

    private int currentRound;

    public void initialize() {
        configManager = new ConfigManager();
        configManager.load();

        currentRound = 1;

        timerLabel.setText(formatNumber(Integer.parseInt(configManager.getProperty("default.timer.period.minutes"))) + ":00");
        currentRoundLabel.setText(String.format("Round %d/%s", currentRound, configManager.getProperty("default.timer.round.count")));

        totalMilliseconds = ((long) (Integer.parseInt(configManager.getProperty("default.timer.period.minutes"))) * 60 * 1000);

        isMuted = false;
        sound = new MediaPlayer(new Media(String.valueOf(getClass().getResource("/org/bw/productivity/sounds/" + configManager.getProperty("sound.file.path")))));
    }

    @FXML
    private void switchToCalendar() throws IOException {
        NavigationManager.goTo("/org/bw/productivity/views/calendar-view.fxml");
    }

    @FXML
    private void openSettings() throws IOException {
        NavigationManager.goTo("/org/bw/productivity/views/settings-view.fxml");
    }

    @FXML
    private void startTimer() {
        this.timeline = new Timeline(
            new KeyFrame(Duration.millis(100), e -> {
                totalMilliseconds -= 100;
                if (totalMilliseconds <= 0) {
                    totalMilliseconds = 0L;

                    timerLabel.setText("Done");
                    timeline.stop();

                    sound.setCycleCount(AudioClip.INDEFINITE);
                    sound.play();
                    startTimerButton.disarm();
                } else {
                    updateTimer(totalMilliseconds);
                }
            })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void stopTimer() {
        timeline.stop();
        sound.stop();

        if (totalMilliseconds == 0) {
            startBreakTimer();
        }
    }

    @FXML
    private void startBreakTimer() {
        switchFocusBreak();
        timeline.stop();
        sound.stop();
    }

    @FXML
    private void toggleMute() {
        toggleMuteButton.getStyleClass().removeAll("muted", "unmuted");

        if (isMuted) {
            toggleMuteButton.getStyleClass().add("unmuted");
            isMuted = false;
            sound.setMute(false);
        } else {
            toggleMuteButton.getStyleClass().add("muted");
            isMuted = true;
            sound.setMute(true);
        }
    }

    @FXML
    private void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        sound.stop();
        titleLabel.setText("Focus");
        currentRound = 1;
        currentRoundLabel.setText(String.format("Round %d/%s", currentRound, configManager.getProperty("default.timer.round.count")));

        long milliseconds = (long) (Integer.parseInt(configManager.getProperty("default.timer.period.minutes"))) * 60000;
        totalMilliseconds = milliseconds;
        updateTimer(milliseconds);
    }

    @FXML
    private void switchFocusBreak() {
        if (titleLabel.getText().equals("Focus")) {
            titleLabel.setText("Break");
            switchFocusBreakButton.setText("Focus");

            long breakMinutesInMilliseconds = (long) Integer.parseInt(configManager.getProperty("default.timer.period.break")) * 60000;
            totalMilliseconds = breakMinutesInMilliseconds;
            updateTimer(breakMinutesInMilliseconds);
        } else {
            currentRound = currentRound + 1 > Integer.parseInt(configManager.getProperty("default.timer.round.count")) ? 1 : currentRound + 1;

            currentRoundLabel.setText(String.format("Round %d/%s", currentRound, configManager.getProperty("default.timer.round.count")));
            titleLabel.setText("Focus");
            switchFocusBreakButton.setText("Break");

            long milliseconds = (long) (Integer.parseInt(configManager.getProperty("default.timer.period.minutes"))) * 60000;

            totalMilliseconds = milliseconds;
            updateTimer(milliseconds);
        }
    }

    private void updateTimer(long millisecond) {
        int totalMinutes = ((int) Math.floor((double) millisecond %  3600000)) / 60000;
        int totalSeconds = ((int) Math.floor((double) millisecond % 60000) / 1000);
        long remainingMilliseconds = millisecond % 1000;

        if (totalSeconds < 0 && configManager.getProperty("show.milliseconds").equals("false")) {
            timerLabel.setText(String.format("%ss", formatNumber(totalSeconds)));
        } else if (totalSeconds < 10 && configManager.getProperty("show.milliseconds").equals("true")) {
            timerLabel.setText(String.format("%s:%s", formatNumber(totalSeconds), formatNumber((int) (remainingMilliseconds / 10))));
        } else {
            timerLabel.setText(String.format("%s:%s", formatNumber(totalMinutes), formatNumber(totalSeconds)));
        }
    }
}