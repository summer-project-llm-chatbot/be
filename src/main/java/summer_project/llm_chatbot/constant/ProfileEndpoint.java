package summer_project.llm_chatbot.constant;

public enum ProfileEndpoint {
    PROFILE("/classic/reading/status.do", "https://classic.sejong.ac.kr");

    private final String path;
    private final String baseUrl;

    ProfileEndpoint(String path, String baseUrl) {
        this.path = path;
        this.baseUrl = baseUrl;
    }

    public String url() {
        return baseUrl + path;
    }
}
