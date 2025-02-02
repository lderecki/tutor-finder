package pl.lderecki.viewcontroller.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pl.lderecki.viewcontroller.model.TeachingSubject;
import reactor.core.publisher.Flux;

import java.util.UUID;

interface TeachingSubjectCrudRepository extends R2dbcRepository<TeachingSubject, Integer>, TeachingSubjectRepository {

    @Query("""
            select ts.* from user_teaching_subject uts 
            left join teaching_subject ts on uts.teaching_subject_id = ts.id
            where uts.user_id = :userId
          """)
    <S extends TeachingSubject> Flux<S> findAllByUserId(UUID userId);
}
