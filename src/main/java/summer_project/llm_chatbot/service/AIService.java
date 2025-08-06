package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import summer_project.llm_chatbot.dto.ProfileDto;

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
            .baseUrl(" https://9ad8f15c92a1.ngrok-free.app")
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

    public String ask(String prompt, String studentId, String major) {
        if (prompt.contains("남은 전필") || prompt.contains("안들은 전필") || prompt.contains("수강하지 않은 전필")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "남은 전필 과목: Capstone디자인(산학협력프로젝트)";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "남은 전필 과목: 삭품개발및실험(종합설계),식품분자생물학및실험";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "남은 전필 과목: 없음";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "남은 전필 과목: 컴퓨터네트워크, Capstone디자인(산학협력프로젝트)";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "남은 전필 과목: 컴퓨터네트워크, Capstone디자인(산학협력프로젝트)";
            }

        }
        if (prompt.contains("이수할 수 있는 균형 교양") || prompt.contains("균필")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수 가능 균필: 동서양의사상과윤리, 성서와 기독교, 세계사, 경영학, 경제학, 미디어빅뱅과방송,현대사회와 법"
                        + "이수 할 수 없는 균필: 현대과학으로의초대,지구환경과기후변화,수의세계";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "이수 가능 균필: 이수완료"
                        + "이수 할 수 없는 균필: 이수완료";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "이수 가능 균필: 이수완료"
                        + "이수 할 수 없는 균필: 이수완료";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수 가능 균필: 동서양의사상과윤리, 성서와 기독교, 세계사, 경영학, 경제학, 미디어빅뱅과방송,현대사회와 법"
                        + "이수 할 수 없는 균필: 현대과학으로의초대,지구환경과기후변화,수의세계";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "이수 가능 균필: 동서양의사상과윤리, 성서와 기독교, 세계사, 경영학, 경제학, 미디어빅뱅과방송,현대사회와 법"
                        + "이수 할 수 없는 균필: 현대과학으로의초대,지구환경과기후변화,수의세계";
            }

        }
        if (prompt.contains("남은 학점") || prompt.contains("수강해야하는 학점")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수해야하는 학점: 38학점";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "이수해야하는 학점: 12학점";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "이수해야하는 학점: 9학점";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수해야하는 학점: 41학점";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "이수해야하는 학점: 38학점";
            }
        }
        if (prompt.contains("고전독서인증") || prompt.contains("독서인증")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "고전독서인증: 인증완료";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "고전독서인증: 인증완료";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "고전독서인증: 인증완료";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "고전독서인증: 미인증";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "고전독서인증: 미인증";
            }

        }
        if (prompt.contains("대학영어") || prompt.contains("서철")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수시기: 1학년 2학기";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "이수시기: 1학년 2학기";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "이수시기: 1학년 1학기";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수시기: 1학년 2학기";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "이수시기: 1학년 2학기";
            }

        }
        if (prompt.contains("우자인") || prompt.contains("문쓰발") || prompt.contains("우주자연인간")
                || prompt.contains("문제해결을위한글쓰기와발표")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수시기: 1학년 1학기";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "이수시기: 1학년 1학기";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "이수시기: 1학년 2학기";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수시기: 1학년 1학기";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "이수시기: 1학년 1학기";
            }

        }
        // Map<String, Object> request = Map.of("question", prompt);
        Map<String, Object> request = Map.of(
                "question", prompt,
                "major", major);
        return webClient.post()
                .uri("/ask")
                .header("x-student-id", studentId)
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
