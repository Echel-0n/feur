package projet.jee.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException notFoundException,
                                                              WebRequest webRequest){
        HttpStatus hs = HttpStatus.NOT_FOUND;
        ErrorMessage message = new ErrorMessage(hs,
                notFoundException.getMessage());
        return ResponseEntity.status(hs)
                .body(message);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorMessage> alreadyExistException(AlreadyExistException alreadyExistException,
                                                              WebRequest webRequest){
        HttpStatus hs = HttpStatus.CONFLICT;
        ErrorMessage message = new ErrorMessage(hs,
                alreadyExistException.getMessage());
        return ResponseEntity.status(hs)
                .body(message);
    }
}
