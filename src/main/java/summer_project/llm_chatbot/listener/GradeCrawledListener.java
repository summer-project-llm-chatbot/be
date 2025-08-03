package summer_project.llm_chatbot.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import summer_project.llm_chatbot.controller.CrawlController;
import summer_project.llm_chatbot.dto.CourseSummaryDto;
import summer_project.llm_chatbot.dto.CrawlingLoginDto;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;
import summer_project.llm_chatbot.event.GradeCrawledEvent;
import summer_project.llm_chatbot.service.CourseService;
import summer_project.llm_chatbot.service.EnrollmentService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GradeCrawledListener {
    private final RestTemplate restTemplate;
    private final CourseService courseService;
    private final CrawlController crawlController;
    private final EnrollmentService enrollmentService;

    @Async
    @EventListener
    public void courseCrawl(GradeCrawledEvent event) {
        CrawlingLoginDto loginDto = event.getLoginDto();

        try{
            CourseSummaryDto[] body = Objects.requireNonNull(
                    crawlController.getGradeSummary(loginDto).getBody()
            );
            List<String> curiNos = Arrays.stream(body)
                                         .map(CourseSummaryDto::curiNo)
                                         .toList();
            courseService.saveCourses(body);
            enrollmentService.enrollUserInCourses(loginDto.userId(), curiNos);
        } catch (Exception e) {
            throw ApplicationException.of(ErrorCode.PROFILE_IO_FAILED);
        }

    }
}
