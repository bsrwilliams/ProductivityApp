package org.bw.productivity.util;

import java.nio.file.Path;

public class ConfigPathUtil {

    public static Path getUserConfigPath(String folderName, String fileName) {
        String osName = System.getProperty("os.name");
        String path;

        if (osName.contains("Win")) {
            if (System.getProperty("LOCALAPPDATA") == null) {
                path = System.getProperty("user.home") + "/AppData/Local/" + folderName + "/" + fileName;
            } else {
                path = System.getProperty("LOCALAPPDATA");
            }
        } else if (osName.contains("Mac")) {
            path = System.getProperty("user.home") + "/Library/Application\\ Support" + folderName + "/" + fileName;
        } else {
            path = System.getProperty("user.home") + "/.config/" + folderName + "/" + fileName;
        }

        return Path.of(path);
    }
}
