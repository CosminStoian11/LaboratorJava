package ProiectLaborator.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private final int status;
    private final String type;
    private final String message;

}
