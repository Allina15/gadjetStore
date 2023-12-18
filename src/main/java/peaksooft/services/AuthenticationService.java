package peaksooft.services;

import peaksooft.dto.AuthenticationResponse;
import peaksooft.dto.SignInRequest;
import peaksooft.dto.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest request);

    AuthenticationResponse singIn(SignInRequest request);
}
