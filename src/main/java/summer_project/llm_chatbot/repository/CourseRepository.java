package summer_project.llm_chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summer_project.llm_chatbot.entity.CourseEntity;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByCuriNo(String curiNo);
}
