package project.dailysup.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;
    private String detail;

    public ExceptionResponse(String message, String detail) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.detail = detail;
    }

    public ExceptionResponse(Exception e, WebRequest request, boolean desc){
        this.timestamp = LocalDateTime.now();
        this.message = e.getMessage();
        this.detail = request.getDescription(desc);
    }

}
