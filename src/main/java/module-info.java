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

    opens org.tyresemv.smkonnect to javafx.fxml;
    exports org.tyresemv.smkonnect to ALL_UNNAMED;
}