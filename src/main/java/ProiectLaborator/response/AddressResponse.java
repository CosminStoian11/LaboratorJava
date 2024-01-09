package ProiectLaborator.response;

import lombok.Data;

@Data
public class AddressResponse {
    private Long id;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String deliveryInstructions;
}
