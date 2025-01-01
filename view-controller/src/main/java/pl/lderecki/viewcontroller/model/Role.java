package pl.lderecki.viewcontroller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("role")
public class Role implements GrantedAuthority {

    @Id
    @Column("id")
    private Integer id;

    @Column("name")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
