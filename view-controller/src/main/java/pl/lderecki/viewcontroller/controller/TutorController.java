package pl.lderecki.viewcontroller.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.lderecki.viewcontroller.dto.TutorResponse;
import pl.lderecki.viewcontroller.dto.TutorSearchCriteria;
import pl.lderecki.viewcontroller.service.TutorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService service;

    @GetMapping("/{id}")
    public Mono<? extends TutorResponse> getTutorById(@PathVariable("id") UUID id) {
        return service.findTutorById(id);
    }

    @GetMapping()
    public Flux<? extends TutorResponse> getByCriteria(@RequestParam(name = "nickname", required = false, defaultValue = "") String nickname,
                                                       @RequestParam(name = "subjectIds") List<Integer> subjectIds,
                                                       @RequestParam(name = "dateFrom") String dateFrom,
                                                       @RequestParam(name = "dateTo") String dateTo) {

        return service.findByCriteria(new TutorSearchCriteria(nickname, subjectIds, dateFrom, dateTo));
    }
}
