package summer_project.llm_chatbot.dto;

public record LoginRequestDto(String id, String password) {
    static LoginRequestDto of(String id, String password) {
        return new LoginRequestDto(id, password);
    }
}
