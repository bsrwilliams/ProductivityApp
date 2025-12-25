package org.bw.productivity.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public final class NavigationManager {

    private static Stage stage;

    private NavigationManager() {}

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void goTo(String viewNavigation) throws IOException {
        Parent root = ViewLoader.load(viewNavigation).getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getStylesheet(viewNavigation));

        stage.setScene(scene);
    }

    // TODO: Refactor
    private static String getStylesheet(String viewNavigation) {
        if (viewNavigation.contains("focus")) {
            return Objects.requireNonNull(NavigationManager.class.getResource("/org/bw/productivity/css/focus-style.css")).toExternalForm();
        } else if (viewNavigation.contains("calendar")) {
            return Objects.requireNonNull(NavigationManager.class.getResource("/org/bw/productivity/css/style.css")).toExternalForm();
        } else {
            return Objects.requireNonNull(NavigationManager.class.getResource("/org/bw/productivity/css/settings.css")).toExternalForm();
        }
    }
}
