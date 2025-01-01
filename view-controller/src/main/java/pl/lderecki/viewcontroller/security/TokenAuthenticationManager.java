package pl.lderecki.viewcontroller.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import pl.lderecki.viewcontroller.dto.TokenResponse;
import pl.lderecki.viewcontroller.dto.UserRequest;
import pl.lderecki.viewcontroller.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface TokenAuthenticationManager extends ReactiveAuthenticationManager {

    @Override
    Mono<Authentication> authenticate(Authentication authentication);

    Mono<TokenResponse> generateToken(String username, String password);

    Mono<UserResponse> createUser(UserRequest userRequest);

    Mono<TokenResponse> refreshToken();
}
