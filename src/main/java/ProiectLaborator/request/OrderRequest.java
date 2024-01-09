package ProiectLaborator.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequest {

    private Long customerId;
    private List<OrderItemRequest> items;

}