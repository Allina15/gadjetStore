package peaksooft.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    @NotNull(message = "product name must not be null")
    private String comment;
    @NotNull(message = "product name must not be null")
    private String productName;
}
