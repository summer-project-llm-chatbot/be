package summer_project.llm_chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrawlingLoginDto(
        @JsonProperty("user_id") String userId,
        @JsonProperty("password") String password
) {
    static CrawlingLoginDto of (String userId, String password) {
        return new CrawlingLoginDto(userId, password);
    }
}
