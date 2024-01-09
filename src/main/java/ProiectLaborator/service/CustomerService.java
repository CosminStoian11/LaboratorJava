package ProiectLaborator.service;

import ProiectLaborator.repository.CustomerRepository;
import ProiectLaborator.entity.Customer;
import ProiectLaborator.mapper.CustomerMapper;
import ProiectLaborator.request.CustomerRequest;
import ProiectLaborator.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.toEntity(request);
        customer = customerRepository.save(customer);
        return CustomerMapper.toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for id: " + id));
        return CustomerMapper.toResponse(customer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for id: " + id));

        Customer updatedCustomer = CustomerMapper.toEntity(request);
        updatedCustomer.setId(existingCustomer.getId());
        customerRepository.save(updatedCustomer);
        return CustomerMapper.toResponse(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found for id: " + id);
        }

        customerRepository.deleteById(id);
    }
}