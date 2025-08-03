package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summer_project.llm_chatbot.dto.CourseSummaryDto;
import summer_project.llm_chatbot.dto.ProfileDto;
import summer_project.llm_chatbot.entity.CourseEntity;
import summer_project.llm_chatbot.entity.EnrollmentEntity;
import summer_project.llm_chatbot.entity.ProfileEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.repository.CourseRepository;
import summer_project.llm_chatbot.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public ProfileDto getProfile(String studentId){
        UserEntity user = userRepository.findByStudentId(studentId)
                                        .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다: " + studentId));

        ProfileEntity profile = user.getProfile();

        return ProfileDto.of(
                profile.getMajor(),
                profile.getStudentCode(),
                profile.getName(),
                profile.getGrade(),
                profile.getUserStatus(),
                profile.getTotalSemesters(),
                profile.getReadingVerifiedSemesters(),
                profile.getReadingCertification()
        );
    }

    public List<CourseSummaryDto> getCourses(String studentId){
        UserEntity user = userRepository.findByStudentId(studentId)
                                        .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다: " + studentId));

        return user.getEnrollments().stream()
                   .map(e -> {
                       CourseEntity c = e.getCourse();
                       return CourseSummaryDto.of(
                               c.getCuriNo(),
                               c.getCuriNm(),
                               c.getTypeName(),
                               c.getCdt()
                       );
                   })
                   .toList();
    }
}
