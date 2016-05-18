package screens;

public enum Screens {
    MAIN("Main"),
    LOGIN("Login"),
    REGISTER("Register"),
    WORKSPACE("Workspace"),
    NOTIFICATION("Notification"),
    NOT_FOUND("Error404"),
    SOCKET_FAILED("ErrorSocketFailed");

    private final String view;
    private final String suffix = "FXML";
    private final String ext = ".fxml";

    private Screens(String view) {
        this.view = view ;
    }

    @Override
    public String toString() {
        return this.suffix + this.view + this.ext;
    }
}