package summer_project.llm_chatbot.error;

public class InvalidCredentialError extends ApplicationException{
    private InvalidCredentialError(String message) {
        // HTTP 401 Unauthorized 로 상태 코드를 예시로 설정
        super(message, 401);
    }

    public static InvalidCredentialError of(String message) {
        return new InvalidCredentialError(message);
    }
}
