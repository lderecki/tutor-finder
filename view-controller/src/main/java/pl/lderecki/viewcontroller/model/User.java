package pl.lderecki.viewcontroller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("app_user")
public class User implements UserDetails {

    private static Long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("account_not_expired")
    private Boolean accountNotExpired;

    @Column("account_not_locked")
    private Boolean accountNotLocked;

    @Column("credentials_not_expired")
    private Boolean credentialsNotExpired;

    @Column("enabled")
    private Boolean enabled;

    @Transient
    private List<Role> authorities = new ArrayList<>();  //? extends GrantedAuthority

}
