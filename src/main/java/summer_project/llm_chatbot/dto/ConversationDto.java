package summer_project.llm_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConversationDto {
    private Long conversationId;
    private String title;
    private LocalDateTime startedAt;
}
