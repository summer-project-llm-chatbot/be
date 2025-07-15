package summer_project.llm_chatbot.dto;

public record LoginResponseDto(boolean success, AuthTokenDto token) {
    static LoginResponseDto of(boolean isSuccess, AuthTokenDto token) {
        return new LoginResponseDto(isSuccess, token);
    }
}
