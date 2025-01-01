package pl.lderecki.viewcontroller.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import pl.lderecki.viewcontroller.model.User;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    @Override
    Mono<UserDetails> findByUsername(String username);

    Mono<? extends UserDetails> save(User user);
}
