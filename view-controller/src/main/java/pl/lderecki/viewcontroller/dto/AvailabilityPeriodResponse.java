package pl.lderecki.viewcontroller.dto;

import java.time.ZonedDateTime;

public record AvailabilityPeriodResponse(ZonedDateTime periodStart, ZonedDateTime periodEnd) {
}
