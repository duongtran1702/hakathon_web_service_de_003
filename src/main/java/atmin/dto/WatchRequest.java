package atmin.dto;

import atmin.entity.Status;
import atmin.entity.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchRequest {
    @NotBlank(message = "Tên không được rỗng")
    private String model_name;
    @NotBlank(message = "Hãng không được rỗng")
    private String brand;
    @Positive(message = "Giá phải lớn hơn 0")
    @NotNull(message = "Giá tiền không được rỗng")
    private Double price;
    @NotNull(message = "Loại không được rỗng")
    private Type movement_type;
    @NotNull(message = "Trạng thái không được rỗng")
    private Status status;
}
