package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkingHours {
    @Column(nullable = false)
    private int startHours;

    @Column(nullable = false)
    private int startMinutes;

    @Column(nullable = false)
    private int endHours;

    @Column(nullable = false)
    private int endMinutes;

    public boolean isValid()
    {
        if(startHours >23 || startHours < 0) return false;
        if(endHours >23 || endHours < 0) return false;
        if(startMinutes >59 || startMinutes < 0) return false;
        if(endMinutes >59 || endMinutes < 0) return false;

        return  true;
    }
}
