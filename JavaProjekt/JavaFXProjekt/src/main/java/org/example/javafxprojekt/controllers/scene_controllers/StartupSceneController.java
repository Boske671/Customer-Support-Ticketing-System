package org.example.javafxprojekt.controllers.scene_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for managing scene transitions in the startup interface.
 */

public class StartupSceneController
{
    /**
     * Loads the customer page (New Ticket Scene).
     *
     * @param event The action event triggering the transition.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void customerPage(ActionEvent event) throws IOException
    {
        setNewStage("/org/example/javafxprojekt/NewTicketScene.fxml", event);
    }
    /**
     * Loads the agent login page.
     *
     * @param event The action event triggering the transition.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void agentPage(ActionEvent event) throws IOException
    {
        setNewStage("/org/example/javafxprojekt/LoginScene.fxml", event);
    }

    /**
     * Helper method to switch scenes based on the provided FXML file.
     *
     * @param fxmlPath The path to the FXML file.
     * @param event    The action event that triggered the scene change.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public void setNewStage(String fxmlPath, ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
