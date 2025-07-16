package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;

    // 사용자와 대화목록 대응
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConversationEntity> conversations = new ArrayList<>();

    @Builder(toBuilder = true)
    public UserEntity(Long id, String studentId) {
        this.id = id;
        this.studentId = studentId;
    }

}
