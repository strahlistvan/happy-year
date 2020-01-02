package hu.happyyear.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class DateService {

    public static enum TimeTense { PAST, PRESENT, FUTURE };

    public Integer getNextYear() {
        LocalDate now = LocalDate.now();
        return now.getYear()+1;
    }

    public TimeTense getYearTense(Integer year) {
        Integer currentYear = LocalDate.now().getYear();
        if (year < currentYear) {
            return DateService.TimeTense.PAST;
        }
        if (year > currentYear) {
            return DateService.TimeTense.FUTURE;
        }
        return DateService.TimeTense.PRESENT;
    }
    
    public Long daysUntilYear(Integer year) {
        Long diff = 0L;
        LocalDate currentDate = LocalDate.now();
        TimeTense tense = getYearTense(year);
        if (tense == TimeTense.PAST) {
            LocalDate destDate = LocalDate.of(year, 12, 31);
            diff = ChronoUnit.DAYS.between(destDate, currentDate);
        }
        else if (tense == TimeTense.PRESENT) {
            LocalDate destDate = LocalDate.of(year, 1, 1);
            diff = ChronoUnit.DAYS.between(destDate, currentDate);
        }
        else {
            LocalDate destDate = LocalDate.of(year, 1, 1);
            diff = ChronoUnit.DAYS.between(currentDate, destDate);
        }
        return diff;
    }
}
