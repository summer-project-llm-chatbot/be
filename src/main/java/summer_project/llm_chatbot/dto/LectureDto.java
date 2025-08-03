package summer_project.llm_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureDto {
    private String department;
    private String subjectName;
    private String courseType;
    private int grade;
    private int credit;
}
