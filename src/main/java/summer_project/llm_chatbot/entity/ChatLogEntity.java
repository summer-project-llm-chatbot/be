package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "chat_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 대화(Conversation)에 속하는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    // 질문 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    // 응답 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    // 생성 시각
    private LocalDateTime createdAt;

    @Builder
    public ChatLogEntity(ConversationEntity conversation, String question, String answer, LocalDateTime createdAt) {
        this.conversation = conversation;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
    }
}
