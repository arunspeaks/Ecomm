package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.ModifyRequestDto;
import ecommerce.ecombackend.dto.responses.ModifyProductResponseDto;
import ecommerce.ecombackend.model.ModifyProductRequest;
import ecommerce.ecombackend.repository.ModifyProductRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModifyProductRequestService {

    private final ModifyProductRequestRepository modifyProductRequestRepository;

    public ModifyProductResponseDto createModifyProductRequest(ModifyRequestDto requestDto) {
        ModifyProductRequest request = ModifyProductRequest.builder()
                .name(requestDto.getName()) // Assuming same fields as your entity
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .imageUrl(requestDto.getImageUrl())
                .category(requestDto.getCategory())
                .brand(requestDto.getBrand())
                .requestType("modify")
                .build();

        ModifyProductRequest savedRequest = modifyProductRequestRepository.save(request);
        return mapToResponseDto(savedRequest);
    }

    public List<ModifyProductResponseDto> getAllModifyProductRequests() {
        return modifyProductRequestRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<ModifyProductResponseDto> getModifyProductRequestById(Long id) {
        return modifyProductRequestRepository.findById(id)
                .map(this::mapToResponseDto);
    }

    public boolean deleteModifyProductRequest(Long id) {
        if (modifyProductRequestRepository.existsById(id)) {
            modifyProductRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ModifyProductResponseDto mapToResponseDto(ModifyProductRequest request) {
        return ModifyProductResponseDto.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .brand(request.getBrand())
                .requestType(request.getRequestType())
                .build();
    }
}
