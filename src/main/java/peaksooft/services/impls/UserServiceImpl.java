package peaksooft.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksooft.dto.*;
import peaksooft.enums.Role;
import peaksooft.exceptions.BadCredentialsException;
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.User;
import peaksooft.repository.UserRepository;
import peaksooft.services.UserService;

import java.security.Principal;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

     private final UserRepository userRepository;

     @Override
     public SimpleResponse delete(long id) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          String email = authentication.getName();
          User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + "not found"));
                    userRepository.delete(user);
          return new SimpleResponse(HttpStatus.OK, "Deleted successfully");
     }

     @Override
     public UserResponse getProfile(long id) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          String email = authentication.getName();

          User authenticatedUser = userRepository.findUserByEmail(email)
                  .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
               return new UserResponse(
                       authenticatedUser.getFirstName(),
                       authenticatedUser.getLastName(),
                       authenticatedUser.getEmail(),
                       authenticatedUser.getFavorites(),
                       authenticatedUser.getBasket()
               );
     }

     @Override
     public UserResponse update(long id, UserRequest userRequest, Principal principal) {
          User newUser = userRequest.build();
          String email = principal.getName();
          User authUser = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
          if (authUser.getRole().equals(Role.ADMIN)) {
               User parUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
               parUser.setFirstName(newUser.getFirstName());
               parUser.setLastName(newUser.getLastName());
               parUser.setEmail(newUser.getEmail());
          } else if (authUser.getRole().equals(Role.USER) && authUser.getId() == id) {
               User parUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
               parUser.setFirstName(newUser.getFirstName());
               parUser.setLastName(newUser.getLastName());
               parUser.setEmail(newUser.getEmail());
               parUser.setPassword(newUser.getPassword());
          }else{
          throw new BadCredentialsException("Access denied");
     }
          return new UserResponse(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail());
     }

     @Override
     public UsersResponse getAll(int currentPage, int size) {
          Pageable pageable = PageRequest.of(currentPage-1,size);
          Page <UserResponse> users = userRepository.getAll(pageable);
          return UsersResponse.builder()
                  .users(users.getContent())
                  .currentPage(users.getNumber()+1)
                  .size(users.getTotalPages())
                  .build();
     }
}
