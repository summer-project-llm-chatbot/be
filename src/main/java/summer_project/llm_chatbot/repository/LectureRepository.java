package summer_project.llm_chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import summer_project.llm_chatbot.entity.LectureEntity;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long> {

    // 개설학과와 이수구분으로 강의 목록 조회
    List<LectureEntity> findByDepartmentAndCourseType(String department, String courseType);
}