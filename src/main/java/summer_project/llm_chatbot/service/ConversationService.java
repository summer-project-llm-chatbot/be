package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summer_project.llm_chatbot.entity.ConversationEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.repository.ConversationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    // new chat 생성
    public ConversationEntity createConversation(UserEntity user) {
        ConversationEntity conversation = ConversationEntity.builder()
                .user(user)
                .startedAt(LocalDateTime.now())
                .build();
        return conversationRepository.save(conversation);
    }

    // 사용자의 대화목록 조회
    public List<ConversationEntity> getConversationsByUserId(Long userId) {
        return conversationRepository.findByUserId(userId);
    }

    // 특정 대화 조회(chat log)
    public ConversationEntity getById(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대화가 존재하지 않습니다."));
    }

    // 채팅 제목 설정->첫 질문으로 제목 설정
    public void setTitleIfEmpty(ConversationEntity conversation, String title) {
        if (conversation.getTitle() == null || conversation.getTitle().isBlank()) {
            conversation.setTitle(title);
            conversationRepository.save(conversation);
        }
    }
}
