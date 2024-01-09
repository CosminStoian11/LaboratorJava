package ProiectLaborator.service;

import ProiectLaborator.entity.Address;
import ProiectLaborator.entity.Customer;
import ProiectLaborator.mapper.AddressMapper;
import ProiectLaborator.repository.AddressRepository;
import ProiectLaborator.repository.CustomerRepository;
import ProiectLaborator.request.AddressRequest;
import ProiectLaborator.response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;


    public List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(AddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AddressResponse getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found for id: " + id));
        return AddressMapper.toResponse(address);
    }

    public AddressResponse updateAddress(Long id, AddressRequest request) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found for id: " + id));

        Address updatedAddress = AddressMapper.toEntity(request);
        updatedAddress.setId(existingAddress.getId());
        addressRepository.save(updatedAddress);
        return AddressMapper.toResponse(updatedAddress);
    }

}
