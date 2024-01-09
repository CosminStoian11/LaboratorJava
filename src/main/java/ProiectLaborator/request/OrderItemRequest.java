package ProiectLaborator.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemRequest {

    private Long bookId;
    private Integer quantity;

}