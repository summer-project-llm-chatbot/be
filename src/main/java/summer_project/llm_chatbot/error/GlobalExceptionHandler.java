package summer_project.llm_chatbot.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import summer_project.llm_chatbot.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().status)
                .body(ErrorResponseDto.of(false, e.getErrorCode().status.value(), e.getErrorCode().message));
    }
}
