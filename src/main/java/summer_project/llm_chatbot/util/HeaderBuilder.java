package summer_project.llm_chatbot.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import summer_project.llm_chatbot.constant.LoginHeader;

public class HeaderBuilder {
    public static HttpHeaders loginHeaders() {
        HttpHeaders headers = new HttpHeaders();
        for (LoginHeader lh : LoginHeader.values()) {
            headers.set(lh.getHeaderName(), lh.getHeaderValue());
        }
        // 명시적으로 Form URL Encoded
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
