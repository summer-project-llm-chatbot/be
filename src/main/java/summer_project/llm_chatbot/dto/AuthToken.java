package summer_project.llm_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthToken {
    private final String jsessionId;
    private final String ssoToken;

    // session과 token으로 auth token 만듦
    public static AuthToken of(String jsessionId, String ssoToken) {
        return new AuthToken(
                jsessionId != null ? jsessionId : "",
                ssoToken   != null ? ssoToken   : ""
        );
    }
}
