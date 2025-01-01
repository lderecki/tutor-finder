package pl.lderecki.viewcontroller.dao;

import pl.lderecki.viewcontroller.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface RoleRepository {

    Mono<Role> findById(Integer integer);

    Flux<Role> findByIdIn(Collection<Integer> ids);
}
