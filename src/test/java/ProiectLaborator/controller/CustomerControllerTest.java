package ProiectLaborator.controller;

import ProiectLaborator.mapper.CustomerMapper;
import ProiectLaborator.request.CustomerRequest;
import ProiectLaborator.response.CustomerResponse;
import ProiectLaborator.service.CustomerService;
import ProiectLaborator.utils.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomerTest() throws Exception {
        CustomerResponse customerResponse = CustomerMapper.toResponse(DataUtils.getCustomerTest());
        CustomerRequest customerRequest = CustomerMapper.toRequestFromEntity(DataUtils.getCustomerTest());

        Mockito.when(customerService.createCustomer(customerRequest)).thenReturn(customerResponse);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.name").value(customerResponse.getName()));
    }

    @Test
    void getAllCustomersTest() throws Exception {
        List<CustomerResponse> customerResponses = List.of(CustomerMapper.toResponse(DataUtils.getCustomerTest()));
        Mockito.when(customerService.getAllCustomers()).thenReturn(customerResponses);

        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(customerResponses.size()));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        CustomerResponse customerResponse = CustomerMapper.toResponse(DataUtils.getCustomerTest());
        Long customerId = customerResponse.getId();
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customerResponse);

        mockMvc.perform(get("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.name").value(customerResponse.getName()));
    }

    @Test
    void updateCustomerTest() throws Exception {
        CustomerResponse updatedCustomerResponse = CustomerMapper.toResponse(DataUtils.getCustomerTest());
        Long customerId = updatedCustomerResponse.getId();
        CustomerRequest customerRequest = CustomerMapper.toRequestFromEntity(DataUtils.getCustomerTest());

        Mockito.when(customerService.updateCustomer(customerId, customerRequest)).thenReturn(updatedCustomerResponse);

        mockMvc.perform(put("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCustomerResponse.getId()))
                .andExpect(jsonPath("$.name").value(updatedCustomerResponse.getName()));
    }

    @Test
    void deleteCustomerTest() throws Exception {
        Long customerId = 1L;
        Mockito.doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/customers/{id}", customerId))
                .andExpect(status().isOk());
    }
}