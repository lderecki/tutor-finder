package pl.lderecki.viewcontroller.dao;

import pl.lderecki.viewcontroller.model.User;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserRepository {

    Mono<User> findById(UUID id);

    Mono<User> findByUsername(String username);

    <S extends User> Mono<S> save(S entity);
}
