package summer_project.llm_chatbot.dto;

import lombok.Getter;

@Getter
public class ChatRequestDto {
    private Long userId;
    private Long conversationId;
    private String question;
}
