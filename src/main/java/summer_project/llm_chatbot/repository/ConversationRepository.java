package summer_project.llm_chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summer_project.llm_chatbot.entity.ConversationEntity;

public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
    List<ConversationEntity> findByUserId(Long userId);
}
