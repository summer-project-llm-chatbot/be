package summer_project.llm_chatbot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "course")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "curi_no", nullable = false)
    private String curiNo;

    @Column(name = "curi_nm", nullable = false)
    private String curiNm;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "cdt", nullable = false)
    private float cdt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();

    @Builder(toBuilder = true)
    public CourseEntity(String curiNo, String curiNm, String typeName, float cdt) {
        this.curiNo = curiNo;
        this.curiNm = curiNm;
        this.typeName = typeName;
        this.cdt = cdt;
    }
}
