package pl.lderecki.viewcontroller.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.lderecki.viewcontroller.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


@Repository
interface UserCrudRepository extends ReactiveCrudRepository<User, UUID>, UserRepository {

    @Override
    Mono<User> findById(UUID id);

    @Override
    Mono<User> findByUsername(String username);

    @Override
    <S extends User> Mono<S> save(S entity);

    @Override
    @Query("""
                select au.* from app_user au
                left join user_role ur on au.id = ur.user_id
                left join role r on ur.role_id = r.id
                where au.id = :userId
                and r.name = :roleName
            """)
    <S extends User> Mono<S> findByIdAndRole(UUID userId, String roleName);

    @Override
    @Query("""
                select au.* from app_user au
                left join user_teaching_subject uts on au.id = uts.user_id
                left join availability_period ap on uts.user_id = ap.user_id
                left join user_role ur on au.id = ur.user_id
                left join role r on ur.role_id = r.id
                where au.username ilike :nickname
                and uts.teaching_subject_id in (:subjectIds)
                and ap.period_start <= :periodStart
                and ap.period_end >= :periodEnd
                and r.name = :roleName
            """)
    <S extends User> Flux<S> findAllByCriteria(String nickname, List<Integer> subjectIds, ZonedDateTime periodStart, ZonedDateTime periodEnd, String roleName);
}
