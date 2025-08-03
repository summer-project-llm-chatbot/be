package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "lecture")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 개설학과전공
    @Column(nullable = false)
    private String department;

    // 교과목명
    private String subjectName;

    // 이수구분
    private String courseType;

    // 학년
    private int grade;

    // 학점
    private int credit;

}
