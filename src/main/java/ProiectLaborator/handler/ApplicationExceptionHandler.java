package ProiectLaborator.handler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ProiectLaborator.exception.BaseRuntimeException;
import ProiectLaborator.exception.CategoryNotFound;
import ProiectLaborator.exception.CustomerNotFound;
import ProiectLaborator.exception.BookNotFound;
import ProiectLaborator.response.ErrorResponse;



@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler({CustomerNotFound.class, BookNotFound.class, CategoryNotFound.class})
    public ResponseEntity<ErrorResponse> handle(final BaseRuntimeException exception){
        final var response = new ErrorResponse(NOT_FOUND.value(),NOT_FOUND.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(response);
    }
}
