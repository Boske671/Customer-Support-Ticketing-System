package org.example.javafxprojekt.controllers.view_controllers;


import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.entity.LoggedInUser;
import hr.java.entity.Ticket;
import hr.java.enums.TicketPriority;
import hr.java.enums.TicketStatus;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for managing and displaying tickets in the application.
 * This class manages the ticket table, filtering based on user input
 */
public class AllTicketsNormalAgentController
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event2 -> displayTable()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}