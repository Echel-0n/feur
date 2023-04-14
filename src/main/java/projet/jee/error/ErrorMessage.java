package projet.jee.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private HttpStatus error;
    private String message;
    public ErrorMessage(HttpStatus httpStatus, String message) {
        this.error = httpStatus;
        this.message = message;
    }
}