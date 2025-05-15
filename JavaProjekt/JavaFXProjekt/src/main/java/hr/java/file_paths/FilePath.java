package hr.java.file_paths;

/**
 * The FilePath enum defines constants for various file paths used in the application.
 */
public enum FilePath {
    TICKETS("data/tickets.txt"),
    CUSTOMERS("data/customers.txt"),
    LOGGED_IN_USERS("data/loggedInUsers.txt"),
    RESOLUTION_LOG("data/resolution_log.txt"),
    SUPER_AGENT_FXML_PATH("/org/example/javafxprojekt/MainScene.fxml"),
    NORMAL_AGENT_FXML_PATH("/org/example/javafxprojekt/NormalAgentMainScene.fxml"),
    IMAGE_NOT_FOUND("data/image_not_provided/image_not_found.png");

    private final String path;

    /**
     * Constructs a new {@code FilePath} enum constant with the specified path.
     *
     * @param path the file path associated with the enum constant
     */
    FilePath(String path) {
        this.path = path;
    }

    /**
     * Gets the file path associated with the enum constant.
     *
     * @return the file path as a string
     */
    public String getPath() {
        return path;
    }
}
