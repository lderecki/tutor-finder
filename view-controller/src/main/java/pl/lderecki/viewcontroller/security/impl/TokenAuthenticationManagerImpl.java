package pl.lderecki.viewcontroller.security.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.lderecki.viewcontroller.dto.TokenResponse;
import pl.lderecki.viewcontroller.dto.UserRequest;
import pl.lderecki.viewcontroller.dto.UserResponse;
import pl.lderecki.viewcontroller.exception.UnauthorizedException;
import pl.lderecki.viewcontroller.model.Role;
import pl.lderecki.viewcontroller.model.User;
import pl.lderecki.viewcontroller.security.TokenAuthenticationManager;
import pl.lderecki.viewcontroller.security.TokenService;
import pl.lderecki.viewcontroller.service.UserService;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Component
public class TokenAuthenticationManagerImpl implements TokenAuthenticationManager {


    private final TokenService tokenService;

    private final UserService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public TokenAuthenticationManagerImpl(TokenService tokenService, UserService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        return tokenService.extractUserDetails(token)
                .map(u -> new UsernamePasswordAuthenticationToken(u, token, u.getAuthorities()));
    }

    @Override
    public Mono<TokenResponse> generateToken(String username, String password) {
        return userDetailsService.findByUsername(username)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User not found")))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .switchIfEmpty(Mono.error(new UnauthorizedException("Invalid credentials")))
                .flatMap(user -> tokenService.generateToken(user.getUsername(), user.getAuthorities()))
                .map(TokenResponse::new);
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return Mono.just(userRequest).map(u -> new User(null, u.firstName(), u.lastName(), u.email(), u.username(),
                u.password(), true, true, true, true, userRequest.roleIds().stream().map(id -> new Role(id, null)).toList()))
                .flatMap(userDetailsService::save)
                .map(user -> new UserResponse((User) user));
    }

    @Override
    public Mono<TokenResponse> refreshToken() {

        Mono<Authentication> authenticationMono = ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication);

        Mono<String> principalMono = authenticationMono.map(Authentication::getPrincipal).map(p -> ((User) p).getUsername());
        Mono<Collection<? extends GrantedAuthority>> authoritiesMono = authenticationMono.map(Authentication::getAuthorities);

        return principalMono
                .zipWith(authoritiesMono, tokenService::generateToken).flatMap(t -> t).map(TokenResponse::new);
    }
}
