package summer_project.llm_chatbot.dto;

public record LoginResponseDto(boolean success, JwtDto jwt) {
    public static LoginResponseDto of(boolean isSuccess, JwtDto jwt) {
        return new LoginResponseDto(isSuccess, jwt);
    }
}
