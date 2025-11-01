package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.DeleteRequestDto;
import ecommerce.ecombackend.dto.responses.DeleteProductResponseDto;
import ecommerce.ecombackend.service.DeleteProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin-requests/delete-product")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DeleteProductRequestController {

    private final DeleteProductRequestService deleteProductRequestService;

    // Add delete request to database
    @PostMapping
    public ResponseEntity<DeleteProductResponseDto> addDeleteProductRequest(
            @Valid @RequestBody DeleteRequestDto requestDto) {
        try {
            DeleteProductResponseDto response = deleteProductRequestService.createDeleteProductRequest(requestDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all delete product requests
    @GetMapping
    public ResponseEntity<List<DeleteProductResponseDto>> getAllDeleteProductRequests() {
        try {
            List<DeleteProductResponseDto> requests = deleteProductRequestService.getAllDeleteProductRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get delete product request by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeleteProductResponseDto> getDeleteProductRequestById(@PathVariable Long id) {
        return deleteProductRequestService.getDeleteProductRequestById(id)
                .map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete delete product request
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeleteProductRequest(@PathVariable Long id) {
        if (deleteProductRequestService.deleteDeleteProductRequest(id)) {
            return new ResponseEntity<>("Delete product request deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delete product request not found", HttpStatus.NOT_FOUND);
        }
    }
}
