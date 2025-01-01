package pl.lderecki.viewcontroller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("user_role")
public class UserRole {

    @Transient
    @Id
    private Integer id;

    @Column("user_id")
    private UUID userId;


    @Column("role_id")
    private Integer roleId;

    /*@Id
    private UserRoleCompositeId id;*/
}
