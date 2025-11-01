package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.InventoryRequestDto;
import ecommerce.ecombackend.dto.responses.InventoryResponseDto;
import ecommerce.ecombackend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/update")
    public ResponseEntity<InventoryResponseDto> updateStock(@RequestBody InventoryRequestDto dto) {
        return ResponseEntity.ok(inventoryService.updateStock(dto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDto> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }
}
