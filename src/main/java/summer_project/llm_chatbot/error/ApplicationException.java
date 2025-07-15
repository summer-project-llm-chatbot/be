package summer_project.llm_chatbot.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final int status;
    private final String message;

    protected ApplicationException(int status) {
        this.status = status;
        this.message = "";
    }

    protected ApplicationException(String message, int status) {
        super(message);
        this.status = status;
        this.message = message;
    }

    protected ApplicationException(String message, int status, Throwable cause, int status1) {
        super(message, cause);
        this.status = status1;
        this.message = message;
    }

    public static ApplicationException of(String message, int status) {
        return new ApplicationException(message, status);
    }

    public static ApplicationException of(int status) {
        return new ApplicationException(status);
    }
}
