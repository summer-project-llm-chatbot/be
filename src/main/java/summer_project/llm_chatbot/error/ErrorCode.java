package summer_project.llm_chatbot.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    LOGIN_FAILED(HttpStatus.FORBIDDEN, "로그인 실패"),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "잘못된 토큰 포맷"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    PROFILE_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "프로필 요청 실패"),
    PROFILE_IO_FAILED(HttpStatus.BAD_GATEWAY, "프로필 가져오기 실패"),
    PROFILE_PARSE_FAILED(HttpStatus.BAD_GATEWAY,"프로필 파싱 오류"),
    CRAWLING_FAILED(HttpStatus.FORBIDDEN, "크롤링 실패"),
    ;

    public final HttpStatus status;
    public final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
