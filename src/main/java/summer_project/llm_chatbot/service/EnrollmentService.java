package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import summer_project.llm_chatbot.entity.CourseEntity;
import summer_project.llm_chatbot.entity.EnrollmentEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.repository.CourseRepository;
import summer_project.llm_chatbot.repository.EnrollmentRepository;
import summer_project.llm_chatbot.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public void enrollUserInCourses(String studentId, List<String> curiNos) {
        UserEntity user = userRepository.findByStudentId(studentId)
                                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. 학번: " + studentId));

        for (String curiNo : curiNos) {
            CourseEntity course = courseRepository.findByCuriNo(curiNo)
                                                  .orElseThrow(() -> new IllegalArgumentException("과목 없음: " + curiNo));

            boolean exists = enrollmentRepository.existsByUserAndCourse(user, course);
            if (!exists) {
                EnrollmentEntity e = EnrollmentEntity.builder()
                                                     .user(user)
                                                     .course(course)
                                                     .build();
                enrollmentRepository.save(e);
            }


        }
    }
}
