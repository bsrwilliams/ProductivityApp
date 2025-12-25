package org.bw.productivity.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SettingsController {

    @FXML
    private VBox primaryMenu;

    @FXML
    private VBox secondaryMenu;

    @FXML
    private StackPane mainDisplay;

    private enum PrimaryMenuOption {
        TIMER,
        CALENDAR
    }

    private PrimaryMenuOption currentPrimaryMenuOption = PrimaryMenuOption.TIMER;

    @FXML
    private void onPomodoroTimerSelected() {
        currentPrimaryMenuOption = PrimaryMenuOption.TIMER;
        loadSecondaryMenu(currentPrimaryMenuOption);
    }

    @FXML
    private void onCalendarSelected() {
        currentPrimaryMenuOption = PrimaryMenuOption.CALENDAR;
        loadSecondaryMenu(currentPrimaryMenuOption);
    }

    private void loadSecondaryMenu(PrimaryMenuOption option) {
        // Clear dynamic main display
        mainDisplay.getChildren().clear();

        // Clear secondary menu options
        secondaryMenu.getChildren().clear();

        if (option.equals(PrimaryMenuOption.TIMER)) {
            Label option1 = new Label("Timer");
            option1.getStyleClass().add("nav-header");

            Button timerConfig = new Button("Config");
            timerConfig.getStyleClass().add("nav-button-inner");
            timerConfig.setPrefWidth(200);
            timerConfig.setOnAction(e -> loadContent("timer-config.fxml"));

            Button timerStyle = new Button("Timer Style");
            timerStyle.getStyleClass().add("nav-button-inner");
            timerStyle.setOnAction(e -> loadContent("timer-style.fxml"));

            Label option2 = new Label("Sound");
            option2.getStyleClass().add("nav-header");
            Button soundConfig = new Button("Config");
            soundConfig.getStyleClass().add("nav-button-inner");
            soundConfig.setPrefWidth(200);
            soundConfig.setOnAction(e -> loadContent("sound-config.fxml"));

            secondaryMenu.getChildren().addAll(option1, timerConfig, timerStyle, option2, soundConfig);
        } else {
            Label option1 = new Label("Calendar");
            Button calendarConfig = new Button("Config");
            calendarConfig.setOnAction(e -> loadContent("calendar-config.fxml"));
            Button calendarStyle = new Button("Calendar Style");

            secondaryMenu.getChildren().addAll(option1, calendarConfig, calendarStyle);
        }
    }

    private void loadContent(String fxml) {
        try {
            Parent view = FXMLLoader.load(
                    getClass().getResource("/org/bw/productivity/views/settings/" + fxml)
            );
            mainDisplay.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
