package org.example.javafxprojekt.controllers.view_controllers;

import hr.java.file_paths.FilePath;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Controller for displaying logs from the resolution log file.
 * This controller reads the contents of the log file and updates the UI
 * periodically to display the latest logs.
 */
public class ResolutionLogController {
    @FXML
    private TextArea textAreaLogs;

    /**
     * Initializes the controller, loads the data from the log file,
     * and sets up a timeline to refresh the displayed log every 2 seconds.
     */
    public void initialize() {
        loadDataFromFile();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event2 -> loadDataFromFile()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Reads the content of the resolution log file and displays it in the TextArea.
     * The file is read line by line, the lines are reversed (to show the latest logs at the top),
     * and then concatenated to display the full log content.
     */
    @FXML
    private void loadDataFromFile() {
        StringBuilder content = new StringBuilder();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath.RESOLUTION_LOG.getPath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.error("Error reading file {} ", e.getMessage());
        }
        Collections.reverse(lines);
        for (String line : lines) {
            content.append(line).append("\n");
        }
        textAreaLogs.setText(content.toString());
    }

}
