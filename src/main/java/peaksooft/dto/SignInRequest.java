package peaksooft.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotNull(message = "user name must not be null")
        String username,
        @NotNull(message = "password must not be null")
        @Min(value = 8)
        String password) {
}
