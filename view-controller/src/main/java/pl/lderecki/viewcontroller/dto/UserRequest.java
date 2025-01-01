package pl.lderecki.viewcontroller.dto;

import java.util.List;

public record UserRequest(String firstName, String lastName, String email, String username, String password, List<Integer> roleIds) {
}
