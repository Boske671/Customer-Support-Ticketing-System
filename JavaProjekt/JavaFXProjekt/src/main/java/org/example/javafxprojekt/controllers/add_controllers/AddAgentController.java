package org.example.javafxprojekt.controllers.add_controllers;

import hr.java.data_repository.database_repository.AgentDatabase;
import hr.java.data_repository.file_repository.LoggedInUserDataRepository;
import hr.java.interfaces.PasswordHasher;
import hr.java.entity.Agent;
import hr.java.entity.LoggedInUser;
import hr.java.enums.AgentStatus;
import hr.java.enums.AgentType;
import hr.java.exception.InvalidUserInputException;
import hr.java.exception.UserInputLengthException;
import hr.java.utils.AlertMaker;
import hr.java.utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Controller for adding a new agent in the JavaFX application.
 * Handles user input validation, agent creation, and persistence.
 */
public final class AddAgentController implements PasswordHasher
{
    private static final int NAME_MAX_LENGTH = 30;
    private static final int PASSWORD_MAX_LENGTH = 30;
    private static final int EMAIL_MAX_LENGTH = 50;
    @FXML
    private ComboBox<AgentType> agentTypeComboBox;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;

    /**
     * Adds a new agent if the input is valid.
     * Displays error messages if validation fails.
     *
     * @throws NoSuchAlgorithmException if password encryption fails
     */
    public void appendToFile() throws NoSuchAlgorithmException
    {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String eMail = emailInput.getText();
        String password = passwordInput.getText();
        AgentType agentType = agentTypeComboBox.getSelectionModel().getSelectedItem();
        StringBuilder stringBuilder = displayWarnings(firstName, lastName, eMail, password);
        if (!stringBuilder.isEmpty())
        {
            AlertMaker alertMaker = new AlertMaker("Agent not added!", "Not valid agent details!", stringBuilder.toString(), "ERROR");
            alertMaker.displayAlert();
        } else
        {
            Agent agent = new Agent(0L, firstName, lastName, eMail, AgentStatus.AVAILABLE, agentType);
            if (AgentDatabase.agentAlreadyExists(agent))
            {
                AlertMaker alertMaker = new AlertMaker("Agent not added!", "Agent already exists!", "Please choose another email address.", "ERROR");
                alertMaker.displayAlert();
            } else
            {
                AgentDatabase.addNewAgent(agent);
                LoggedInUser loggedInUser = new LoggedInUser(firstName, lastName, eMail, encryptString(password), agentType);
                LoggedInUserDataRepository.saveToTextFile(loggedInUser);
                AlertMaker alertMaker = new AlertMaker("SUCCESS", null, "Agent added!", "INFORMATION");
                alertMaker.displayAlert();
            }
        }
    }

    /**
     * Displayes the combo box with available agent types and selects a default value.
     */
    private void displayComboBoxItems()
    {
        for (AgentType status : AgentType.values())
        {
            agentTypeComboBox.getItems().add(status);
        }
        agentTypeComboBox.getSelectionModel().select(1);
    }

    /**
     * Initializes the controller by setting up the combo box options.
     */
    public void initialize()
    {
        displayComboBoxItems();
    }

    /**
     * Validates input fields and returns any validation errors.
     *
     * @param firstName agent's first name
     * @param lastName  agent's last name
     * @param eMail     agent's email
     * @param password  agent's password
     * @return a {@code StringBuilder} containing error messages if validation fails
     */

    private StringBuilder displayWarnings(String firstName, String lastName, String eMail, String password)
    {
        StringBuilder error = new StringBuilder();
        try
        {
            InputValidator.lengthValidator("Agent - First name field", firstName, NAME_MAX_LENGTH);
        } catch (UserInputLengthException e)
        {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try
        {
            InputValidator.lengthValidator("Agent - Last name field", lastName, NAME_MAX_LENGTH);
        } catch (UserInputLengthException e)
        {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try
        {
            InputValidator.lengthValidator("Agent - E-mail field", eMail, EMAIL_MAX_LENGTH);
            InputValidator.emailValidator(eMail);
        } catch (UserInputLengthException | InvalidUserInputException e)
        {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try
        {
            InputValidator.lengthValidator("Agent - Password field", password, PASSWORD_MAX_LENGTH);
        } catch (UserInputLengthException e)
        {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }

        return error;
    }

}
