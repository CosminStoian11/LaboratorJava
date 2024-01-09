package ProiectLaborator.mapper;

import ProiectLaborator.entity.Address;
import ProiectLaborator.request.AddressRequest;
import ProiectLaborator.response.AddressResponse;

import static java.util.Objects.isNull;


public class AddressMapper {
    public static AddressResponse toResponse(Address Address) {
        AddressResponse response = new AddressResponse();

        response.setId(Address.getId());
        response.setStreet(Address.getStreet());
        response.setCity(Address.getCity());
        response.setPostalCode(Address.getPostalCode());
        response.setCountry(Address.getCountry());
        response.setDeliveryInstructions(Address.getDeliveryInstructions());

        return response;
    }

    public static Address toEntity(AddressRequest request) {
        if (isNull(request)) {
            return null;
        }
        Address address = new Address();

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setDeliveryInstructions(request.getDeliveryInstructions());

        return address;
    }

    public static AddressRequest toRequestFromEntity(Address address) {
        return AddressRequest.builder()
                .city(address.getCity())
                .country(address.getCountry())
                .street(address.getStreet())
                .deliveryInstructions(address.getDeliveryInstructions())
                .postalCode(address.getPostalCode())
                .customerId(address.getId())
                .build();
    }
}


