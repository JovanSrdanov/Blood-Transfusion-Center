package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class DateRange {
    @Column(nullable = false)
    private LocalDateTime startTime;
    //Defined in minutes
    @Column(nullable = false)
    private  int duration;
}
