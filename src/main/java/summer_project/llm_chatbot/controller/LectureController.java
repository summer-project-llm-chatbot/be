package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import summer_project.llm_chatbot.dto.LectureDto;
import summer_project.llm_chatbot.service.LectureService;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    // 학과 + 이수구분으로 강의 목록 조회
    @GetMapping
    public List<LectureDto> getLecturesByDepartmentAndCourseType(
            @RequestParam String department,
            @RequestParam String courseType) {

        return lectureService.getLecturesByDepartmentAndCourseType(department, courseType);
    }
}
