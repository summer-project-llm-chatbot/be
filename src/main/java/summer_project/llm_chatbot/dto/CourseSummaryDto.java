package summer_project.llm_chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CourseSummaryDto(
        @JsonProperty("curi_no") String curiNo,
        @JsonProperty("curi_nm") String curiNm,
        @JsonProperty("type_name") String typeName,
        @JsonProperty("cdt") float cdt) {
    static CourseSummaryDto of(String curiNo,
                               String curiNm,
                               String typeName,
                               float cdt) {
        return new CourseSummaryDto(curiNo, curiNm, typeName, cdt);
    }
}
