package com.company.rewardspoints.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Report {
    private static final
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Report(Long customerId, Long points) {
        this.customerId = customerId;
        this.points = points;
    }
    public Report(String date, Long customerId, Long points) throws ParseException {
        this.date = simpleDateFormat.parse(date);
        this.customerId = customerId;
        this.points = points;
    }
    Date date;
    Long customerId;
    Long points;
}
