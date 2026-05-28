package atmin.service;

import atmin.dto.WatchRequest;
import atmin.entity.Watch;
import atmin.dto.WatchResponse;
import atmin.repository.WatchRepository;
import atmin.exception.CustomValidationException;
import atmin.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchService {
    private final WatchRepository watchRepository;

    private WatchResponse mapToResponse(Watch watch) {
        if (watch == null) return null;
        return WatchResponse.builder()
                .id(watch.getId())
                .model_name(watch.getModel_name())
                .brand(watch.getBrand())
                .price(watch.getPrice())
                .movement_type(watch.getMovement_type())
                .status(watch.getStatus())
                .build();
    }

    private Watch findEntityById(Long id) {
        Watch w = watchRepository.findWatchById(id);
        if (w == null) {
            throw new ResourceNotFoundException("Watch not found with id: " + id);
        }
        return w;
    }

    public WatchResponse getById(Long id) {
        return mapToResponse(findEntityById(id));
    }

    public WatchResponse save(WatchRequest watchRequest) {
        Watch watch = Watch.builder().model_name(watchRequest.getModel_name())
                .brand(watchRequest.getBrand())
                .price(watchRequest.getPrice())
                .movement_type(watchRequest.getMovement_type())
                .status(watchRequest.getStatus())
                .build();
        return mapToResponse(watchRepository.save(watch));
    }

    public List<WatchResponse> getWatches() {
        return watchRepository.findAllWatches().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public WatchResponse update(WatchRequest watchRequest, Long id) {
        Watch oldWatch = findEntityById(id);
        oldWatch.setModel_name(watchRequest.getModel_name());
        oldWatch.setBrand(watchRequest.getBrand());
        oldWatch.setPrice(watchRequest.getPrice());
        oldWatch.setMovement_type(watchRequest.getMovement_type());
        oldWatch.setStatus(watchRequest.getStatus());
        return mapToResponse(watchRepository.save(oldWatch));
    }

    public WatchResponse updatePatch(WatchRequest watchRequest, Long id) {
        Watch oldWatch = findEntityById(id);

        Map<String, String> errors = new HashMap<>();

        if (watchRequest.getModel_name() != null) {
            if (watchRequest.getModel_name().trim().isEmpty()) {
                errors.put("model_name", "Tên không được để trống");
            } else {
                oldWatch.setModel_name(watchRequest.getModel_name());
            }
        }

        if (watchRequest.getBrand() != null) {
            if (watchRequest.getBrand().trim().isEmpty()) {
                errors.put("brand", "Hãng không được để trống");
            } else {
                oldWatch.setBrand(watchRequest.getBrand());
            }
        }

        if (watchRequest.getPrice() != null) {
            if (watchRequest.getPrice() <= 0) {
                errors.put("price", "Giá phải lớn hơn 0");
            } else {
                oldWatch.setPrice(watchRequest.getPrice());
            }
        }

        if (watchRequest.getMovement_type() != null) {
            oldWatch.setMovement_type(watchRequest.getMovement_type());
        }

        if (watchRequest.getStatus() != null) {
            oldWatch.setStatus(watchRequest.getStatus());
        }

        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        return mapToResponse(watchRepository.save(oldWatch));
    }

    public void deleteById(Long id) {
        Watch watch = findEntityById(id);
        if (watch != null) {
            watchRepository.deleteById(id);
        }
    }

    public Page<WatchResponse> search(String model_name, String brand, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Watch> watchPage = watchRepository.search(model_name, brand, pageable);
        return watchPage.map(this::mapToResponse);
    }
}
