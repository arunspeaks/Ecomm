package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.AddProductRequestDto;
import ecommerce.ecombackend.dto.responses.AddProductResponseDto;
import ecommerce.ecombackend.service.AddProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin-requests/add-product")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AddProductRequestController {

    private final AddProductRequestService addProductRequestService;

    // Add product request to database
    @PostMapping
    public ResponseEntity<AddProductResponseDto> addProductRequest(
            @Valid @RequestBody AddProductRequestDto requestDto) {
        try {
            AddProductResponseDto response = addProductRequestService.createAddProductRequest(requestDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all add product requests
    @GetMapping("/all")
    public ResponseEntity<List<AddProductResponseDto>> getAllAddProductRequests() {
        try {
            List<AddProductResponseDto> requests = addProductRequestService.getAllAddProductRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get add product request by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddProductResponseDto> getAddProductRequestById(@PathVariable Long id) {
        return addProductRequestService.getAddProductRequestById(id)
                .map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete add product request
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddProductRequest(@PathVariable Long id) {
        if (addProductRequestService.deleteAddProductRequest(id)) {
            return new ResponseEntity<>("Add product request deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Add product request not found", HttpStatus.NOT_FOUND);
        }
    }
}
