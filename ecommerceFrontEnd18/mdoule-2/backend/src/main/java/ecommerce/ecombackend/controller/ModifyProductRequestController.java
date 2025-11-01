package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.ModifyRequestDto;
import ecommerce.ecombackend.dto.responses.ModifyProductResponseDto;
import ecommerce.ecombackend.service.ModifyProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin-requests/modify-product")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ModifyProductRequestController {

    private final ModifyProductRequestService modifyProductRequestService;

    // Add modify request to database
    @PostMapping
    public ResponseEntity<ModifyProductResponseDto> addModifyProductRequest(
            @Valid @RequestBody ModifyRequestDto requestDto) {
        try {
            ModifyProductResponseDto response = modifyProductRequestService.createModifyProductRequest(requestDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all modify product requests
    @GetMapping
    public ResponseEntity<List<ModifyProductResponseDto>> getAllModifyProductRequests() {
        try {
            List<ModifyProductResponseDto> requests = modifyProductRequestService.getAllModifyProductRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get modify product request by ID
    @GetMapping("/{id}")
    public ResponseEntity<ModifyProductResponseDto> getModifyProductRequestById(@PathVariable Long id) {
        return modifyProductRequestService.getModifyProductRequestById(id)
                .map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete modify product request
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModifyProductRequest(@PathVariable Long id) {
        if (modifyProductRequestService.deleteModifyProductRequest(id)) {
            return new ResponseEntity<>("Modify product request deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Modify product request not found", HttpStatus.NOT_FOUND);
        }
    }
}
