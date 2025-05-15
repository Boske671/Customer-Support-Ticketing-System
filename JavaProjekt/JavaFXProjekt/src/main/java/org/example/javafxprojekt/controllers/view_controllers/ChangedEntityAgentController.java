package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.entity.Agent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
/**
 * Controller for displaying details of an agent whose information has been changed.
 * This class shows the old and new values of the changed agent details,
 * along with information about the agent making the changes.
 */
public class ChangedEntityAgentController
{

    @FXML
    private TitledPane titledPane;
    @FXML
    private Label changedAgentIDLabel;
    @FXML
    private Label changedAgentFirstNameLabel;
    @FXML
    private Label changedAgentLastNameLabel;
    @FXML
    private Label changedAgentEmailLabel;
    @FXML
    private Label changedAgentTypeLabel;
    @FXML
    private Label agentIDLabel;
    @FXML
    private Label agentFirstNameLabel;
    @FXML
    private Label agentLastNameLabel;
    @FXML
    private Label agentEmailLabel;
    @FXML
    private Label agentTypeLabel;
    @FXML
    private TextArea oldValueTextArea;
    @FXML
    private TextArea newValueTextArea;

    Agent changedAgent;
    Agent agentMakingChange;

    /**
     * Sets the details of the changed agent and the agent who made the changes.
     * It updates the UI components with information about both agents and the old and new values.
     *
     * @param changedAgent The agent whose details were changed.
     * @param agentMakingChanges The agent who made the changes.
     * @param oldValue The old value of the changed field.
     * @param newValue The new value of the changed field.
     */

    public void setDetails(Agent changedAgent, Agent agentMakingChanges, String oldValue, String newValue)
    {
        this.changedAgent = changedAgent;
        this.agentMakingChange = agentMakingChanges;
        titledPane.setText("Agent ID: " + changedAgent.getId());
        changedAgentIDLabel.setText(String.valueOf(changedAgent.getId()));
        changedAgentFirstNameLabel.setText(changedAgent.getFirstName());
        changedAgentLastNameLabel.setText(changedAgent.getLastName());
        changedAgentEmailLabel.setText(changedAgent.getEmail());
        changedAgentTypeLabel.setText(changedAgent.getAgentType().toString());

        agentIDLabel.setText(String.valueOf(agentMakingChanges.getId()));
        agentFirstNameLabel.setText(agentMakingChanges.getFirstName());
        agentLastNameLabel.setText(agentMakingChanges.getLastName());
        agentEmailLabel.setText(agentMakingChanges.getEmail());
        agentTypeLabel.setText(agentMakingChanges.getAgentType().toString());

        oldValueTextArea.setText(oldValue);
        newValueTextArea.setText(newValue);
    }
}
