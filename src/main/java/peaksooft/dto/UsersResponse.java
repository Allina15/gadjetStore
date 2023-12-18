package peaksooft.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record UsersResponse(
        List<UserResponse>users,
        int currentPage,
        int size
) {
}
