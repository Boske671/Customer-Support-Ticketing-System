package hr.java.utils;

import javafx.scene.control.Alert;

/**
 * The AlertMaker class is used to display customizable alert dialogs in a JavaFX application.
 * The class allows you to set the title, header, content, and type of the alert.
 */
public class AlertMaker
{
    private final String title;
    private final String header;
    private final String content;
    private final String type;

    /**
     * Constructs an {@code AlertMaker} instance with the specified title, header, content, and alert type.
     *
     * @param title   the title of the alert dialog
     * @param header  the header text displayed in the alert dialog
     * @param content the content text displayed in the alert dialog
     * @param type    the type of the alert
     */
    public AlertMaker(String title, String header, String content, String type)
    {
        this.title = title;
        this.header = header;
        this.content = content;
        this.type = type;
    }

    /**
     * Displays the alert dialog with the specified title, header, content, and type.
     * The alert is displayed and will wait for the user to close it.
     */
    public void displayAlert()
    {
        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}