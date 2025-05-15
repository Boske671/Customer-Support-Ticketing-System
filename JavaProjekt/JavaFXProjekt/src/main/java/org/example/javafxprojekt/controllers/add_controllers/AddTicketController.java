package org.example.javafxprojekt.controllers.add_controllers;

import hr.java.data_repository.database_repository.CustomerDatabase;
import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.entity.Customer;
import hr.java.entity.Person;
import hr.java.entity.Ticket;
import hr.java.enums.TicketPriority;
import hr.java.enums.TicketStatus;
import hr.java.exception.FileException;
import hr.java.exception.InvalidUserInputException;
import hr.java.exception.UserInputLengthException;
import hr.java.utils.AlertMaker;
import hr.java.utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Controller class for adding a new ticket.
 * Manages user input, validates data, and interacts with the database.
 */

public class AddTicketController
{
    private static final int NAME_MAX_LENGTH = 30;
    private static final int EMAIL_MAX_LENGTH = 50;
    private static final int DESCRIPTION_MAX_LENGTH = 300;
    private static final int SUMMARY_MAX_LENGTH = 50;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField eMailInput;
    @FXML
    private TextField summaryInput;
    @FXML private TextArea descriptionInput;
    @FXML private ComboBox<TicketPriority> ticketPriorityComboBox;
    @FXML private ImageView imageView;


    /**
     * Handles ticket submission, validates input, and stores the ticket in the database.
     */

    public void appendToFile()
    {
        StringBuilder stringBuilder;
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String eMail = eMailInput.getText();
        String summary = summaryInput.getText();
        String description = descriptionInput.getText();
        TicketPriority ticketPriority = ticketPriorityComboBox.getValue();

        stringBuilder = displayWarnings(firstName, lastName, eMail, summary, description);

        if (stringBuilder.isEmpty())
        {
            Long customerID;
            Customer customer = new Customer(new Person( 0L, firstName, lastName), eMail);
            if (!CustomerDatabase.doesCustomerExist(customer))
            {
                customerID = CustomerDatabase.addNewCustomer(customer);
            } else
            {
                customerID = CustomerDatabase.idFromCustomerEmail(customer);
            }

            Ticket ticket = new Ticket.Builder().setSummary(summary).setDescription(description)
                    .setTicketStatus(TicketStatus.OPEN).setTicketPriority(ticketPriority)
                    .setCustomerID(customerID).build();
            Long ticketID = TicketDatabase.addTicket(ticket);
            saveImage(ticketID);
            AlertMaker alertMaker = new AlertMaker("SUCCESS", null, "Ticket submitted", "INFORMATION");
            alertMaker.displayAlert();
        } else
        {
            AlertMaker alertMaker = new AlertMaker("Ticket not submitted!", "Not valid ticket details!", stringBuilder.toString(), "ERROR");
            alertMaker.displayAlert();
        }
    }

    /**
     * Validates user input and returns a string with error messages if any validation fails.
     */

    private StringBuilder displayWarnings(String firstName, String lastName, String eMail, String summary, String description)
    {
        StringBuilder error = new StringBuilder();
        try {
            InputValidator.lengthValidator("Ticket - First name field", firstName, NAME_MAX_LENGTH);
        } catch (UserInputLengthException e) {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try {
            InputValidator.lengthValidator("Ticket - Last name field", lastName, NAME_MAX_LENGTH);
        } catch (UserInputLengthException e) {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try {
            InputValidator.lengthValidator("Ticket - E-mail field", eMail, EMAIL_MAX_LENGTH);
            InputValidator.emailValidator(eMail);
        } catch (UserInputLengthException | InvalidUserInputException e) {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try {
            InputValidator.onlyLengthValidation("Ticket - Description field", description, DESCRIPTION_MAX_LENGTH);
        } catch (UserInputLengthException e) {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        try {
            InputValidator.lengthValidator("Ticket - Summary field", summary, SUMMARY_MAX_LENGTH);
        } catch (UserInputLengthException e) {
            error.append(e.getMessage()).append(System.lineSeparator());
            logger.error(e.getMessage());
        }
        return error;
    }


    /**
     * Populates the ComboBox with ticket priority options.
     */

    private void displayComboBoxItems()
    {
        for (TicketPriority priority : TicketPriority.values())
        {
            ticketPriorityComboBox.getItems().add(priority);
        }
        ticketPriorityComboBox.getSelectionModel().select(1);
    }

    /**
     * Initializes the controller by setting up the ComboBox items.
     */
    public void initialize()
    {
        displayComboBoxItems();
    }

    /**
     * Handles image drag-and-drop functionality.
     */
    public void imageViewDragDropped(DragEvent event)
    {
        Dragboard db = event.getDragboard();
        if (db.hasFiles() || db.hasImage())
        {
            try
            {
                imageView.setImage(new Image(new FileInputStream(db.getFiles().getFirst())));
            } catch (FileNotFoundException e)
            {
                throw new FileException("File not found");
            }
        }
    }

    /**
     * Enables image drag-over functionality.
     */
    public void imageViewDragOver(DragEvent event)
    {
        Dragboard db = event.getDragboard();
        if (db.hasFiles() || db.hasImage())
        {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    /**
     * Converts a JavaFX Image to a BufferedImage.
     */
    private BufferedImage convertToBufferedImage(Image image)
    {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int argb = image.getPixelReader().getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        return bufferedImage;
    }

    /**
     * Saves the uploaded image associated with the ticket.
     */
    private void saveImage(Long ticketID)
    {
        if (imageView.getImage() != null)
        {
            Image image = imageView.getImage();
            File file = new File("data/ticket_images/" + ticketID + ".png");
            try
            {
                BufferedImage bufferedImage = convertToBufferedImage(image);
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e)
            {
                throw new FileException("Failed to save the image, " + e.getMessage());
            }
        }
    }

    /**
     * Removes the currently displayed image.
     */
    public void removeImage()
    {
        if (imageView.getImage() != null)
        {
            imageView.setImage(null);
        }
    }
}
