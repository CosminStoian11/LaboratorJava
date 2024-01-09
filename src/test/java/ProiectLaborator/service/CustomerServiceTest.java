package ProiectLaborator.service;

import ProiectLaborator.entity.Customer;
import ProiectLaborator.mapper.CustomerMapper;
import ProiectLaborator.repository.CustomerRepository;
import ProiectLaborator.request.CustomerRequest;
import ProiectLaborator.response.CustomerResponse;
import ProiectLaborator.utils.DataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    void createCustomerTest() {
        Customer customerTest = DataUtils.getCustomerTest();
        CustomerRequest customerRequestTest = CustomerMapper.toRequestFromEntity(customerTest);
        when(customerRepository.save(any(Customer.class))).thenReturn(customerTest);

        CustomerResponse response = customerService.createCustomer(customerRequestTest);

        assertEquals(response, CustomerMapper.toResponse(customerTest));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getAllCustomersTest() {
        Customer customerTest = DataUtils.getCustomerTest();
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customerTest));
        var responses = customerService.getAllCustomers();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        CustomerResponse response = responses.get(0);

        assertEquals(response, CustomerMapper.toResponse(customerTest));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerByIdTest() {
        Customer customerTest = DataUtils.getCustomerTest();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerTest));
        CustomerResponse response = customerService.getCustomerById(1L);

        assertEquals(response, CustomerMapper.toResponse(customerTest));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_NotFoundTest() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerById(1L);
        });

        assertEquals("Customer not found for id: 1", thrown.getMessage());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void updateCustomerTest() {
        Customer customerTest = DataUtils.getCustomerTest();
        CustomerRequest request = CustomerMapper.toRequestFromEntity(customerTest);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerTest));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerTest);

        CustomerResponse response = customerService.updateCustomer(1L, request);

        CustomerResponse responseExpected = CustomerMapper.toResponse(customerTest);
        assertEquals(response.getName(), responseExpected.getName());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteCustomerTest() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCustomer_NotFoundTest() {
        when(customerRepository.existsById(anyLong())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            customerService.deleteCustomer(1L);
        });

        assertEquals("Customer not found for id: 1", thrown.getMessage());
        verify(customerRepository, never()).deleteById(anyLong());
    }
}


