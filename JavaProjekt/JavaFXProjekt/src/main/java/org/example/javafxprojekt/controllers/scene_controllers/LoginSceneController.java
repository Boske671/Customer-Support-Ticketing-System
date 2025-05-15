package org.example.javafxprojekt.controllers.scene_controllers;

import hr.java.data_repository.file_repository.LoggedInUserManager;
import hr.java.exception.FileException;
import hr.java.file_paths.FilePath;
import hr.java.entity.LoggedInUser;
import hr.java.enums.AgentType;
import hr.java.interfaces.LoaderInterface;
import hr.java.utils.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * Controller for handling user login in the JavaFX application.
 * Validates login credentials and loads the appropriate user scene.
 */

public class LoginSceneController implements LoaderInterface
{
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField eMailInput;
    @FXML
    private PasswordField passwordInput;

    private final LoggedInUserManager loggedInUserManager = new LoggedInUserManager();

    /**
     * Handles the login process by verifying user credentials.
     * If valid, it loads the corresponding user scene.
     *
     * @param event the action event triggered by the login button
     * @throws IOException              if loading the scene fails
     * @throws NoSuchAlgorithmException if password hashing fails
     */
    public void login(ActionEvent event) throws IOException, NoSuchAlgorithmException
    {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String eMail = eMailInput.getText();
        String password = passwordInput.getText();

        AgentType agentType = loggedInUserManager.exists(firstName, lastName, eMail, password);

        if (agentType == null)
        {
            AlertMaker alertMaker = new AlertMaker("Error", null, "Incorrect information", "ERROR");
            alertMaker.displayAlert();
            return;
        }

        LoggedInUser loggedInUser = new LoggedInUser(firstName, lastName, eMail, password, agentType);
        loadUserScene(loggedInUser, event);
    }
    /**
     * Handles the login process by verifying user credentials.
     * If valid, it loads the corresponding user scene.
     *
     * @param event the action event triggered by the login button
     * @throws FileException if there is an error loading the scene
     */
    private void loadUserScene(LoggedInUser user, ActionEvent event)
    {
        String fxmlPath = user.getType().equals(AgentType.SUPERAGENT) ?
                FilePath.SUPER_AGENT_FXML_PATH.getPath() : FilePath.NORMAL_AGENT_FXML_PATH.getPath();

        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = null;
        try {
            root = sceneLoader.load();
        } catch (IOException e) {
            throw new FileException("Error loading scene file", e);
        }

        if (user.getType() == AgentType.SUPERAGENT)
        {
            SceneController sceneController = sceneLoader.getController();
            sceneController.displayAgentInfo(user);
        } else
        {
            NormalAgentMainSceneController sceneController = sceneLoader.getController();
            sceneController.displayAgentInfo(user);
        }

        switchScene(event, root);
    }
}