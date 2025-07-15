package summer_project.llm_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private final boolean success;
    private final AuthTokenDto token;
}
