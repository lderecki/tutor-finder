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
@Table("user_teaching_subject")
public class UserTeachingSubject {

    @Transient
    @Id
    private Integer id;

    @Column("user_id")
    private UUID userId;

    @Column("teaching_subject_id")
    private Integer teachingSubjectId;
}
