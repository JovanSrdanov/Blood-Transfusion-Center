package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class DateRange {
    public DateRange(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Column(nullable = false)
    private LocalDateTime startTime;
    //Defined in minutes
    @Column(nullable = false)
    private LocalDateTime endTime;

    public static List<DateRange> subtractFromBigRange(DateRange bigRange, List<DateRange> smallRanges) {
        List<DateRange> ranges = new ArrayList<>();

        //Ako nema malih rangeva dodaj samo jedan veliki
        if (smallRanges.size() == 0) {
            ranges.add(new DateRange(bigRange.startTime, bigRange.endTime));
            return ranges;
        }

        //Ako ima mesta na pocetku dodaj pocetni date range
        if(bigRange.startTime.isBefore(smallRanges.get(0).startTime)) {
            ranges.add(new DateRange(bigRange.startTime, smallRanges.get(0).startTime));
        }

        //Uzimamo praznine izmedju zauzetih dateRange
        for(int i = 0; i < smallRanges.size() - 1; i++) {
            LocalDateTime first = smallRanges.get(i).getEndTime();
            LocalDateTime second = smallRanges.get(i+1).getStartTime();

            if(!first.equals(second))
                ranges.add(new DateRange(first, second));
        }

        //Ako ima mesta na kraju dodaj
        DateRange lastSmallRange = smallRanges.get(smallRanges.size()-1);
        if(bigRange.endTime.isAfter(lastSmallRange.endTime)) {
            ranges.add(new DateRange(lastSmallRange.endTime, bigRange.endTime));
        }

        return ranges;
    }

    public static List<DateRange> splitBigRangeIntoSmallRanges(DateRange bigRange, int durationMinutes) {
        List<DateRange> ranges = new ArrayList<>();
        if(bigRange.getDurationMinutes() < durationMinutes) {
            return ranges;
        }

        int durationLeft = bigRange.getDurationMinutes();
        LocalDateTime start = bigRange.startTime;
        LocalDateTime end = bigRange.startTime.plusMinutes(durationMinutes);

        while(durationLeft >= durationMinutes) {
            ranges.add(new DateRange(start, end));

            start = end;
            end = start.plusMinutes(durationMinutes);
            durationLeft -= durationMinutes;
        }

        return ranges;
    }

    public int getDurationMinutes() {
        return (int)ChronoUnit.MINUTES.between(this.startTime, this.endTime);
    }
}
