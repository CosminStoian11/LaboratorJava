package ProiectLaborator.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {
    private String name;
    private String email;
    private AddressRequest address;
    private Boolean isAdmin;
}
