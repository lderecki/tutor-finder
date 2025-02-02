package pl.lderecki.viewcontroller.dao;

import pl.lderecki.viewcontroller.model.AvailabilityPeriod;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AvailabilityPeriodRepository {

    Flux<AvailabilityPeriod> findAllByUserId(UUID userId);
}
