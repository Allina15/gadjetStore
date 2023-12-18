package peaksooft.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksooft.enums.Role;
import peaksooft.repository.UserRepository;
import peaksooft.services.AuthenticationService;
import peaksooft.dto.AuthenticationResponse;
import peaksooft.dto.SignInRequest;
import peaksooft.dto.SignUpRequest;
import peaksooft.exceptions.AlreadyExistsException;
import peaksooft.exceptions.BadCredentialsException;
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.error("user with userName: " + request.email()+ " already exists!");
            throw new AlreadyExistsException(
                    "user with userName: " + request.email()+ " already exists!"
            );
        }
        User user = User.builder().firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(token)
                .userName(user.getUsername())
                .build();
    }

    @Override
    public AuthenticationResponse singIn(SignInRequest request) {
        User user = userRepository.findUserByEmail(request.username()).orElseThrow(
                () -> new NotFoundException(
                        "user with email: " + request.username() + " not fount"));
        if (request.username().isBlank()) {
            throw new BadCredentialsException("email is blank");
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken)
                .userName(user.getUsername())
                .build();
    }
}
