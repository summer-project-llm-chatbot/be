package summer_project.llm_chatbot.dto;

public record ErrorResponseDto(boolean success, int status, String message) {
    public static ErrorResponseDto of(boolean success, int status, String message) {
        return new ErrorResponseDto(success, status, message);
    }
}
