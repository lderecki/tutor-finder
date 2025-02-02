package pl.lderecki.viewcontroller.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.lderecki.viewcontroller.model.AvailabilityPeriod;
import reactor.core.publisher.Flux;

import java.util.UUID;

interface AvailabilityPeriodCrudRepository extends ReactiveCrudRepository<AvailabilityPeriod, Long>, AvailabilityPeriodRepository {

    @Override
    Flux<AvailabilityPeriod> findAllByUserId(UUID userId);
}
