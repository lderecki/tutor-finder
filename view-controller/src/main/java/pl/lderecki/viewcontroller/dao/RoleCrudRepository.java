package pl.lderecki.viewcontroller.dao;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.lderecki.viewcontroller.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
interface RoleCrudRepository extends R2dbcRepository<Role, Integer>, RoleRepository {

    @Override
    Mono<Role> findById(Integer integer);

    Flux<Role> findByIdIn(List<Integer> ids);

}
