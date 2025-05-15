package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.entity.*;
import hr.java.data_repository.database_repository.AgentDatabase;
import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.data_repository.file_repository.ChangedDataRepository;
import hr.java.data_repository.file_repository.LoggedInUserDataRepository;
import hr.java.enums.AgentStatus;
import hr.java.enums.AgentType;
import hr.java.enums.ChangedFieldName;
import hr.java.enums.TicketStatus;
import hr.java.utils.AlertMaker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;

import static org.example.javafxprojekt.controllers.view_controllers.TicketDetailsController.sharedConfirmationDialog;

/**
 * Controller for managing and displaying all agents in the application.
 */

public class AllAgentsController {
    LoggedInUser loggedInUser;
    Agent agentMakingChanges;
    @FXML
    private TableView<Agent> tableView;
    @FXML
    private TableColumn<Agent, Long> agentIDColumn;
    @FXML
    private TableColumn<Agent, String> agentTypeColumn;
    @FXML
    private TableColumn<Agent, String> agentEmailColumn;
    @FXML
    private TableColumn<Agent, String> firstNameColumn;
    @FXML
    private TableColumn<Agent, String> lastNameColumn;
    @FXML
    private TableColumn<Agent, String> assignedTicketsColumn;
    @FXML
    private TableColumn<Agent, String> statusColumn;
    @FXML
    private TableColumn<Agent, Agent> deleteColumn;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label numberOfAgents;
    @FXML
    private ComboBox<String> agentTypeComboBox;
    private Timeline timeline;
    /**
     * Initializes the controller, sets up the combo box, table, and auto-refresh.
     */

    public void initialize() {
        displayComboBoxItems();
        displayTable();
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event2 -> displayTable()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        tableView.setOnMouseClicked(event -> timeline.pause());
        tableView.setOnKeyPressed(event -> timeline.pause());
        tableView.setOnKeyReleased(event -> resumeTimelineAfterDelay());
    }

    /**
     *  Resumes the timeline after a delay to avoid instant refresh
     */
    private void resumeTimelineAfterDelay() {
        new Timeline(new KeyFrame(Duration.seconds(5), event -> timeline.play())).play();
    }
    /**
     * Displays the list of agents in the table with filtering based on user input.
     */
    public void displayTable() {
        agentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        agentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("agentType"));
        agentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("agentStatus"));
        assignedTicketsColumn.setCellValueFactory(param -> {
            Agent agent = param.getValue();
            List<Ticket> assignedTickets = TicketDatabase.getTicketsByAgentId(agent.getId());
            assignedTickets = assignedTickets.stream().filter(data -> data.getTicketStatus().equals(TicketStatus.IN_PROGRESS)).toList();
            String ticketIds = assignedTickets.stream()
                    .map(ticket -> String.valueOf(ticket.getId()))
                    .reduce((id1, id2) -> id1 + ", " + id2)
                    .orElse("");
            if (ticketIds.isEmpty()) {
                ticketIds = "No tickets";
            }
            AgentStatus status = assignedTickets.isEmpty() ? AgentStatus.AVAILABLE : AgentStatus.BUSY;
            agent.setAgentStatus(status);
            return new ReadOnlyObjectWrapper<>(ticketIds);
        });
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        editMethods();
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteColumn.setCellFactory(param -> new DeleteButtonCell());
        List<Agent> filteredList = AgentDatabase.getAllAgents();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String agentTypeComboBoxValue = agentTypeComboBox.getValue();
        if (!firstName.isEmpty()) {
            filteredList = filteredList.stream().filter(agent -> agent.getFirstName().toLowerCase().contains(firstName.toLowerCase())).toList();
        }
        if (!lastName.isEmpty()) {
            filteredList = filteredList.stream().filter(agent -> agent.getLastName().toLowerCase().contains(lastName.toLowerCase())).toList();
        }
        if (agentTypeComboBoxValue != null && !agentTypeComboBoxValue.isEmpty()) {
            filteredList = filteredList.stream().filter(agent -> agent.getAgentType().toString().equalsIgnoreCase(agentTypeComboBoxValue)).toList();
        }
        ObservableList<Agent> observableList = FXCollections.observableList(filteredList);
        tableView.setItems(observableList);
        numberOfAgents.setText(String.valueOf(observableList.size()));
    }

    /**
     * Populates the agent type combo box with available types.
     */
    private void displayComboBoxItems() {
        agentTypeComboBox.getItems().add("");
        for (AgentType singleType : AgentType.values()) {
            agentTypeComboBox.getItems().add(String.valueOf(singleType));
        }
    }

    /**
     * Sets up edit methods for agent first and last names.
     */
    private void editMethods() {
        firstNameColumn.setOnEditCommit(event ->
        {
            Agent agent = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue.length() < 30) {
                if (sharedConfirmationDialog("firstName", agent.getFirstName(), agent.getLastName())) {
                    LoggedInUserDataRepository.updateLoggedInUser(agent);
                    EntityChange<Entity> entityChange = new EntityChange<>(agentMakingChanges, agent, ChangedFieldName.FIRSTNAME.toString(), agent.getFirstName(), newValue);
                    agent.setFirstName(event.getNewValue());
                    AgentDatabase.updateAgent("firstname", newValue, agent.getId());
                    ChangedDataRepository.changeSingleEntity(entityChange);
                    displayTable();
                }
            } else {
                AlertMaker alertMaker = new AlertMaker("Error", null, "The maximum length is 30 + for First name", "ERROR");
                alertMaker.displayAlert();
            }
        });
        lastNameColumn.setOnEditCommit(event ->
        {
            Agent agent = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue.length() < 30) {
                if (sharedConfirmationDialog("lastname", newValue, agent.getLastName())) {
                    LoggedInUserDataRepository.updateLoggedInUser(agent);
                    EntityChange<Entity> entityChange = new EntityChange<>(agentMakingChanges, agent, ChangedFieldName.LASTNAME.toString(), agent.getLastName(), newValue);
                    agent.setLastName(event.getNewValue());
                    AgentDatabase.updateAgent("lastname", newValue, agent.getId());
                    ChangedDataRepository.changeSingleEntity(entityChange);
                    displayTable();
                }
            } else {
                AlertMaker alertMaker = new AlertMaker("Error", null, "The maximum length is 30 + for Last name", "ERROR");
                alertMaker.displayAlert();
            }
        });
    }

    /**
     * Sets the logged-in user for the controller.
     *
     * @param loggedInUser the logged-in user
     */
    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.agentMakingChanges = AgentDatabase.getAgentByEmail(loggedInUser.getEmail());
    }

    /**
     * A custom table cell for deleting an agent.
     */
    private class DeleteButtonCell extends TableCell<Agent, Agent> {
        private final Button deleteButton;

        /**
         * Initializes the delete button cell.
         */
        public DeleteButtonCell() {
            deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                Agent agent = getTableView().getItems().get(getIndex());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete agent");
                alert.setHeaderText("Are you sure you want to delete this agent?");
                alert.setContentText("Agent ID: " + agent.getId());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    AgentDatabase.deleteAgent(agent);
                    displayTable();
                }
            });
        }
        /**
         * Updates the delete button cell.
         *
         * @param agent the agent being displayed
         * @param empty if the cell is empty
         */
        @Override
        protected void updateItem(Agent agent, boolean empty) {
            super.updateItem(agent, empty);
            if (empty || agent == null) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }
}
