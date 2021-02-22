package com.infoshare.hw4.logic.commands;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Parameters {
    private LocalDate day;
    private int hour = -1;
    private int minute = -1;

    private GetCommandType commandType;

    public Parameters() {
    }

    public Parameters(LocalDate day, int hour, int minute) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }
}
