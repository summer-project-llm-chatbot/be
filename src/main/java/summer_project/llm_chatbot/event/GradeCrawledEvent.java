package summer_project.llm_chatbot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import summer_project.llm_chatbot.dto.CrawlingLoginDto;

@Getter
@RequiredArgsConstructor
public class GradeCrawledEvent {
    private final CrawlingLoginDto loginDto;
}
