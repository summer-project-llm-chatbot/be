package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summer_project.llm_chatbot.entity.ChatLogEntity;
import summer_project.llm_chatbot.entity.ConversationEntity;
import summer_project.llm_chatbot.repository.ChatLogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogService {

    private final ChatLogRepository chatLogRepository;
    private final ConversationService conversationService;
    private final AIService aiService;

    public ChatLogEntity handleChat(Long conversationId, String question, String studentId, String major) {
        // 1. 대화 세션 가져오기
        ConversationEntity conversation = conversationService.getById(conversationId);

        // 2. AI 응답 받기
        String answer = aiService.ask(question, studentId, major);

        // 3. 대화 제목 설정 (첫 질문일 경우)
        conversationService.setTitleIfEmpty(conversation, question);

        // 4. 질문/응답 저장
        ChatLogEntity log = ChatLogEntity.builder()
                .conversation(conversation)
                .question(question)
                .answer(answer)
                .createdAt(LocalDateTime.now())
                .build();

        return chatLogRepository.save(log);
    }

    public ChatLogEntity save(ConversationEntity conversation, String question, String answer) {
        ChatLogEntity log = ChatLogEntity.builder()
                .conversation(conversation)
                .question(question)
                .answer(answer)
                .createdAt(LocalDateTime.now())
                .build();

        return chatLogRepository.save(log);
    }

    public List<ChatLogEntity> getLogsByConversationId(Long conversationId) {
        return chatLogRepository.findByConversationId(conversationId);
    }
}
