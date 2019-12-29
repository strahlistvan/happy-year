package hu.happyyear.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class DateService {

    public Integer getNextYear() {
        LocalDate now = LocalDate.now();
        return now.getYear()+1;
    }

    public static enum TIME_TENSE { PAST, PRESENT, FUTURE };
    
    public TIME_TENSE getYearTense(Integer year) {
        Integer currentYear = LocalDate.now().getYear();
        if (year < currentYear) {
            return DateService.TIME_TENSE.PAST;
        }
        if (year > currentYear) {
            return DateService.TIME_TENSE.FUTURE;
        }
        return DateService.TIME_TENSE.PRESENT;
    }
    
    public Long daysUntilYear(Integer year) {
        Long diff = 0L;
        LocalDate currentDate = LocalDate.now();
        TIME_TENSE tense = getYearTense(year);
        if (tense == TIME_TENSE.PAST) {
            LocalDate destDate = LocalDate.of(year, 12, 31);
            diff = ChronoUnit.DAYS.between(destDate, currentDate);
        }
        else if (tense == TIME_TENSE.PRESENT) {
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
