package pl.lderecki.viewcontroller.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.lderecki.viewcontroller.dao.RoleRepository;
import pl.lderecki.viewcontroller.dao.UserRepository;
import pl.lderecki.viewcontroller.dao.UserRoleRepository;
import pl.lderecki.viewcontroller.exception.ConstraintViolationException;
import pl.lderecki.viewcontroller.model.Role;
import pl.lderecki.viewcontroller.model.User;
import pl.lderecki.viewcontroller.model.UserRole;
import pl.lderecki.viewcontroller.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserRoleRepository userRoleRepo;
    private final RoleRepository roleRepo;

    public UserServiceImpl(UserRepository repo, UserRoleRepository userRoleRepo, RoleRepository roleRepo) {
        this.repo = repo;
        this.userRoleRepo = userRoleRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return repo.findByUsername(username)
                .flatMap(user -> {
                    Mono<List<Role>> rolesMono = userRoleRepo.findByUserId(user.getId())
                            .map(UserRole::getRoleId)
                            .collectList()
                            .flatMapMany(roleRepo::findByIdIn)
                            .collectList();

                    return rolesMono.map(roles -> {
                        user.setAuthorities(roles);
                        return user;
                    });
                });

    }

    @Override
    public Mono<? extends UserDetails> save(User user) {

        Mono<UserDetails> resultMono = repo.save(user).flatMap(u -> {
            Mono<List<Role>> roles = Flux.fromIterable(user.getAuthorities())
                    .map(a -> new UserRole(null, u.getId(), a.getId()))
                    .flatMap(userRoleRepo::save)
                    .map(UserRole::getRoleId)
                    .collectList()
                    .flatMapMany(roleRepo::findByIdIn)
                    .collectList();

            return roles.map(r -> {
                u.setAuthorities(r);
                return u;
            });
        });

        return repo.findByUsername(user.getUsername())
                .hasElement()
                .flatMap(b -> b ? Mono.error(new ConstraintViolationException("Username is not unique")) : resultMono);
    }

}
