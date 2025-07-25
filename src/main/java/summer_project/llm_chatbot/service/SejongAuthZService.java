package summer_project.llm_chatbot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import summer_project.llm_chatbot.constant.AuthEndpoint;
import summer_project.llm_chatbot.dto.AuthTokenDto;
import summer_project.llm_chatbot.dto.LoginResponseDto;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;
import summer_project.llm_chatbot.util.HttpUtil;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class SejongAuthZService {
    private static final Pattern LOGIN_RESULT_REGEX =
            Pattern.compile("var\\s+result\\s*=\\s*['\"]([^'\"]+)['\"]");
    private static final String SUCCESS = "OK";

    /**
     * 세종대 학사정보시스템을 통해 로그인 수행
     * @param username 학사정보 시스템 로그인아이디 (학번)
     * @param password 학사정보 시스템 로그인 비밀번호
     * @return 로그인 성공 여부 반환
     */
    public AuthTokenDto login(String username, String password) {
        // 실제 로그인 요청 수행
        ResponseEntity<String> response = requestLogin(username, password);
        // 로그인 성공 여부 확인
        boolean isLoginSuccessful = checkLoginStatus(response.getBody());

        if (!isLoginSuccessful) {
            throw ApplicationException.of(ErrorCode.LOGIN_FAILED);
        }

        // 토큰 생성해서 반환
        // HTTP 응답 헤더에서 쿠키 값 추출
        Map<String, String> cookies = HttpUtil.parseCookies(response.getHeaders());
        String jsessionId = cookies.getOrDefault("JSESSIONID", "");
        String ssoToken = cookies.getOrDefault("ssotoken", "");

        AuthTokenDto token = AuthTokenDto.of(jsessionId, ssoToken);
        return token;
    }

    // 학사정보시스템에 로그인 요청 (HTTP POST 요청)
    // 응답 본문과 쿠키 받아서 반환

    /**
     * 학사정보시스템에 로그인 요청
     * @param username 학사정보시스템 아이디 (학번)
     * @param password 학사정보시스템 비밀번호
     * @return 로그인 요청 결과가 반환된다. body에는 로그인 응답 결과가 HTML로 반환됨
     */
    public ResponseEntity<String> requestLogin(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        String loginUrl = AuthEndpoint.LOGIN.url();

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("id", username);
        form.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Host", "portal.sejong.ac.kr");
        headers.set("Referer", "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        return restTemplate.exchange(
                loginUrl,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    /**
     * 응답 HTML을 통해 로그인 성공 여부 확인
     * @param body HTML 응답
     * @return
     */
    private boolean checkLoginStatus(String body){
        // 응답 HTML 에 포함된 JS Val 변수값을 파싱하여 확인
        // 성공인 경우 result에 "OK" 가 명시되어 있음
        // 외부 응답 구조가 변경될 경우 정규식 수정해야함
        // TODO: 외부 응답 구조가 변경된 경우의 예외 처리는 되지 않음, @yejoo
        Matcher matcher = LOGIN_RESULT_REGEX.matcher(body);
        if (!matcher.find()) {
            throw ApplicationException.of(ErrorCode.LOGIN_FAILED);
        }
        String result = matcher.group(1);
        return SUCCESS.equals(result);
    }
}
