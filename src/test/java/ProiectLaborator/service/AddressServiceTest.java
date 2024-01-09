package ProiectLaborator.service;

import ProiectLaborator.entity.Address;
import ProiectLaborator.mapper.AddressMapper;
import ProiectLaborator.repository.AddressRepository;
import ProiectLaborator.request.AddressRequest;
import ProiectLaborator.response.AddressResponse;
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
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;

    @Test
    void getAllAddresses() {
        Address address = DataUtils.getAddress();
        when(addressRepository.findAll()).thenReturn(Collections.singletonList(address));
        var responses = addressService.getAllAddresses();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        AddressResponse response = responses.get(0);

        assertEquals(response, AddressMapper.toResponse(address));
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    void getAddressById() {
        Address address = DataUtils.getAddress();
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        AddressResponse response = addressService.getAddressById(1L);

        assertEquals(response, AddressMapper.toResponse(address));
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    void getAddressById_throwsException() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            addressService.getAddressById(1L);
        });

        assertEquals("Address not found for id: 1", thrown.getMessage());
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    void updateAddress() {
        Address address = DataUtils.getAddress();
        AddressRequest request = AddressMapper.toRequestFromEntity(address);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponse response = addressService.updateAddress(1L, request);

        assertEquals(response, AddressMapper.toResponse(address));
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }
}