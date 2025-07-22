package summer_project.llm_chatbot.constant;

public enum ProfileField {
    MAJOR("학과명"),
    STUDENT_CODE("학번"),
    NAME("이름"),
    GRADE("학년"),
    USER_STATUS("사용자 상태"),
    TOTAL_SEMESTERS("이수 학기"),
    VERIFIED_SEMESTERS("인증완료 학기"),
    CERTIFICATION("인증여부");

    private final String label;

    ProfileField(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
