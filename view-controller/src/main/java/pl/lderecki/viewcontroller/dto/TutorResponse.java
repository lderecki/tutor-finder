package pl.lderecki.viewcontroller.dto;

import pl.lderecki.viewcontroller.model.AvailabilityPeriod;
import pl.lderecki.viewcontroller.model.TeachingSubject;
import pl.lderecki.viewcontroller.model.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TutorResponse(UUID id, String username, List<String> teachingSubjects, List<AvailabilityPeriodResponse> availabilityPeriods) {

    public TutorResponse(User user) {
        this(user.getId(), user.getUsername(),
                user.getTeachingSubjects().stream()
                        .map(TeachingSubject::getName)
                        .collect(Collectors.toList()),

                user.getAvailabilityPeriods().stream()
                        .map(ap -> new AvailabilityPeriodResponse(ap.getPeriodStart(), ap.getPeriodEnd()))
                        .collect(Collectors.toList()));
    }
}
