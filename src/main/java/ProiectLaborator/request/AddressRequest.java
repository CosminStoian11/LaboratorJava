package ProiectLaborator.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String deliveryInstructions;
    private Long customerId;
}
