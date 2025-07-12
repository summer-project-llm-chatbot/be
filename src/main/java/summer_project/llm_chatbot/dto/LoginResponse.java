package summer_project.llm_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private final boolean success;
    private final AuthToken token;
}
