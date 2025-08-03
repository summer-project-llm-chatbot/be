package summer_project.llm_chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summer_project.llm_chatbot.entity.CourseEntity;
import summer_project.llm_chatbot.entity.EnrollmentEntity;
import summer_project.llm_chatbot.entity.UserEntity;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    boolean existsByUserAndCourse(UserEntity user, CourseEntity course);
}
