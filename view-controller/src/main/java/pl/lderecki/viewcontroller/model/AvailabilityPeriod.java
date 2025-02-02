package pl.lderecki.viewcontroller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("availability_period")
public class AvailabilityPeriod {

    @Id
    @Column("id")
    private Long id;

    @Column("period_start")
    private ZonedDateTime periodStart;

    @Column("period_end")
    private ZonedDateTime periodEnd;

    @Column("user_id")
    private UUID userId;
}
