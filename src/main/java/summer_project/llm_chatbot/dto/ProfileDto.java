package summer_project.llm_chatbot.dto;

public record ProfileDto(String major,
                         String studentCode,
                         String name,
                         int grade,
                         String userStatus,
                         int totalSemesters,
                         int readingVerifiedSemesters,
                         String readingCertification) {
    public static ProfileDto of(String major,
                                String studentCode,
                                String name,
                                int grade,
                                String userStatus,
                                int totalSemesters,
                                int readingVerifiedSemesters,
                                String readingCertification){
        return new ProfileDto(major, studentCode, name, grade, userStatus,
                              totalSemesters, readingVerifiedSemesters, readingCertification);
    }
}
