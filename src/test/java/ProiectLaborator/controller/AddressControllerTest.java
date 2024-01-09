package ProiectLaborator.controller;

import ProiectLaborator.mapper.AddressMapper;
import ProiectLaborator.request.AddressRequest;
import ProiectLaborator.response.AddressResponse;
import ProiectLaborator.service.AddressService;
import ProiectLaborator.utils.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressService addressService;

    @Test
    void getAllAddressesTest() throws Exception {
        // Mock the behavior of addressService to return a list of addresses
        List<AddressResponse> addressResponses = List.of(AddressMapper.toResponse(DataUtils.getAddress()));
        when(addressService.getAllAddresses()).thenReturn(addressResponses);

        mockMvc.perform(get("/addresses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(addressResponses.size()));
    }

    @Test
    void getAddressByIdTest() throws Exception {
        // Mock the behavior of addressService to return an address by ID
        AddressResponse addressResponse = AddressMapper.toResponse(DataUtils.getAddress());
        Long addressId = addressResponse.getId();

        when(addressService.getAddressById(addressId)).thenReturn(addressResponse);

        mockMvc.perform(get("/addresses/{id}", addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressResponse.getId()))
                .andExpect(jsonPath("$.city").value(addressResponse.getCity()));
    }

    @Test
    void updateAddressTest() throws Exception {
        // Mock the behavior of addressService to update an address
        AddressResponse addressResponse = AddressMapper.toResponse(DataUtils.getAddress());
        Long addressId = addressResponse.getId();
        AddressRequest addressRequest = AddressMapper.toRequestFromEntity(DataUtils.getAddress());

        when(addressService.updateAddress(addressId, addressRequest)).thenReturn(addressResponse);

        mockMvc.perform(put("/addresses/{id}", addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest))) // Convert object to JSON using ObjectMapper
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressResponse.getId()))
                .andExpect(jsonPath("$.city").value(addressResponse.getCity()));
    }
}
