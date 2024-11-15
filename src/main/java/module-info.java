module org.edem.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens org.edem.librarymanagementsystem to javafx.fxml;
    exports org.edem.librarymanagementsystem;
    exports org.edem.librarymanagementsystem.entities;
    opens org.edem.librarymanagementsystem.controller;
    opens org.edem.librarymanagementsystem.entities to javafx.fxml;
    exports org.edem.librarymanagementsystem.utils;
    opens org.edem.librarymanagementsystem.utils to javafx.fxml;
    exports org.edem.librarymanagementsystem.controller;

}