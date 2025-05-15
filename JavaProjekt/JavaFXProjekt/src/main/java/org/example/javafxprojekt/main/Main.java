package org.example.javafxprojekt.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The Main class serves as the entry point for the JavaFX application.
 * It loads the initial scene (StartupScene.fxml) and sets it to the main stage.
 */
public class Main extends javafx.application.Application
{
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * The start method is the main entry point for JavaFX applications.
     * It loads the StartupScene.fxml file and sets it as the scene for the primary stage.
     *
     * @param stage The primary stage for this JavaFX application.
     * @throws IOException If the FXMLLoader encounters an issue while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/javafxprojekt/StartupScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PROJEKT");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     * This method is invoked when the application is run, and it starts the JavaFX application lifecycle.
     *
     * @param args Command-line arguments (not used in this case).
     */
    public static void main(String[] args)
    {
        launch();
    }
}