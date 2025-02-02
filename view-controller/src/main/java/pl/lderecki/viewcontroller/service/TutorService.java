package pl.lderecki.viewcontroller.service;

import org.springframework.security.core.userdetails.UserDetails;
import pl.lderecki.viewcontroller.dto.TutorResponse;
import pl.lderecki.viewcontroller.dto.TutorSearchCriteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TutorService {

    Mono<? extends TutorResponse> findTutorById(UUID id);

    Flux<? extends TutorResponse> findByCriteria(TutorSearchCriteria criteria);
}
