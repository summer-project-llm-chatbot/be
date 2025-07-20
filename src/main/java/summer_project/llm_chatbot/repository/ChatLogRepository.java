package summer_project.llm_chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summer_project.llm_chatbot.entity.ChatLogEntity;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLogEntity, Long> {
    List<ChatLogEntity> findByConversationId(Long conversationId);
}
