package pl.lderecki.viewcontroller.dto;

import pl.lderecki.viewcontroller.model.User;

import java.util.List;
import java.util.UUID;

public record UserResponse(UUID id, String firstName, String lastName, String email, String username,
                           Boolean accountNotExpired, Boolean accountNotLocked, Boolean credentialsNotExpired,
                           Boolean enabled, List<AuthorityResponse> authorities) {

    public UserResponse(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getAccountNotExpired(),
                user.getAccountNotLocked(), user.getCredentialsNotExpired(), user.getEnabled(),
                user.getAuthorities().stream().map(a -> new AuthorityResponse(a.getId(), a.getName())).toList());
    }
}
