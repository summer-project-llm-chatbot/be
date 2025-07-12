package summer_project.llm_chatbot.error;

public class ApplicationException extends RuntimeException{
    private final int status;

    protected ApplicationException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static ApplicationException of(String message, int status) {
        return new ApplicationException(message, status);
    }
}
