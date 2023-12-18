package peaksooft.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String userName,
        String token
) {
}
