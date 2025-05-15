package hr.java.interfaces;

import hr.java.exception.FileException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Interface providing methods for loading and switching scenes in a JavaFX application.
 */

public interface LoaderInterface
{

    /**
     * Loads an FXML scene into the center of a BorderPane.
     *
     * @param rootPane The BorderPane where the scene will be loaded.
     * @param fxmlPath The path to the FXML file to load.
     * @throws FileException If the FXML file cannot be loaded.
     */
    default void loadScene(BorderPane rootPane, String fxmlPath)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent newContent = null;
        try {
            newContent = loader.load();
        } catch (IOException e) {
            throw new FileException("Could not load scene " + fxmlPath, e);
        }
        rootPane.setCenter(newContent);
    }

    /**
     * Switches the current scene to a new one.
     *
     * @param event The action event that triggers the scene switch.
     * @param root  The new root node for the scene.
     */
    default void switchScene(ActionEvent event, Parent root) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
