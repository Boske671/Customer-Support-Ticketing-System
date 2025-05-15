package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.entity.Customer;
import hr.java.data_repository.database_repository.CustomerDatabase;
import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.entity.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for managing and displaying customers in the application.
 * This class manages the customer table, filtering by first name, last name, and number of tickets.
 */
public class AllCustomersNormalAgentController
{

    @FXML
    private Label numberOfCustomers;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField numberOfMadeTicketsInput;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Long> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> firstNameColumn;

    @FXML
    private TableColumn<Customer, String> lastNameColumn;

    @FXML
    private TableColumn<Customer, String> eMailColumn;

    @FXML
    private TableColumn<Customer, String> madeTicketsColumn;
    List<Ticket> tickets = TicketDatabase.getAllTickets();

    /**
     * Initializes the controller and displays the customer table.
     */
    public void initialize()
    {
        displayTable();
    }

    /**
     * Initializes the customer table and applies filters based on user input.
     */
    public void displayTable()
    {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        eMailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        madeTicketsColumn.setCellValueFactory(param ->
        {
            Customer customer = param.getValue();
            List<String> matchingTicketIds = tickets.stream()
                    .filter(ticket -> ticket.getCustomerID().equals(customer.getId()))
                    .map(ticket -> String.valueOf(ticket.getId()))
                    .toList();
            return new SimpleStringProperty(String.join(", ", matchingTicketIds));
        });

        madeTicketsColumn.setCellFactory(param -> new TableCell<>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText(null);
                    setTooltip(null);
                } else
                {
                    setText(item);
                    setTooltip(new Tooltip(item));
                }
            }
        });

        Set<Customer> filteredCustomers = new HashSet<>(CustomerDatabase.getAllCustomers());

        if (!firstNameInput.getText().isEmpty()) {
            filteredCustomers = filteredCustomers.stream()
                    .filter(customer -> customer.getFirstName().toLowerCase().contains(firstNameInput.getText().toLowerCase()))
                    .collect(Collectors.toSet());
        }

        if (!lastNameInput.getText().isEmpty()) {
            filteredCustomers = filteredCustomers.stream()
                    .filter(customer -> customer.getLastName().toLowerCase().contains(lastNameInput.getText().toLowerCase()))
                    .collect(Collectors.toSet());
        }

        if (!numberOfMadeTicketsInput.getText().isEmpty()) {
            int ticketsFilter = Integer.parseInt(numberOfMadeTicketsInput.getText());
            filteredCustomers = filteredCustomers.stream()
                    .filter(customer -> {
                        long matchingTicketsCount = tickets.stream()
                                .filter(ticket -> ticket.getCustomerID().equals(customer.getId()))
                                .count();
                        return matchingTicketsCount == ticketsFilter;
                    })
                    .collect(Collectors.toSet());
        }

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(filteredCustomers);
        customerTable.setItems(customerObservableList);
        numberOfCustomers.setText(String.valueOf(customerObservableList.size()));
    }
}
