package summer_project.llm_chatbot.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final ErrorCode errorCode;

    protected ApplicationException(ErrorCode code) {
        super(code.message);
        this.errorCode = code;
    }

    public static ApplicationException of(ErrorCode code) {
        return new ApplicationException(code);
    }
}
