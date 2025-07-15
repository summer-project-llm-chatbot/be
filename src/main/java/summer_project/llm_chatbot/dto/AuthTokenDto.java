package summer_project.llm_chatbot.dto;

public record AuthTokenDto(String jsessionId, String ssoToken) {
    // session과 token으로 auth token 만듦
    public static AuthTokenDto of(String jsessionId, String ssoToken) {
        return new AuthTokenDto(
                jsessionId != null ? jsessionId : "",
                ssoToken != null ? ssoToken : ""
        );
    }
}
