package pl.lderecki.viewcontroller.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.lderecki.viewcontroller.dto.TokenRequest;
import pl.lderecki.viewcontroller.dto.TokenResponse;
import pl.lderecki.viewcontroller.dto.UserRequest;
import pl.lderecki.viewcontroller.dto.UserResponse;
import pl.lderecki.viewcontroller.security.TokenAuthenticationManager;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final TokenAuthenticationManager authManager;

    public AuthenticationController(TokenAuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/generate_token")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest) {
        return authManager.generateToken(tokenRequest.username(), tokenRequest.password());
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        return authManager.createUser(userRequest);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/refresh_token")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TokenResponse> refreshToken() {
        return authManager.refreshToken();
    }
}
