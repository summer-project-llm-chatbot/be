package summer_project.llm_chatbot.constant;

public enum LoginHeader {
    HOST("Host", "portal.sejong.ac.kr"),
    CONTENT_TYPE("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
    ACCEPT("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"),
    REFERER("Referer", AuthEndpoint.LOGIN.url()),
    USER_AGENT("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)…");

    private final String headerName;
    private final String headerValue;

    LoginHeader(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getHeaderValue() {
        return headerValue;
    }
}
