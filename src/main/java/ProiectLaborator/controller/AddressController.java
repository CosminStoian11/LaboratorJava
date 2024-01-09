package ProiectLaborator.controller;

import ProiectLaborator.request.AddressRequest;
import ProiectLaborator.response.AddressResponse;
import ProiectLaborator.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping
    public List<AddressResponse> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        try {
            AddressResponse response = addressService.getAddressById(id);
            // If book is found, return a success response
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // If book is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(id, request));
    }


}
