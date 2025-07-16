package summer_project.llm_chatbot.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    LOGIN_FAILED(HttpStatus.FORBIDDEN.value(), "로그인 실패"),

    ;

    public final int status;
    public final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
