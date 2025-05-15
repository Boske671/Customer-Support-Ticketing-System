package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.data_repository.database_repository.AgentDatabase;
import hr.java.data_repository.database_repository.CustomerDatabase;
import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.data_repository.file_repository.ChangedDataRepository;
import hr.java.entity.*;
import hr.java.enums.ChangedFieldName;
import hr.java.file_paths.FilePath;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Controller for displaying and editing ticket details in the application.
 * Handles the display of ticket, customer, and agent details, as well as tracking changes to ticket fields such as description and summary.
 * Allows users to confirm or discard changes to the ticket fields.
 */

public class TicketDetailsController
{
    @FXML
    private TitledPane titledPane;
    @FXML
    private Label customerIDLabel;
    @FXML
    private Label customerFirstNameLabel;
    @FXML
    private Label customerLastNameLabel;
    @FXML
    private Label customerEmailLabel;
    @FXML
    private TextArea ticketDescription;
    @FXML
    private ImageView ticketImageView;
    @FXML
    private TextArea ticketSummary;

    Ticket currentTicket;
    AllTicketsController allTicketsController;
    LoggedInUser loggedInUser;
    Agent agent;

    private static final String DESCRIPTION = "description";
    private static final String SUMMARY = "summary";

    /**
     * Sets the controller for the "All Tickets" view to allow refreshing of the ticket list when changes are saved.
     *
     * @param allTicketsController The controller for the "All Tickets" view.
     */
    public void setAllTicketsController(AllTicketsController allTicketsController)
    {
        this.allTicketsController = allTicketsController;
    }

    /**
     * Sets the details of the selected ticket, including customer and agent information, description, summary, and associated image.
     * Also tracks changes made to the ticket description and summary.
     *
     * @param ticket The ticket whose details are to be displayed.
     * @param loggedInUser The logged-in user (agent) viewing the ticket.
     */
    public void setTicketDetails(Ticket ticket, LoggedInUser loggedInUser)
    {
        this.currentTicket = ticket;
        this.loggedInUser = loggedInUser;
        this.agent = AgentDatabase.getAgentByEmail(loggedInUser.getEmail());
        titledPane.setText("Ticket ID: " + ticket.getId());
        customerIDLabel.setText(String.valueOf(ticket.getCustomerID()));

        Customer customer = CustomerDatabase.getCustomerById(ticket.getCustomerID());
        customerFirstNameLabel.setText(customer.getFirstName());
        customerLastNameLabel.setText(customer.getLastName());
        customerEmailLabel.setText(customer.getCustomerEmail());

        ticketDescription.setText(ticket.getDescription());
        ticketSummary.setText(ticket.getSummary());

        String imagePath = "data/ticket_images/" + ticket.getId() + ".png";
        if (Files.exists(Paths.get(imagePath)))
        {
            ticketImageView.setImage(new Image(Paths.get(imagePath).toUri().toString()));
        } else
        {
            ticketImageView.setImage(new Image(Paths.get(FilePath.IMAGE_NOT_FOUND.getPath()).toUri().toString()));
        }
        trackChanges(ticket);
    }

    /**
     * Tracks changes to the ticket fields (description and summary) and prompts the user to save or discard changes.
     * A listener is added to the ticket description and summary fields to detect when they lose focus, indicating a potential change.
     *
     * @param ticket The ticket whose fields are being tracked.
     */
    private void trackChanges(Ticket ticket)
    {
        ticketDescription.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
        {
            if (Boolean.FALSE.equals(isNowFocused))
            {
                saveChangesIfRequired(ticket, ChangedFieldName.DESCRIPTION.toString(),
                        ticket.getDescription(), ticketDescription.getText());
            }
        });

        ticketSummary.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
        {
            if (Boolean.FALSE.equals(isNowFocused))
            {
                saveChangesIfRequired(ticket, ChangedFieldName.SUMMARY.toString(),
                        ticket.getSummary(), ticketSummary.getText());
            }
        });
    }

    /**
     * If changes were made to a ticket field, prompts the user with a confirmation dialog to save or discard the changes.
     * If changes are saved, updates the ticket in the database and tracks the change.
     *
     * @param ticket The ticket whose field was changed.
     * @param fieldName The name of the field being changed (e.g., "description" or "summary").
     * @param oldValue The old value of the field.
     * @param newValue The new value of the field.
     */
    private void saveChangesIfRequired(Ticket ticket, String fieldName, String oldValue, String newValue)
    {
        if (newValue.equals(oldValue))
        {
            return;
        }

        if (!sharedConfirmationDialog(fieldName, oldValue, newValue))
        {
            restoreOldValue(fieldName, oldValue);
            return;
        }
        int maxLength = -1;
        if (fieldName.equals(DESCRIPTION))
        {
            maxLength = 300;
        }
        if (fieldName.equals(SUMMARY))
        {
            maxLength = 50;
        }
        if (maxLength > 0 && !isValid(newValue, maxLength))
        {
            showAlert("Exceeded maximum length of " + maxLength + " characters!");
            return;
        }

        updateField(ticket, fieldName, newValue);
        saveChange(ticket, fieldName, oldValue, newValue);
        allTicketsController.displayTable();
    }

    /**
     * Restores the old value to the field if the user chooses to discard the changes.
     *
     * @param fieldName The name of the field being reverted.
     * @param oldValue The old value of the field.
     */
    private void restoreOldValue(String fieldName, String oldValue)
    {
        if (fieldName.equals(DESCRIPTION))
        {
            ticketDescription.setText(oldValue);
        } else if (fieldName.equals(SUMMARY))
        {
            ticketSummary.setText(oldValue);
        }
    }
    /**
     * Updates the field with the new value if the user chooses to save the changes.
     *
     * @param ticket The ticket whose field is being updated.
     * @param fieldName The name of the field being updated.
     * @param newValue The new value of the field.
     */
    private void updateField(Ticket ticket, String fieldName, String newValue)
    {
        if (fieldName.equals(DESCRIPTION))
        {
            ticketDescription.setText(newValue);
        } else if (fieldName.equals(SUMMARY))
        {
            ticket.setSummary(newValue);
        }
    }

    /**
     * Saves the change to the ticket and logs the change.
     *
     * @param ticket The ticket being updated.
     * @param fieldName The name of the field being updated.
     * @param oldValue The old value of the field.
     * @param newValue The new value of the field.
     */
    private void saveChange(Ticket ticket, String fieldName, String oldValue, String newValue)
    {
        TicketDatabase.updateTicket(fieldName, newValue, ticket.getId());
        EntityChange<Entity> entityChange = new EntityChange<>(
                agent, ticket, fieldName, oldValue, newValue
        );
        ChangedDataRepository.changeSingleEntity(entityChange);
    }

    /**
     * Displays a confirmation dialog asking the user whether they want to save the changes to a ticket field.
     *
     * @param fieldName The name of the field being changed.
     * @param oldValue The old value of the field.
     * @param newValue The new value of the field.
     * @return True if the user confirms the changes, false otherwise.
     */
    static boolean sharedConfirmationDialog(String fieldName, String oldValue, String newValue)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Changes");
        alert.setHeaderText("Changes will be saved");
        alert.setContentText("You are about to update the " + fieldName + ":\n\n"
                + "Old Value:\n" + oldValue + "\n\n"
                + "New Value:\n" + newValue + "\n\n"
                + "Do you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Validates that the text does not exceed the specified maximum length.
     *
     * @param text The text to validate.
     * @param maxLength The maximum allowed length for the text.
     * @return True if the text is valid (i.e., within the maximum length), false otherwise.
     */
    private boolean isValid(String text, int maxLength)
    {
        return text.length() <= maxLength;
    }

    /**
     * Displays an error alert if the user input is invalid.
     *
     * @param message The message to display in the error alert.
     */
    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

