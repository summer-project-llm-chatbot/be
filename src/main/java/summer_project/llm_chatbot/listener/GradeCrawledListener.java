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

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GradeCrawledListener {
    private final RestTemplate restTemplate;
    private final CourseService courseService;
    private final CrawlController crawlController;

    @Async
    @EventListener
    public void courseCrawl(GradeCrawledEvent event) {
        CrawlingLoginDto loginDto = event.getLoginDto();

        try{
            ResponseEntity<CourseSummaryDto[]> summaries = crawlController.getGradeSummary(loginDto);
            courseService.saveCourses(Objects.requireNonNull(summaries.getBody()));
        } catch (Exception e) {
            throw ApplicationException.of(ErrorCode.CRAWLING_FAILED);
        }

    }
}
