package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summer_project.llm_chatbot.dto.LectureDto;
import summer_project.llm_chatbot.entity.LectureEntity;
import summer_project.llm_chatbot.repository.LectureRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    // 특정 학과와 이수구분으로 강의 조회
    public List<LectureDto> getLecturesByDepartmentAndCourseType(String department, String courseType) {
        List<LectureEntity> lectures = lectureRepository.findByDepartmentAndCourseType(department, courseType);

        return lectures.stream()
                .map(lecture -> LectureDto.builder()
                        .department(lecture.getDepartment())
                        .subjectName(lecture.getSubjectName())
                        .courseType(lecture.getCourseType())
                        .grade(lecture.getGrade())
                        .credit(lecture.getCredit())
                        .build())
                .collect(Collectors.toList());
    }

    public void saveLecturesFromExcel() {
        try {
            lectureRepository.deleteAll();

            InputStream is = new ClassPathResource("data/통합_최종_강의시간표.xlsx").getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 헤더 4줄 가정
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                String department = getCellStringValue(row.getCell(0));
                String subjectName = getCellStringValue(row.getCell(1));
                String courseType = getCellStringValue(row.getCell(2));
                int grade = (int) getCellNumericValue(row.getCell(3));
                int credit = (int) getCellNumericValue(row.getCell(4));

                if (department.isBlank() || subjectName.isBlank())
                    continue;

                LectureEntity lecture = LectureEntity.builder()
                        .department(department)
                        .subjectName(subjectName)
                        .courseType(courseType)
                        .grade(grade)
                        .credit(credit)
                        .build();

                lectureRepository.save(lecture);
            }

            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("엑셀 파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    // 헬퍼 메서드들
    private String getCellStringValue(Cell cell) {
        return (cell == null) ? "" : cell.toString().trim();
    }

    private double getCellNumericValue(Cell cell) {
        if (cell == null)
            return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                try {
                    yield Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    yield 0;
                }
            }
            default -> 0;
        };
    }

}
