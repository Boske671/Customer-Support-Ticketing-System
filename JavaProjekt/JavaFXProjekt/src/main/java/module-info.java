module org.example.javafxprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires com.h2database;
    requires ch.qos.logback.classic;
    requires org.slf4j;

    exports org.example.javafxprojekt.controllers.scene_controllers;
    exports org.example.javafxprojekt.controllers.add_controllers;
    exports org.example.javafxprojekt.controllers.view_controllers;
    exports org.example.javafxprojekt.main;

    opens org.example.javafxprojekt.controllers.scene_controllers to javafx.fxml;
    opens org.example.javafxprojekt.controllers.add_controllers to javafx.fxml;
    opens org.example.javafxprojekt.controllers.view_controllers to javafx.fxml;
    opens hr.java.enums to javafx.base;
    opens hr.java.entity to javafx.base;
    opens org.example.javafxprojekt.main to javafx.fxml;
    opens org.example.javafxprojekt to javafx.fxml;
    exports hr.java.thread_managmenet;
    opens hr.java.thread_managmenet to javafx.fxml;
    exports hr.java.interfaces;
    opens hr.java.interfaces to javafx.fxml;
}