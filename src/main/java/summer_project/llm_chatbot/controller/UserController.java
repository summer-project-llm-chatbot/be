package summer_project.llm_chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summer_project.llm_chatbot.dto.CourseSummaryDto;
import summer_project.llm_chatbot.dto.ProfileDto;
import summer_project.llm_chatbot.service.UserQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserQueryService userQueryService;

    @Operation(
            summary = "프로필 가져오기 API",
            description =
                    """
                    학생의 프로필 정보를 가져옵니다.
                    
                    `studnetId`는 학사정보시스템 포털의 id로 기본적으로 학번과 동일합니다.
                    """
    )
    @GetMapping("/{studentId}/profile")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable String studentId) {
        return ResponseEntity.ok(userQueryService.getProfile(studentId));
    }

    @Operation(
            summary = "수강기록 가져오기 API",
            description =
                    """
                    학생의 수강기록을 가져옵니다.
                    
                    `studentId`는 학사정보시스템 포털의 id로 기본적으로 학번과 동일합니다.
                    """
    )
    @GetMapping("/{studentId}/course")
    public ResponseEntity<List<CourseSummaryDto>> getCompletedCourses(@PathVariable String studentId) {
        return ResponseEntity.ok(userQueryService.getCourses(studentId));
    }
}
