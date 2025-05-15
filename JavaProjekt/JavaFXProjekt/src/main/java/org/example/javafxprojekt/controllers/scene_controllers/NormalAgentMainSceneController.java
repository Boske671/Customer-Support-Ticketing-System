package org.example.javafxprojekt.controllers.scene_controllers;

import hr.java.entity.LoggedInUser;
import hr.java.interfaces.LoaderInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class NormalAgentMainSceneController implements LoaderInterface
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

    @FXML
    private void allTicketsItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/AllTicketsSceneNormalAgent.fxml");
    }

    @FXML
    private void customersMenuItemAction(ActionEvent event)
    {
        loadScene(rootPane, "/org/example/javafxprojekt/Customers_NormalAgent.fxml");
    }

    @FXML
    private void homePageItemAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/NormalAgentMainScene.fxml"));
        Parent newContent = loader.load();
        NormalAgentMainSceneController controller = loader.getController();
        controller.displayAgentInfo(loggedInUser);
        rootPane.getChildren().setAll(newContent);
    }


    public void displayAgentInfo(LoggedInUser loggedInUser)
    {
        this.loggedInUser = loggedInUser;
        agentTypeLabel.setText("Agent type: " + loggedInUser.getType().toString());
        firstNameLabel.setText("First name: " + loggedInUser.getName());
        lastNameLabel.setText("Last name: " + loggedInUser.getLastName());
        eMailLabel.setText("E-mail: " + loggedInUser.getEmail());
    }
}
