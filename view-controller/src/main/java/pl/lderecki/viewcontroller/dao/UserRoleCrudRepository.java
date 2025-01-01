package pl.lderecki.viewcontroller.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.lderecki.viewcontroller.model.UserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
interface UserRoleCrudRepository extends ReactiveCrudRepository<UserRole, Integer>, UserRoleRepository {

    @Override
    Flux<UserRole> findByUserId(UUID userId);

    @Override
    <S extends UserRole> Mono<S> save(S entity);
}
