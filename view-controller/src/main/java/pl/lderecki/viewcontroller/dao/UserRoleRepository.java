package pl.lderecki.viewcontroller.dao;

import pl.lderecki.viewcontroller.model.UserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRoleRepository {

    Flux<UserRole> findByUserId(UUID userId);

    <S extends UserRole> Mono<S> save(S entity);
}
