package com.infoshare.hw4.logic.user_file;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Record {
    private LocalDate date;
    private LocalTime time;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volatility;

    @Override
    public String toString() {
        return open + " " + high.toString() + " "
                + low.toString() + " " + close.toString() + " "
                + volatility.toString();
    }
}
