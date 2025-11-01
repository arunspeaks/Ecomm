package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.AddProductRequestDto;
import ecommerce.ecombackend.dto.responses.AddProductResponseDto;
import ecommerce.ecombackend.model.AddProductRequest;
import ecommerce.ecombackend.repository.AddProductRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddProductRequestService {

    private final AddProductRequestRepository addProductRequestRepository;

    public AddProductResponseDto createAddProductRequest(AddProductRequestDto requestDto) {
        AddProductRequest request = AddProductRequest.builder()
                .name(requestDto.getName()) // Assuming your DTO has getProductName()
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .imageUrl(requestDto.getImageUrl())
                .category(requestDto.getCategory())
                .brand(requestDto.getBrand())
                .requestType("add")
                .build();

        AddProductRequest savedRequest = addProductRequestRepository.save(request);
        return mapToResponseDto(savedRequest);
    }

    public List<AddProductResponseDto> getAllAddProductRequests() {
        return addProductRequestRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<AddProductResponseDto> getAddProductRequestById(Long id) {
        return addProductRequestRepository.findById(id)
                .map(this::mapToResponseDto);
    }

    public boolean deleteAddProductRequest(Long id) {
        if (addProductRequestRepository.existsById(id)) {
            addProductRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private AddProductResponseDto mapToResponseDto(AddProductRequest request) {
        return AddProductResponseDto.builder()
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
