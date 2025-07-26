package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "conversation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300) // 제목 길이 제한 설정 (선택)
    private String title;

    // 대화 시작 시간
    private LocalDateTime startedAt;

    // 유저: 다대일 관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // 대화 내용: 일대다 관계 (1:N)
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatLogEntity> chatLogs = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    @PrePersist
    protected void onCreate() {
        if (this.startedAt == null) {
            this.startedAt = LocalDateTime.now();
        }
    }

    @Builder
    public ConversationEntity(UserEntity user, String title, LocalDateTime startedAt) {
        this.user = user;
        this.title = title;
        this.startedAt = startedAt;
    }
}
