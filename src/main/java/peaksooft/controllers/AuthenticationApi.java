package peaksooft.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksooft.services.AuthenticationService;
import peaksooft.dto.AuthenticationResponse;
import peaksooft.dto.SignInRequest;
import peaksooft.dto.SignUpRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PermitAll
    @PostMapping("/signUp")
    AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request){
        return authenticationService.signUp(request);
    }

    @PermitAll
    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody SignInRequest request){
        return authenticationService.singIn(request);
    }

}
