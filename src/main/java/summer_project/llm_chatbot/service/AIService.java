package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://878c65298e8d.ngrok-free.app")
            .defaultHeader("Content-Type", "application/json")
            .build();

    // @Value("${external.ai.base-url}")
    // private String aiBaseUrl;

    // private WebClient webClient;

    // @PostConstruct
    // private void initWebClient() {
    // this.webClient = WebClient.builder()
    // .baseUrl(aiBaseUrl)
    // .defaultHeader("Content-Type", "application/json")
    // .build();
    // }

    public String ask(String prompt) {
        Map<String, Object> request = Map.of("question", prompt);

        return webClient.post()
                .uri("/ask")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .map(response -> {
                    try {
                        return response.get("answer").toString(); // FastAPI가 반환하는 answer만 추출
                    } catch (Exception e) {
                        return "FastAPI 응답 파싱 실패";
                    }
                })
                .block(); // 동기식으로 결과 받을 때까지 대기
    }
    // return "[Mock 답변] 질문: " + prompt;}

}
