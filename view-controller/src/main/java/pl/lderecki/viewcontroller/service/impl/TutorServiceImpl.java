package pl.lderecki.viewcontroller.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.lderecki.viewcontroller.dao.AvailabilityPeriodRepository;
import pl.lderecki.viewcontroller.dao.TeachingSubjectRepository;
import pl.lderecki.viewcontroller.dao.UserRepository;
import pl.lderecki.viewcontroller.dto.TutorResponse;
import pl.lderecki.viewcontroller.dto.TutorSearchCriteria;
import pl.lderecki.viewcontroller.exception.NotFoundException;
import pl.lderecki.viewcontroller.exception.UnauthorizedException;
import pl.lderecki.viewcontroller.model.AvailabilityPeriod;
import pl.lderecki.viewcontroller.model.TeachingSubject;
import pl.lderecki.viewcontroller.service.TutorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TutorServiceImpl implements TutorService {

    private final UserRepository userRepo;

    private final TeachingSubjectRepository teachingSubjectRepository;

    private final AvailabilityPeriodRepository availabilityPeriodRepository;

    @Override
    public Mono<? extends TutorResponse> findTutorById(UUID id) {
        return userRepo.findByIdAndRole(id, "TUTOR")
                .switchIfEmpty(Mono.error(new NotFoundException("Tutor not found")))
                .flatMap(user -> {
                    Mono<List<TeachingSubject>> subjectsMono = teachingSubjectRepository.findAllByUserId(user.getId())
                            .collectList();

                    return subjectsMono.map(subjects -> {
                        user.setTeachingSubjects(subjects);
                        return user;
                    });
                })
                .flatMap(user -> {
                    Mono<List<AvailabilityPeriod>> periodsMono = availabilityPeriodRepository.findAllByUserId(user.getId())
                            .collectList();

                    return periodsMono.map(periods -> {
                        user.setAvailabilityPeriods(periods);
                        return user;
                    });
                })
                .map(TutorResponse::new);
    }

    @Override
    public Flux<? extends TutorResponse> findByCriteria(TutorSearchCriteria criteria) {
        return userRepo.findAllByCriteria("%" + criteria.nickname() + "%",
                        criteria.subjectIds(),
                        ZonedDateTime.parse(criteria.dateFrom(), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        ZonedDateTime.parse(criteria.dateTo(), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        "TUTOR")
                .flatMap(user -> {
                    Mono<List<TeachingSubject>> subjectsMono = teachingSubjectRepository.findAllByUserId(user.getId())
                            .collectList();

                    return subjectsMono.map(subjects -> {
                        user.setTeachingSubjects(subjects);
                        return user;
                    });
                })
                .flatMap(user -> {
                    Mono<List<AvailabilityPeriod>> periodsMono = availabilityPeriodRepository.findAllByUserId(user.getId())
                            .collectList();

                    return periodsMono.map(periods -> {
                        user.setAvailabilityPeriods(periods);
                        return user;
                    });
                })
                .map(TutorResponse::new);
    }
}
