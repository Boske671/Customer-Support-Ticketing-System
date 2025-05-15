package org.example.javafxprojekt.controllers.view_controllers;
import hr.java.entity.Agent;
import hr.java.data_repository.file_repository.ChangedDataRepository;
import hr.java.entity.Entity;
import hr.java.entity.EntityChange;
import hr.java.entity.Ticket;
import hr.java.enums.ChangedEntity;
import hr.java.enums.ChangedFieldName;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Controller class for managing and displaying all entity changes in a TableView.
 */
public class AllChangedEntitesController {
    @FXML
    private TableView<EntityChange<Entity>> tableView;
    @FXML
    private TableColumn<EntityChange<Entity>, String> agentIdColumn;
    @FXML
    private TableColumn<EntityChange<Entity>, String> changedEntityColumn;
    @FXML
    private TableColumn<EntityChange<Entity>, String> fieldNameColumn;
    @FXML
    private TableColumn<EntityChange<Entity>, String> oldValueColumn;
    @FXML
    private TableColumn<EntityChange<Entity>, String> newValueColumn;
    @FXML
    private TableColumn<EntityChange<Entity>, String> changedAtColumn;
    @FXML
    private ComboBox<String> changedEntityComboBox;
    @FXML
    private ComboBox<String> fieldNameComboBox;

    /**
     * Initializes the controller by setting up the TableView and ComboBoxes.
     */

    public void initialize() {
        displayTable();
        displayComboBoxItems();
        tableView.setRowFactory(tv ->
        {
            TableRow<EntityChange<Entity>> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && event.isAltDown() && !row.isEmpty()) {
                    EntityChange<Entity> entityChange = row.getItem();
                    if (entityChange.getEntityToChange() instanceof Ticket ticket) {
                        openTicketWindow(ticket, entityChange.getAgent(), entityChange.getOldValue(), entityChange.getNewValue());
                    }
                    if (entityChange.getEntityToChange() instanceof Agent agent) {
                        openAgentWindow(agent, entityChange.getAgent(), entityChange.getOldValue(), entityChange.getNewValue());
                    }
                }
            });
            return row;
        });
    }

    /**
     * Filters and displays the data in the TableView based on the selected filters.
     */
    public void displayTable() {
        String selectedChangedEntity = changedEntityComboBox.getValue();
        String selectedfieldName = fieldNameComboBox.getValue();
        List<EntityChange<Entity>> filteredChanges = ChangedDataRepository.readChangedDataFromBinaryFile();
        if (selectedChangedEntity != null && !selectedChangedEntity.isEmpty()) {
            filteredChanges = filteredChanges.stream()
                    .filter(entityChange -> entityChange.getEntityToChange().getClass().getSimpleName()
                            .equalsIgnoreCase(selectedChangedEntity))
                    .toList();
        }

        if (selectedfieldName != null && !selectedfieldName.isEmpty()) {
            filteredChanges = filteredChanges.stream()
                    .filter(entityChange -> entityChange.getFieldName()
                            .equalsIgnoreCase(selectedfieldName))
                    .toList();
        }

        agentIdColumn.setCellValueFactory(celldata -> new SimpleStringProperty(String.valueOf(celldata.getValue().getAgent().getId())));
        changedEntityColumn.setCellValueFactory(celldata -> new SimpleStringProperty(
                celldata.getValue().getEntityToChange().getClass().getSimpleName()
        ));
        fieldNameColumn.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        oldValueColumn.setCellValueFactory(new PropertyValueFactory<>("oldValue"));
        newValueColumn.setCellValueFactory(new PropertyValueFactory<>("newValue"));
        changedAtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"))));
        ObservableList<EntityChange<Entity>> changes = FXCollections.observableArrayList(filteredChanges);
        tableView.setItems(changes);
    }

    /**
     * Opens a new window displaying details of the selected Ticket.
     *
     * @param ticket The Ticket whose details are to be displayed.
     * @param agent The Agent who made the changes.
     * @param oldValue The previous value of the ticket field.
     * @param newValue The new value of the ticket field.
     */
    private void openTicketWindow(Ticket ticket, Agent agent, String oldValue, String newValue) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/ChangedEntity_Ticket.fxml"));
            Parent root = loader.load();
            ChangedEntityTicketController controller = loader.getController();
            controller.setTicketDetails(ticket, agent, oldValue, newValue);
            Stage stage = new Stage();
            stage.setTitle("Changed agent details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            logger.error("Error while opening ticket details window - {}", e.getMessage());

        }
    }

    /**
     * Opens a new window displaying details of the selected Agent.
     *
     * @param agent The Agent whose details are to be displayed.
     * @param agentMakingChanges The Agent who made the changes.
     * @param oldValue The previous value of the agent field.
     * @param newValue The new value of the agent field.
     */
    private void openAgentWindow(Agent agent, Agent agentMakingChanges, String oldValue, String newValue) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxprojekt/ChangedEntity_Agent.fxml"));
            Parent root = loader.load();
            ChangedEntityAgentController controller = loader.getController();
            controller.setDetails(agent, agentMakingChanges, oldValue, newValue);
            Stage stage = new Stage();
            stage.setTitle("Changed agent details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the ComboBoxes with items from the ChangedEntity and ChangedFieldName enums.
     */
    private void displayComboBoxItems() {
        changedEntityComboBox.getItems().add("");
        for (ChangedEntity singleChangedEntity : ChangedEntity.values()) {
            changedEntityComboBox.getItems().add(String.valueOf(singleChangedEntity));
        }

        fieldNameComboBox.getItems().add("");
        for (ChangedFieldName singleChangedFieldName : ChangedFieldName.values()) {
            fieldNameComboBox.getItems().add(String.valueOf(singleChangedFieldName));
        }
    }
}
