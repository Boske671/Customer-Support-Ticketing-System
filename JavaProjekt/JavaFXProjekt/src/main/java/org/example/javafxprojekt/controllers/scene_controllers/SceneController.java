package org.example.javafxprojekt.controllers.scene_controllers;

import hr.java.entity.LoggedInUser;
import hr.java.interfaces.LoaderInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.example.javafxprojekt.controllers.view_controllers.AllAgentsController;
import org.example.javafxprojekt.controllers.view_controllers.AllTicketsController;

import java.io.IOException;

/**
 * Controller for managing scene navigation and UI updates in the main application window.
 */

public class SceneController implements LoaderInterface
{
    private LoggedInUser loggedInUser;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label eMailLabel;
    @FXML
    private Label agentTypeLabel;

    /**
     * Loads and displays the "All Tickets" scene, passing the logged-in user.
     *
     * @param event the action event that triggered the scene change
     * @throws IOException if loading the FXML file fails
     */
    @FXML
    private void allTicketsItemAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/AllTicketsScene.fxml"));
        Parent newContent = loader.load();
        AllTicketsController allTicketsController = loader.getController();
        allTicketsController.setLoggedInUser(loggedInUser);
        rootPane.setCenter(newContent);
    }

    /**
     * Loads the "New Ticket" scene.
     *
     * @param event the action event that triggered the scene change
     */
    @FXML
    private void newTicketMenuItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/NewTicketScene.fxml");
    }
    /**
     * Loads the "New Agent" scene.
     *
     * @param event the action event that triggered the scene change
     */
    @FXML
    private void newAgentItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/NewAgentScene.fxml");
    }

    /**
     * Loads and displays the "All Agents" scene, passing the logged-in user.
     *
     * @param event the action event that triggered the scene change
     * @throws IOException if loading the FXML file fails
     */
    @FXML
    private void allAgentsItemAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/AllAgentsScene.fxml"));
        Parent newContent = loader.load();
        AllAgentsController allAgentsController = loader.getController();
        allAgentsController.setLoggedInUser(loggedInUser);
        rootPane.setCenter(newContent);
    }



    /**
     * Loads the "Customers" scene.
     *
     * @param event the action event that triggered the scene change
     * @throws IOException if loading the FXML file fails
     */
    @FXML
    private void customersMenuItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/Customers.fxml");
    }

    /**
     * Loads the home page scene and updates agent information.
     *
     * @param event the action event that triggered the scene change
     * @throws IOException if loading the FXML file fails
     */
    @FXML
    private void homePageItemAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/MainScene.fxml"));
        Parent newContent = loader.load();
        SceneController controller = loader.getController();
        controller.displayAgentInfo(loggedInUser);
        rootPane.getChildren().setAll(newContent);
    }

    /**
     * Loads the "Resolution Log" scene.
     *
     * @param event the action event that triggered the scene change
     */
    @FXML
    private void resolutionLogMenuItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/ResolutionLogScene.fxml");
    }

    /**
     * Loads the "Made Changes" scene.
     *
     * @param event the action event that triggered the scene change
     */
    @FXML
    private void madeChangesItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/MadeChanges.fxml");
    }

    /**
     * Displays the logged-in agent's information in the UI.
     *
     * @param loggedInUser the logged-in user whose information will be displayed
     */
    public void displayAgentInfo(LoggedInUser loggedInUser)
    {
        this.loggedInUser = loggedInUser;
        agentTypeLabel.setText("Agent type: " + loggedInUser.getType().toString());
        firstNameLabel.setText("First name: " + loggedInUser.getName());
        lastNameLabel.setText("Last name: " + loggedInUser.getLastName());
        eMailLabel.setText("E-mail: " + loggedInUser.getEmail());
    }

}