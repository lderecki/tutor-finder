package pl.lderecki.viewcontroller.dto;

import java.util.List;

public record TutorSearchCriteria(String nickname, List<Integer> subjectIds, String dateFrom, String dateTo) {
}
