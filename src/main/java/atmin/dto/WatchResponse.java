package atmin.dto;

import atmin.entity.Status;
import atmin.entity.Type;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatchResponse {
    private Long id;
    private String model_name;
    private String brand;
    private Double price;
    private Type movement_type;
    private Status status;
}
