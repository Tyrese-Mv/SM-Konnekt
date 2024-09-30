module org.tyresemv.smkonnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires scribejava.core;
    requires scribejava.apis;
    requires jdk.jsobject;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens org.tyresemv.smkonnect to javafx.fxml;
    exports org.tyresemv.smkonnect to javafx.graphics;
    exports org.tyresemv.smkonnect.controllers to ALL_UNNAMED;
    opens org.tyresemv.smkonnect.controllers to javafx.fxml;
}