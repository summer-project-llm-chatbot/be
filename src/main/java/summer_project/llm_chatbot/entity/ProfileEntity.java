package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "profile")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "student_code", unique = true, nullable = false)
    private String studentCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "grade", nullable = false)
    private int grade;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "total_semesters")
    private int totalSemesters;

    @Column(name = "reading_verified_semesters")
    private int readingVerifiedSemesters;

    @Column(name = "reading_certification")
    private String readingCertification;

    // user 1:1 매핑
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
        if (user.getProfile() != this) {
            user.setProfile(this);
        }
    }
}
