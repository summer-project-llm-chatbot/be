package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

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

    // profile 1:1 매핑
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    // course n:m 매핑
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();

    @Builder(toBuilder = true)
    public UserEntity(Long id, String studentId) {
        this.id = id;
        this.studentId = studentId;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
        if (profile.getUser() != this) {
            profile.setUser(this);
        }
    }

    public void enroll(CourseEntity course) {
        EnrollmentEntity e = EnrollmentEntity.builder()
                                             .user(this)
                                             .course(course)
                                             .build();
        enrollments.add(e);
        course.getEnrollments().add(e);
    }
}
