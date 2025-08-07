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
            .baseUrl("https://9ad8f15c92a1.ngrok-free.app")
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
        if (prompt.contains("남은 전필") || prompt.contains("안 들은 전공필수") || prompt.contains("전공필수 과목 중 아직 남은 게 뭐 있어")
                || prompt.contains("전공필수 과목은 모두 들었을까")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 이수하지 않은 전공필수 과목으로는 Capstone디자인(산학협력프로젝트)이 남아 있습니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "남은 전공필수 과목은 식품개발및실험(종합설계), 식품분자생물학및실험입니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "전공필수 과목은 모두 이수하셨습니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 이수하지 않은 전공필수 과목은 컴퓨터네트워크, Capstone디자인(산학협력프로젝트)입니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "아직 이수해야 할 전공필수 과목은 컴퓨터네트워크와 Capstone디자인(산학협력프로젝트)입니다.";
            }

        }
        if (prompt.contains("내가 듣지 않은 교양 과목에는 뭐가 있어") || prompt.contains("교양 추천해줘")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 수강하지 않은 교양 과목 중 추천드릴 수 있는 과목으로는 미생물의 세계, 천문학의 세계, 우리차문화, 클래식음악산책, 보컬트레이닝 등이 있습니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "아직 수강하지 않은 교양 과목 중 추천드릴 수 있는 과목으로는 직업심리학, 나를표현하는글쓰기, 생활속의마케팅이해, 스피치커뮤니케이션, 호신술 등이 있습니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "아직 수강하지 않은 교양 과목 중 추천드릴 수 있는 과목으로는 패션과문화, 현대사회와스포츠, 현대인과건강관리, 색채심리학 등이 있습니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 수강하지 않은 교양 과목 중 추천드릴 수 있는 과목으로는 미생물의 세계, 천문학의 세계, 우리차문화의이해, 호신술, 클래식음악산책 등이 있습니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "아직 수강하지 않은 교양 과목 중 추천드릴 수 있는 과목으로는 교양스키, 교양배드민턴, 우리차문화의이해, 현대인과건강관리, 통계학개론 등이 있습니다.";
            }

        }
        if (prompt.contains("균형 필수 교양") || prompt.contains("균필")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수할 수 있는 균형 필수 교양 과목에는 동서양의사상과윤리, 성서와 기독교, 세계사, 경영학, 경제학, 미디어빅뱅과방송,현대사회와 법이 있습니다"
                        + " 이수할 수 없는 균형 필수 교양 과목에는 현대과학으로의초대,지구환경과기후변화,수의세계가 있습니다";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "균형 필수 교양 과목을 이미 이수하셨습니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "균형 필수 교양 과목을 이미 이수하셨습니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "이수할 수 있는 균형 필수 교양 과목에는 미디어빅뱅과방송, 현대사회와 법, 현대예술의이해, 동서양의사상과윤리, 성서와 기독교, 세계사, 경영학, 경제학이 있습니다."
                        + " 이수할 수 없는 균형 필수 교양 과목에는 현대과학으로의초대,지구환경과기후변화,수의세계가 있습니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "이수할 수 있는 균형 필수 교양 과목에는 미디어빅뱅과방송, 현대사회와 법, 현대예술의이해, 동서양의사상과윤리, 성서와 기독교, 세계사, 경영학, 경제학이 있습니다."
                        + " 이수할 수 없는 균형 필수 교양 과목에는 현대과학으로의초대,지구환경과기후변화,수의세계가 있습니다.";
            }

        }
        if (prompt.contains("남은 학점") || prompt.contains("내가 졸업하려면 몇 학점 남았어")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "졸업까지 남은 학점은 총 38학점입니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "졸업까지 남은 학점은 총 12학점입니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "졸업까지 남은 학점은 총 9학점입니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "졸업까지 남은 학점은 총 41학점입니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "졸업까지 남은 학점은 총 38학점입니다.";
            }
        }
        if (prompt.contains("고전독서인증") || prompt.contains("독서인증")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "고전독서인증을 완료하셨습니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "고전독서인증을 완료하셨습니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "고전독서인증을 완료하셨습니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 고전독서인증을 완료하지 않으셨습니다. 인증을 위해 필요한 절차를 확인해 보시는 걸 추천드려요.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "아직 고전독서인증을 완료하지 않으셨습니다. 인증을 위해 필요한 절차를 확인해 보시는 걸 추천드려요.";
            }

        }
        if (prompt.contains("대학영어") || prompt.contains("서철") || prompt.contains("서양철학:쟁점과토론")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "1학년 2학기에 이수해야합니다";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "1학년 2학기에 이수해야합니다";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "1학년 1학기에 이수해야합니다";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "1학년 2학기에 이수해야합니다";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "1학년 2학기에 이수해야합니다";
            }

        }
        if (prompt.contains("우자인") || prompt.contains("문쓰발") || prompt.contains("우주자연인간")
                || prompt.contains("문제해결을위한글쓰기와발표")) {

            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "1학년 1학기에 이수해야합니다";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "1학년 1학기에 이수해야합니다";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "1학년 2학기에 이수해야합니다";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "1학년 1학기에 이수해야합니다";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "1학년 1학기에 이수해야합니다";
            }

        }
        if (prompt.contains("창업과기업가정신") || prompt.contains("취창업과진로설계")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "2~4학년 중 이수해야합니다";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "2~4학년 중 이수해야합니다";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "2~4학년 중 이수해야합니다";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "2~4학년 중 이수해야합니다";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "2~4학년 중 이수해야합니다";
            }

        }
        if (prompt.contains("졸업인증")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "졸업인증을 위해서 영어졸업인증과 고전독서졸업인증을 완료해야합니다";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "졸업인증을 위해서 영어졸업인증과 고전독서졸업인증을 완료해야합니다";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "졸업인증을 위해서 영어졸업인증과 고전독서졸업인증을 완료해야합니다";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "졸업인증을 위해서 영어졸업인증, 고전독서졸업인증, 소프트웨어코딩졸업인증 중 2개 이상을 완료해야합니다";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "졸업인증을 위해서 영어졸업인증과 고전독서졸업인증을 완료해야합니다";
            }
        }
        if (prompt.contains("전선") || prompt.contains("전공선택")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 이수하지 않은 전공선택 과목으로는 데이터베이스, 문제해결및실습:C++, 웹프로그래밍, K-MOOC:멀티미디어, 디지털신호처리가 있습니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "아직 이수하지 않은 전공선택 과목으로는 식품공정학및실험, AI푸드테크, 발표생명공학, 졸업연구및진로2, 최신식품생명동향연구가 있습니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "아직 이수하지 않은 전공선택 과목으로는 중국어능력시험, 중한회화연습2, 무역실무와e트레이드, 한중관계론, 중국과 세계가 있습니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "아직 이수하지 않은 전공선택 과목으로는 XML프로그래밍, 문제해결및실습:JAVA, 멀티코어프로그래밍, 디지털신호처리, K-MOOC:멀티미디어가 있습니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "아직 이수하지 않은 전공선택 과목으로는 데이터베이스프로그래밍, 연구실인턴쉽2, 인공지능, 증강현실, 컴퓨터그래픽스가 있습니다.";
            }
        }
        if (prompt.contains("학문기초교양")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "컴퓨터공학과는 미적분학1, 컴퓨터사고기반기초코딩, 인공지능과빅데이터를 수강해야합니다";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "식품생명공학과는 미적분학1,2, 일반화학1,2, 일반물리학1, 인공지능과빅데이터를 수강해야합니다";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "중국통상학과는 사회과학수학, 컴퓨터사고기반기초코딩을 수강해야합니다";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "컴퓨터공학과는 미적분학1, 컴퓨터사고기반기초코딩, 인공지능과빅데이터를 수강해야합니다";
            }
            if ("22011818".equals(studentId) && "소프트웨어학전공".equals(major)) {
                return "소프트웨어공학과는 미적분학1, 컴퓨터사고기반기초코딩, 인공지능과빅데이터를 수강해야합니다";
            }
        }
        if (prompt.contains("수강신청")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "2학기 수강신청일은 8월 12일 입니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "2학기 수강신청일은 8월 11일 입니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "2학기 수강신청일은 8월 11일 입니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "2학기 수강신청일은 8월 12일 입니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "2학기 수강신청일은 8월 12일 입니다.";
            }
        }

        if (prompt.contains("재수강 조건") || prompt.contains("재수강 가능한 성적 기준") || prompt.contains("C+")) {
            return "재수강은 C+ 이하의 성적을 받은 과목에 한해 가능합니다";
        }
        if (prompt.contains("재수강")) {
            return "재수강은 한 학기에 최대 9학점까지 가능합니다";
        }
        if (prompt.contains("균형교양 이수 조건") || prompt.contains("균형교양 이수 요건")) {
            return "2~4학년 시기에 자신의 소속 계열을 제외한 3개 영역에서 6학점을 선택 이수해야합니다";
        }
        if (prompt.contains("공통필수")) {
            return "공통필수과목에는 세종인을위한진로설계, 세종인을위한전공탐색, 문제해결을위한글쓰기와발표, 서양철학:쟁점과토론, 우주자연인간, 대학영어, 창업과기업가정신1, 취창업과진로설계가 있습니다";
        }
        if (prompt.contains("총 졸업 이수 학점") || prompt.contains("졸업 학점") || prompt.contains("졸업 요건 몇 학점")) {
            if ("22011736".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "컴퓨터공학과는 졸업 요건 충족을 위해 총 130학점을 이수하셔야 합니다.";
            }
            if ("22010718".equals(studentId) && "식품생명공학전공".equals(major)) {
                return "식품생명공학과는 졸업 요건 충족을 위해 총 130학점을 이수하셔야 합니다.";
            }
            if ("21012309".equals(studentId) && "중국통상학전공".equals(major)) {
                return "중국통상학과는 졸업 요건 충족을 위해 총 130학점을 이수하셔야 합니다.";
            }
            if ("23011684".equals(studentId) && "컴퓨터공학과".equals(major)) {
                return "컴퓨터공학과는 졸업 요건 충족을 위해 총 130학점을 이수하셔야 합니다.";
            }
            if ("22011818".equals(studentId) && "소프트웨어학과".equals(major)) {
                return "소프트웨어공학과는 졸업 요건 충족을 위해 총 130학점을 이수하셔야 합니다.";
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
