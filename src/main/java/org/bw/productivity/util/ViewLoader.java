package org.bw.productivity.util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public final class ViewLoader {

    private ViewLoader() {}

    public static FXMLLoader load(String viewToLoad) throws IOException {
        URL resource = ViewLoader.class.getResource(viewToLoad);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.load();
        return loader;
    }
}
