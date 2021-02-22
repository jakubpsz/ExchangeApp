package com.infoshare.hw4.logic.commands.parameters_commands;

import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.user_file.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GetDataCommand extends ParametersCommand {

    public static final int EMPTY = 0;

    public ArrayList<Record> run(List<Record> data, Parameters parameters) {
        if (hasOnlyDate(parameters)) {
            return getDataFromDay(data, parameters);
        } else if (hasOnlyDateAndHour(parameters)) {
            return getDataFromHour(data, parameters);
        } else {
            return getDataFromMinute(data, parameters);
        }
    }

    protected ArrayList<Record> getDataFromDay(List<Record> data, Parameters parameters) {
        if (filterDays(data,parameters).toArray().length == EMPTY){
            throw new NoSuchElementException();
        }else {
            return filterDays(data, parameters).collect(Collectors.toCollection(ArrayList::new));
        }
    }

    private ArrayList<Record> getDataFromHour(List<Record> data, Parameters parameters) {
        if (filterHours(data,parameters).toArray().length == EMPTY){
            throw new NoSuchElementException();
        }else {
            return filterHours(data, parameters).collect(Collectors.toCollection(ArrayList::new));
        }
    }

    private ArrayList<Record> getDataFromMinute(List<Record> data, Parameters parameters) {
        if (filterMinutes(data,parameters).toArray().length == EMPTY){
            throw new NoSuchElementException();
        }else {
            return filterMinutes(data, parameters).collect(Collectors.toCollection(ArrayList::new));
        }
    }
}
