package summer_project.llm_chatbot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import summer_project.llm_chatbot.constant.AuthEndpoint;
import summer_project.llm_chatbot.dto.AuthToken;
import summer_project.llm_chatbot.dto.LoginResponse;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.InvalidCredentialError;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class SejongAuthZService {
    private static final Pattern LOGIN_RESULT_REGEX =
            Pattern.compile("var\\s+result\\s*=\\s*['\"]([^'\"]+)['\"]");
    private static final String SUCCESS = "OK";

    // 세종대 학사정보시스템을 통해 로그인 수행
    // 로그인 성공 여부 및 인증 토큰 정보 반환
    public LoginResponse login(String username, String password) {
        // 실제 로그인 요청 수행
        ResponseEntity<String> response = requestLogin(username, password);
        // 로그인 성공 여부 확인
        boolean isLoginSuccessful = checkLoginStatus(response.getBody());

        if (!isLoginSuccessful) {
            throw InvalidCredentialError.of("로그인에 실패");
        }

        // 토큰 생성해서 반환
        // HTTP 응답 헤더에서 쿠키 값 추출
        String jsessionId = extractCookie(response.getHeaders(), "JSESSIONID");
        String ssoToken = extractCookie(response.getHeaders(), "ssotoken");

        AuthToken token = AuthToken.of(jsessionId, ssoToken);
        return new LoginResponse(true, token);
    }

    // 학사정보시스템에 로그인 요청 (HTTP POST 요청)
    // 응답 본문과 쿠키 받아서 반환
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

    // 응답 HTML에 OK 가 있는지 확인. 없으면 연결 오류
    // 응답 HTML에서 로그인 성공 여부 확인 후 반환
    private boolean checkLoginStatus(String body){
        Matcher matcher = LOGIN_RESULT_REGEX.matcher(body);
        if (!matcher.find()) {
            throw ApplicationException.of("올바르지 않은 응답 유형", 404);
        }
        String result = matcher.group(1);
        return SUCCESS.equals(result);
    }

    // HTTP 응답 헤더에서 쿠키 값 추출
    // 세션 ID와 SSO 토큰 반환
    private String extractCookie(HttpHeaders headers, String name) {
        List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);
        if (cookies == null) {
            return "";
        }
        return cookies.stream()
                      .filter(cookie -> cookie.startsWith(name + "="))
                      .map(cookie -> {
                          String[] parts = cookie.split(";", 2);
                          return parts[0].substring(name.length() + 1);
                      })
                      .findFirst()
                      .orElse("");
    }
}
