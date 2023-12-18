package peaksooft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignUpRequest(
        @NotNull(message = "first name must not be null")
        String firstName,
        @NotNull(message = "last name must not be null")
        String lastName,
        @NotNull(message = "email must not be null")
        @Email
        String email,
        @NotNull(message = "password must not be null")
        String password
) {
}
