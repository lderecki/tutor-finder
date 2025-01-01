package pl.lderecki.viewcontroller.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface TokenService {

    Mono<String> generateToken(String username, Collection<? extends GrantedAuthority> roles);

    Mono<UserDetails> extractUserDetails(String token);
}
