package pl.lderecki.viewcontroller.dao;

import pl.lderecki.viewcontroller.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


public interface UserRepository {

    Mono<User> findById(UUID id);

    Mono<User> findByUsername(String username);

    <S extends User> Mono<S> save(S entity);

    <S extends User> Mono<S> findByIdAndRole(UUID userId, String roleName);

    <S extends User> Flux<S> findAllByCriteria(String nickname, List<Integer> subjectIds, ZonedDateTime periodStart, ZonedDateTime periodEnd, String roleName);
}
