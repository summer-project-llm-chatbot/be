package summer_project.llm_chatbot.dto;

public record JwtDto(String accessToken, String refreshToken) {
    public static JwtDto of(String accessToken, String refreshToken) {
        return new JwtDto(accessToken, refreshToken);
    }
}