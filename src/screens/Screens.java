package screens;

public enum Screens {
    APP_PARAMETERS("AppParameters"),
    MAIN("Main"),
    LOGIN("Login"),
    RELOADER("Reloader"),
    REGISTER("Register"),
    WORKSPACE("Workspace"),
    PROFIL("Profil"),
    NOTIFICATION("Notification"),
    NOT_FOUND("Error404"),
    SOCKET_RETRY("SocketRetry"),
     CREATE("Create"),
      VIEW("EquationView"),
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