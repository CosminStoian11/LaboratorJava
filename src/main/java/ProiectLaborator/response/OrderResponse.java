package ProiectLaborator.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private Date orderDate;
    private Double totalAmount;
    private List<OrderItemResponse> items;

}

