package pl.lderecki.viewcontroller.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.lderecki.viewcontroller.model.User;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
interface UserCrudRepository extends ReactiveCrudRepository<User, UUID>, UserRepository {

    @Override
    Mono<User> findById(UUID id);

    @Override
    Mono<User> findByUsername(String username);

    @Override
    <S extends User> Mono<S> save(S entity);
}
