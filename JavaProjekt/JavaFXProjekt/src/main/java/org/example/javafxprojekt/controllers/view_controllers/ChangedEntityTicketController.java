package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.data_repository.database_repository.CustomerDatabase;
import hr.java.entity.Agent;
import hr.java.entity.Customer;
import hr.java.entity.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the "Changed Entity - Ticket" view, used to display ticket details and changes.
 * This controller is responsible for populating the view with the details of a ticket,
 * the associated agent, and the changes made to the ticket.
 */
public class ChangedEntityTicketController
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
    private Label agentTypeLabel;
    @FXML
    private Label agentEmailLabel;
    @FXML
    private Label agentIDLabel;
    @FXML
    private Label agentFirstNameLabel;
    @FXML
    private Label agentLastNameLabel;
    @FXML
    private TextArea oldValueTextArea;
    @FXML
    private TextArea newValueTextArea;

    Ticket currentTicket;
    Agent agent;

    /**
     * Sets the details for the selected ticket, the associated agent, and the changes made.
     * This method populates the various labels and text areas in the view with the
     * ticket details, customer information, agent details, and the old and new values
     * for the ticket change.
     *
     * @param ticket The ticket whose details are to be displayed.
     * @param agent The agent who made the changes to the ticket.
     * @param oldValue The old value of the ticket field that was changed.
     * @param newValue The new value of the ticket field after the change.
     */
    public void setTicketDetails(Ticket ticket, Agent agent, String oldValue, String newValue)
    {
        this.currentTicket = ticket;
        this.agent = agent;
        titledPane.setText("Ticket ID: " + ticket.getId());
        customerIDLabel.setText(String.valueOf(ticket.getCustomerID()));

        Customer customer = CustomerDatabase.getCustomerById(ticket.getCustomerID());
        customerFirstNameLabel.setText(customer.getFirstName());
        customerLastNameLabel.setText(customer.getLastName());
        customerEmailLabel.setText(customer.getCustomerEmail());

        agentIDLabel.setText(String.valueOf(agent.getId()));
        agentFirstNameLabel.setText(agent.getFirstName());
        agentLastNameLabel.setText(agent.getLastName());
        agentEmailLabel.setText(agent.getEmail());
        agentTypeLabel.setText(agent.getAgentType().toString());

        oldValueTextArea.setText(oldValue);
        newValueTextArea.setText(newValue);
    }
}
