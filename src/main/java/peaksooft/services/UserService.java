package peaksooft.services;

import peaksooft.dto.*;

import java.security.Principal;

public interface UserService {
    SimpleResponse delete(long id);
    UserResponse getProfile(long id);
    UserResponse update(long id, UserRequest userRequest, Principal principal);
    UsersResponse getAll(int currentPage, int size);
}
