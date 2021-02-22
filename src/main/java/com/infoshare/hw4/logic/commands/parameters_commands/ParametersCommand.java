package com.infoshare.hw4.logic.commands.parameters_commands;

import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.user_file.Record;

import java.util.List;
import java.util.stream.Stream;

public abstract class ParametersCommand {

    public static final int NO_PARAMETER = -1;

    protected Stream<Record> filterDays(List<Record> data, Parameters parameters){
        return data.stream()
                .filter(record -> record.getDate().equals(parameters.getDay()));
    }
    
    protected Stream<Record> filterHours(List<Record> data, Parameters parameters) {
        return data.stream()
                .filter(record -> record.getDate().equals(parameters.getDay()))
                .filter(record -> record.getTime().getHour() == parameters.getHour());
    }

    protected Stream<Record> filterMinutes(List<Record> data, Parameters parameters) {
        return data.stream()
                .filter(record -> record.getDate().equals(parameters.getDay()))
                .filter(record -> record.getTime().getHour() == parameters.getHour())
                .filter(record -> record.getTime().getMinute() == parameters.getMinute());
    }
    protected boolean hasOnlyDate(Parameters parameters){
        return (parameters.getHour() == NO_PARAMETER && parameters.getMinute() == NO_PARAMETER);
    }
    protected boolean hasOnlyDateAndHour(Parameters parameters){
        return parameters.getMinute() == NO_PARAMETER;
    }
}
