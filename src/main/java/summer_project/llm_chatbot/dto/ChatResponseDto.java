package summer_project.llm_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatResponseDto {
    private String question;
    private String answer;
    private LocalDateTime createdAt;
    private Long conversationId;
}
