package summer_project.llm_chatbot.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class JwtDto {
    private final String accessToken;
    private final String refreshToken;
}