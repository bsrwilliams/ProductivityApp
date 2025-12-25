module org.bw.productivityapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens org.bw.productivity to javafx.fxml;
    exports org.bw.productivity;
    exports org.bw.productivity.controller;
    opens org.bw.productivity.controller to javafx.fxml;
}