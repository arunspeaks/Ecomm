package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.DeleteRequestDto;
import ecommerce.ecombackend.dto.responses.DeleteProductResponseDto;
import ecommerce.ecombackend.model.DeleteProductRequest;
import ecommerce.ecombackend.repository.DeleteProductRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeleteProductRequestService {

    private final DeleteProductRequestRepository deleteProductRequestRepository;

    public DeleteProductResponseDto createDeleteProductRequest(DeleteRequestDto requestDto) {
        DeleteProductRequest request = DeleteProductRequest.builder()
                .name(requestDto.getName()) // Assuming same fields as your entity
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .imageUrl(requestDto.getImageUrl())
                .category(requestDto.getCategory())
                .brand(requestDto.getBrand())
                .requestType("delete")
                .build();

        DeleteProductRequest savedRequest = deleteProductRequestRepository.save(request);
        return mapToResponseDto(savedRequest);
    }

    public List<DeleteProductResponseDto> getAllDeleteProductRequests() {
        return deleteProductRequestRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<DeleteProductResponseDto> getDeleteProductRequestById(Long id) {
        return deleteProductRequestRepository.findById(id)
                .map(this::mapToResponseDto);
    }

    public boolean deleteDeleteProductRequest(Long id) {
        if (deleteProductRequestRepository.existsById(id)) {
            deleteProductRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private DeleteProductResponseDto mapToResponseDto(DeleteProductRequest request) {
        return DeleteProductResponseDto.builder()
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
