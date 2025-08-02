package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import summer_project.llm_chatbot.dto.CourseSummaryDto;
import summer_project.llm_chatbot.entity.CourseEntity;
import summer_project.llm_chatbot.repository.CourseRepository;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public void saveCourses(CourseSummaryDto[] summaries){
        for (CourseSummaryDto dto : summaries) {
            courseRepository.findByCuriNo(dto.curiNo())
                            .orElseGet(() -> courseRepository.save(
                                    CourseEntity.builder()
                                                .curiNo(dto.curiNo())
                                                .curiNm(dto.curiNm())
                                                .typeName(dto.typeName())
                                                .cdt(dto.cdt())
                                                .build()
                            ));
        }
    }
}
