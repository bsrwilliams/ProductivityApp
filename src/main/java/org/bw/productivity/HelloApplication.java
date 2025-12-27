package org.bw.productivity;

import javafx.application.Application;
import javafx.stage.Stage;
import org.bw.productivity.util.NavigationManager;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager.init(stage);
        NavigationManager.goTo("/org/bw/productivity/views/focus-view.fxml");
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}