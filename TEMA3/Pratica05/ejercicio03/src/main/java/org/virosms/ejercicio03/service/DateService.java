package org.virosms.ejercicio03.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.virosms.ejercicio03.model.DateEntity;

import java.time.LocalDate;

@Service
public class DateService {

    private LocalDate date = LocalDate.now();

    private DateEntity createDateEntity(LocalDate date) {
        DateEntity dateEntity = new DateEntity();
        dateEntity.setYear(date.getYear());
        dateEntity.setMonth(date.getMonthValue());
        dateEntity.setDay(date.getDayOfMonth());
        return dateEntity;
    }

    public ResponseEntity<DateEntity> getDate() {
        return ResponseEntity.ok(createDateEntity(date));
    }

    public ResponseEntity<DateEntity> getDatePlusDays(int days) {
        return ResponseEntity.ok(createDateEntity(date.plusDays(days)));
    }

    public ResponseEntity<DateEntity> changeDate(DateEntity dateEntity) {
        date = LocalDate.of(dateEntity.getYear(), dateEntity.getMonth(),dateEntity.getDay());
        return new ResponseEntity<>(createDateEntity(date), HttpStatus.CREATED);
    }
}