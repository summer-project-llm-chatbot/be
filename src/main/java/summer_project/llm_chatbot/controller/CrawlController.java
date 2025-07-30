package summer_project.llm_chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import summer_project.llm_chatbot.dto.CourseSummary;
import summer_project.llm_chatbot.dto.CrawlingLoginDto;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CrawlController {
    private final RestTemplate restTemplate;

    @Operation(
            summary = "학사정보 수강기록 가져오기 API",
            description =
                    """
                    학생의 학사정보 수강기록을 가져옵니다.
                    
                    `id`는 학사정보시스템 포털의 id로 기본적으로 학번과 동일합니다.
                    
                    `password`는 학사정보시스템 포털의 비밀번호입니다.
                    """
    )
    @PostMapping("/grade-summary")
    public ResponseEntity<CourseSummary[]> getGradeSummary(@RequestBody CrawlingLoginDto loginDto) {
        // 1) Python API URL
        String crawlerUrl = "http://localhost:8000/crawl";

        // 2) HTTP 요청 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "Mozilla/5.0");
        HttpEntity<CrawlingLoginDto> request = new HttpEntity<>(loginDto, headers);

        // 3) Python 서버에 POST 요청
        try {
            ResponseEntity<CourseSummary[]> response = restTemplate.postForEntity(
                    crawlerUrl,
                    request,
                    CourseSummary[].class
            );
            return ResponseEntity.ok(response.getBody());

        } catch (HttpClientErrorException e) {
            // 400 Bad Request 등 클라이언트 오류
            String body = e.getResponseBodyAsString();
            if (body.contains("로그인")) {
                throw ApplicationException.of(ErrorCode.LOGIN_FAILED);
            }
            throw ApplicationException.of(ErrorCode.PROFILE_REQUEST_FAILED);

        } catch (HttpServerErrorException e) {
            // 500 오류 등 서버 오류
            throw ApplicationException.of(ErrorCode.PROFILE_IO_FAILED);

        } catch (Exception e) {
            // 그 외 예외
            throw ApplicationException.of(ErrorCode.PROFILE_PARSE_FAILED);
        }
    }
}
