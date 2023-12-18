package peaksooft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import peaksooft.models.User;

@Builder
public record UserRequest(
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
    public User build(){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
