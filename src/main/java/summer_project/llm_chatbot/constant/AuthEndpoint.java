package summer_project.llm_chatbot.constant;

public enum AuthEndpoint {
    //LOGIN("/jsp/login/loginSSL.jsp", "https://portal.sejong.ac.kr");
    LOGIN("/jsp/login/login_action.jsp", "https://portal.sejong.ac.kr");

    private final String path;
    private final String baseUrl;

    AuthEndpoint(String path, String baseUrl) {
        this.path = path;
        this.baseUrl = baseUrl;
    }

    public String url() {
        return baseUrl + path;
    }
}
