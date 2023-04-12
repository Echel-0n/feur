package projet.jee.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus error;
    private String message;
    private String information;

    public ErrorMessage(HttpStatus httpStatus, String message) {
        this.error = httpStatus;
        this.message = message;
        this.information = "";
    }
}