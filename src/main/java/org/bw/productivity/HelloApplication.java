package org.bw.productivity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bw.productivity.util.NavigationManager;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager.init(stage);
        NavigationManager.goTo("/org/bw/productivity/views/focus-view.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/focus-view.fxml"));
        stage.setResizable(false);
//        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(String.valueOf(getClass().getResource("css/focus-style.css")));
        stage.setTitle("Hello!");
//        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}