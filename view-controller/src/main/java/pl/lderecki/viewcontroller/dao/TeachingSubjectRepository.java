package pl.lderecki.viewcontroller.dao;

import pl.lderecki.viewcontroller.model.TeachingSubject;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface TeachingSubjectRepository {

    <S extends TeachingSubject> Flux<S> findAllByUserId(UUID userId);
}
