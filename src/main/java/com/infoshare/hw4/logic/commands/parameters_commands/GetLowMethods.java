package com.infoshare.hw4.logic.commands.parameters_commands;

import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.user_file.Record;

import java.math.BigDecimal;
import java.util.List;

public class GetLowMethods extends ParametersCommand {

    protected BigDecimal getDataFromDay(List<Record> data, Parameters parameters) {
        return filterDays(data, parameters)
                .map(Record::getLow)
                .min(BigDecimal::compareTo).orElseThrow();
    }

    protected BigDecimal getDataFromHour(List<Record> data, Parameters parameters) {
        return filterHours(data, parameters)
                .map(Record::getLow)
                .min(BigDecimal::compareTo).orElseThrow();
    }

    protected BigDecimal getDataFromMinute(List<Record> data, Parameters parameters) {
        return filterMinutes(data, parameters)
                .map(Record::getLow)
                .findFirst().orElseThrow();
    }

    protected BigDecimal getDataFromAll(List<Record> data) {
        return data.stream()
                .map(Record::getHigh)
                .min(BigDecimal::compareTo).orElseThrow();
    }
}
