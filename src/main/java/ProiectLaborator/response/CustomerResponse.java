package ProiectLaborator.response;

import ProiectLaborator.entity.Address;
import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private AddressResponse address;
    private Boolean isAdmin;

}
