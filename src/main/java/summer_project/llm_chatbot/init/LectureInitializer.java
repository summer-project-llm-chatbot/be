package summer_project.llm_chatbot.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import summer_project.llm_chatbot.repository.LectureRepository;
import summer_project.llm_chatbot.service.LectureService;

@Component
@RequiredArgsConstructor
public class LectureInitializer implements ApplicationRunner {
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (lectureRepository.count() == 0) {
            lectureService.saveLecturesFromExcel();
            System.out.println("엑셀 데이터가 자동으로 로드되었습니다.");
        } else {
            System.out.println("강의 데이터가 이미 존재하여 엑셀 로드를 건너뜁니다.");
        }
    }
}
