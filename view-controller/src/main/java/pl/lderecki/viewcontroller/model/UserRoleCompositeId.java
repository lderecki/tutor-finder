package pl.lderecki.viewcontroller.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleCompositeId implements Serializable {

    @Column("user_id")
    private UUID userId;

    @Column("role_id")
    private Integer roleId;
}
