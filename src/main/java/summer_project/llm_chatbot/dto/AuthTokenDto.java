package summer_project.llm_chatbot.dto;

import summer_project.llm_chatbot.constant.CookieName;

import java.util.Map;

public record AuthTokenDto(String jsessionId, String ssoToken) {
    // session과 token으로 auth token 만듦
    public static AuthTokenDto of(String jsessionId, String ssoToken) {
        return new AuthTokenDto(
                jsessionId != null ? jsessionId : "",
                ssoToken != null ? ssoToken : ""
        );
    }
    /**
     * "JSESSIONID=...; ssotoken=..." 형태로 HttpUtil 에 전달할 수 있도록 Map 으로 변환
     */
    public Map<String, String> toMap() {
        return Map.of(
                CookieName.PO_JSESSION.getValue(), this.jsessionId,
                CookieName.SSO_TOKEN.getValue(), this.ssoToken
        );
    }
}
