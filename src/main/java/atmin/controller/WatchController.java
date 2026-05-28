package atmin.controller;

import atmin.dto.WatchRequest;
import atmin.dto.WatchResponse;
import atmin.service.WatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/watches")
public class WatchController {
    private final WatchService watchService;

    @PostMapping
    public ResponseEntity<WatchResponse> save(@Valid @RequestBody WatchRequest watch) {
        return new ResponseEntity<>(watchService.save(watch), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<WatchResponse>> getWatches(
            @RequestParam(required = false) String model_name,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(watchService.search(model_name, brand, page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WatchResponse> getWatchById(@PathVariable Long id) {
        return new ResponseEntity<>(watchService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WatchResponse> update(@PathVariable Long id, @Valid @RequestBody WatchRequest watchRequest) {
        return new ResponseEntity<>(watchService.update(watchRequest, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WatchResponse> patch(@PathVariable Long id, @RequestBody WatchRequest watchRequest) {
        return new ResponseEntity<>(watchService.updatePatch(watchRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        watchService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
