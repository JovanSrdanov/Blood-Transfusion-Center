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

    public static DateRange intersectTwoRanges(DateRange range1, DateRange range2) {
        if(range1.endTime.isBefore(range2.startTime) ||
            range2.endTime.isBefore(range1.startTime)) {
            return null;
        }

        LocalDateTime start = start = range1.startTime;
        LocalDateTime end = start = range1.endTime;

        if(range1.startTime.isEqual(range2.startTime) || range1.startTime.isAfter(range2.startTime)) {
            start = range1.startTime;
        }
        else if(range2.startTime.isAfter(range1.startTime)) {
            start = range2.startTime;
        }

        if(range1.endTime.isEqual(range2.endTime) || range1.endTime.isBefore(range2.endTime)) {
            end = range1.endTime;
        }
        else if(range2.endTime.isBefore(range1.endTime)) {
            end = range2.endTime;
        }

        return new DateRange(start, end);
    }

    public static List<DateRange> intersectTwoList(List<DateRange> l1, List<DateRange> l2) {
        List<DateRange> intersections = new ArrayList<>();

        for(DateRange range1 : l1) {
            for(DateRange range2 : l2) {
                DateRange intersection = DateRange.intersectTwoRanges(range1, range2);
                if(intersection != null) {
                    intersections.add(intersection);
                }
            }
        }

        return intersections;
    }

    public int getDurationMinutes() {
        return (int)ChronoUnit.MINUTES.between(this.startTime, this.endTime);
    }
}
