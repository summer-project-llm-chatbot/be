package summer_project.llm_chatbot.constant;

public enum JwtClaim {
    USER_ID("userId"),
    ROLE("role");

    private final String key;
    JwtClaim(String key) { this.key = key; }
    public String getKey() { return key; }
}
