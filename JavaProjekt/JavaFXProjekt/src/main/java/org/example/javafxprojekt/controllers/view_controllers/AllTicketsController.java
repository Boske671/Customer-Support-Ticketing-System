package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.entity.LoggedInUser;
import hr.java.entity.Ticket;
import hr.java.enums.TicketPriority;
import hr.java.enums.TicketStatus;
import hr.java.exception.FileException;
import hr.java.thread_managmenet.TicketManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Controller for managing and displaying tickets in the application.
 * This class manages the ticket table, filtering based on user input
 */
public class AllTicketsController
{
    @FXML
    private TableView<Ticket> tableView;
    @FXML
    private TableColumn<Ticket, Long> ticketID;
    @FXML
    private TableColumn<Ticket, String> summary;
    @FXML
    private TableColumn<Ticket, String> status;
    @FXML
    private TableColumn<Ticket, String> priority;
    @FXML
    private TableColumn<Ticket, Long> agent;
    @FXML
    private TableColumn<Ticket, String> created;
    @FXML
    private TableColumn<Ticket, String> resolvedColumn;
    @FXML
    private TableColumn<Ticket, Ticket> deleteColumn;
    @FXML
    private Label numberOfTickets;
    @FXML
    private ComboBox<String> ticketPriorityComboBox;
    @FXML
    private ComboBox<String> ticketStatusComboBox;

    LoggedInUser loggedInUser;

    /**
     * Displays the tickets in the TableView with filters applied from the ComboBoxes.
     * This method sets up the data for the ticket columns, filters tickets based on selected
     * priority and status, and updates the TableView with the filtered tickets.
     */
    public void displayTable()
    {
        ticketID.setCellValueFactory(new PropertyValueFactory<>("id"));
        summary.setCellValueFactory(new PropertyValueFactory<>("summary"));
        status.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        priority.setCellValueFactory(new PropertyValueFactory<>("ticketPriority"));
        agent.setCellValueFactory(new PropertyValueFactory<>("assignedAgentID"));
        created.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"))));
        resolvedColumn.setCellValueFactory(celldata ->
        {
            String outPut = "";
            if (celldata.getValue().getDateResolved() == null)
            {
                outPut = "NOT RESOLVED";
            }
            else
            {
                outPut = celldata.getValue().getDateResolved().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"));
            }
            return new SimpleStringProperty(outPut);
        });
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteColumn.setCellFactory(param -> new DeleteButtonCell());

        String selectedPriority = ticketPriorityComboBox.getValue();
        String selectedStatus = ticketStatusComboBox.getValue();
        List<Ticket> filteredTickets = TicketDatabase.getAllTickets();
        if (selectedPriority != null && !selectedPriority.isEmpty())
        {
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> ticket.getTicketPriority().toString().equalsIgnoreCase(selectedPriority.toLowerCase()))
                    .toList();
        }
        if (selectedStatus != null && !selectedStatus.isEmpty())
        {
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> ticket.getTicketStatus().toString().equalsIgnoreCase(selectedStatus.toLowerCase()))
                    .toList();
        }

        ObservableList<Ticket> tickets = FXCollections.observableList(filteredTickets);
        tableView.setItems(tickets);

        numberOfTickets.setText(String.valueOf(tickets.size()));


    }

    /**
     * Populates the ComboBoxes with the available ticket priorities and statuses.
     * Adds empty selection option as the first item.
     */
    private void displayComboBoxItems()
    {
        ticketPriorityComboBox.getItems().add("");
        for (TicketPriority singlePriority : TicketPriority.values())
        {
            ticketPriorityComboBox.getItems().add(String.valueOf(singlePriority));
        }

        ticketStatusComboBox.getItems().add("");
        for (TicketStatus singleStatus : TicketStatus.values())
        {
            ticketStatusComboBox.getItems().add(String.valueOf(singleStatus));
        }
    }

    /**
     * Initializes the view by calling methods to display the table and combo boxes,
     * and sets up the row click event to open ticket details.
     * Starts an animation to periodically refresh the table.
     */
    public void initialize()
    {
        displayTable();
        displayComboBoxItems();
        assignAndResolve();
        tableView.setRowFactory(tv ->
        {
            TableRow<Ticket> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && event.isAltDown() && !row.isEmpty())
                {
                    Ticket selectedTicket = row.getItem();
                    openTicketWindow(selectedTicket);
                }
            });
            return row;
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event2 -> displayTable()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Opens a new window showing the details of the selected ticket.
     *
     * @param ticket The selected ticket to be displayed in the new window.
     */

    private void openTicketWindow(Ticket ticket)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/TicketDetails.fxml"));
            Parent root = loader.load();
            TicketDetailsController controller = loader.getController();
            controller.setTicketDetails(ticket, loggedInUser);
            controller.setAllTicketsController(this);
            Stage stage = new Stage();
            stage.setTitle("Ticket Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e)
        {
            logger.error("Error while opening new window - {}", e.getMessage());

        }
    }

    /**
     * Sets the logged-in user for the controller.
     *
     * @param loggedInUser The user who is currently logged in.
     */

    public void setLoggedInUser(LoggedInUser loggedInUser)
    {
        this.loggedInUser = loggedInUser;
    }
    /**
     * Starts a background thread to manage ticket assignments and resolution.
     */
    public void assignAndResolve()
    {
        Thread ticketManagerThread = new Thread(new TicketManager());
        ticketManagerThread.setDaemon(true);
        ticketManagerThread.start();
    }


    /**
     * Custom TableCell to display a delete button for each row in the TableView.
     * When clicked, the button will prompt the user to confirm the deletion of the ticket.
     */
    private class DeleteButtonCell extends TableCell<Ticket, Ticket> {
        private final Button deleteButton;

        public DeleteButtonCell() {
            deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                Long ticketIDtoDelete = getTableView().getItems().get(getIndex()).getId();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Ticket");
                alert.setHeaderText("Are you sure you want to delete this ticket?");
                alert.setContentText("Ticket ID: " + ticketIDtoDelete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    TicketDatabase.deleteTicket(ticketIDtoDelete);
                    String imagePath = "data/ticket_images/" + ticketIDtoDelete + ".png";
                    try
                    {
                        Files.deleteIfExists(Paths.get(imagePath));
                    } catch (IOException e)
                    {
                        throw new FileException("Error deleting file" + e);
                    }
                    displayTable();
                }
            });
        }

        /**
         * Updates the delete button display for each ticket row.
         *
         * @param ticket The ticket to which the delete button is associated.
         * @param empty  Indicates whether the row is empty.
         */
        @Override
        protected void updateItem(Ticket ticket, boolean empty) {
            super.updateItem(ticket, empty);
            if (empty || ticket == null) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }
}
