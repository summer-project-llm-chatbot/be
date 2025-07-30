package summer_project.llm_chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CourseSummary(
        @JsonProperty("curi_no") String curiNo,
        @JsonProperty("curi_nm") String curiNm,
        @JsonProperty("type_name") String typeName,
        @JsonProperty("cdt") float cdt) {
    static CourseSummary of(String curiNo,
                            String curiNm,
                            String typeName,
                            float cdt) {
        return new CourseSummary(curiNo, curiNm, typeName, cdt);
    }
}
