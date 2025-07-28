package summer_project.llm_chatbot.dto;

import lombok.Getter;

@Getter
public class ChatRequestDto {
    private String studentId;
    private Long conversationId;
    private String question;
}
