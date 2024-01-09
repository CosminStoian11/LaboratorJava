package ProiectLaborator.mapper;

import ProiectLaborator.entity.Customer;
import ProiectLaborator.request.CustomerRequest;
import ProiectLaborator.response.CustomerResponse;

public class CustomerMapper {
    public static CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setIsAdmin(customer.isAdmin());
        response.setAddress(AddressMapper.toResponse(customer.getAddress()));
        return response;
    }

    public static Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(AddressMapper.toEntity(request.getAddress()))
                .build();
    }

    public static CustomerRequest toRequestFromEntity(Customer customer) {
        return CustomerRequest.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .isAdmin(customer.isAdmin())
                .address(AddressMapper.toRequestFromEntity(customer.getAddress()))
                .build();
    }
}