package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader("Content-Type", "application/json")
            .build();

    @Value("${openai.api.key}")
    private String apiKey;

    public String ask(String prompt) {
        Map<String, Object> request = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[] {
                        Map.of("role", "user", "content", prompt)
                });

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    try {
                        return ((Map) ((Map) ((java.util.List) response.get("choices")).get(0)).get("message"))
                                .get("content").toString();
                    } catch (Exception e) {
                        return "AI 응답 파싱 실패";
                    }
                })
                .block(); // 동기식 호출
    }

}
