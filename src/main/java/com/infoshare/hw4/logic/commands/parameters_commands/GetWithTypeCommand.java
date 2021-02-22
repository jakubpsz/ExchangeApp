package com.infoshare.hw4.logic.commands.parameters_commands;

import com.infoshare.hw4.logic.user_file.Record;
import com.infoshare.hw4.logic.commands.Command;
import com.infoshare.hw4.logic.commands.Parameters;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GetWithTypeCommand extends ParametersCommand implements Command {
    GetHighMethods getHighMethods = new GetHighMethods();
    GetLowMethods getLowMethods = new GetLowMethods();

    @Override
    public BigDecimal run(List<Record> data, Parameters parameters) {
        if (hasOnlyDate(parameters)) {
            return getDataFromDay(data, parameters);
        } else if (hasOnlyDateAndHour(parameters)) {
            return getDataFromHour(data, parameters);
        } else {
            return getDataFromMinute(data, parameters);
        }
    }

    private BigDecimal getDataFromDay(List<Record> data, Parameters parameters) {
        return switch (parameters.getCommandType()) {
            case CLOSE -> filterDays(data, parameters)
                    .map(Record::getClose)
                    .reduce((first, second) -> second).orElseThrow();
            case OPEN -> filterDays(data, parameters)
                    .map(Record::getOpen)
                    .findFirst().orElseThrow();
            case HIGH -> getHighMethods.getDataFromDay(data, parameters);
            case LOW -> getLowMethods.getDataFromDay(data, parameters);
        };
    }

    private BigDecimal getDataFromHour(List<Record> data, Parameters parameters) {
        return switch (parameters.getCommandType()) {
            case CLOSE -> filterHours(data, parameters)
                    .map(Record::getClose)
                    .reduce((first, second) -> second).orElseThrow();
            case OPEN -> filterHours(data, parameters)
                    .map(Record::getOpen)
                    .findFirst().orElseThrow();
            case HIGH -> getHighMethods.getDataFromHour(data, parameters);
            case LOW -> getLowMethods.getDataFromHour(data, parameters);
        };
    }

    private BigDecimal getDataFromMinute(List<Record> data, Parameters parameters) {
        return switch (parameters.getCommandType()) {
            case CLOSE -> filterMinutes(data, parameters)
                    .map(Record::getClose)
                    .reduce((first, second) -> second).orElseThrow();
            case OPEN -> filterMinutes(data, parameters)
                    .map(Record::getOpen)
                    .findFirst().orElseThrow();
            case HIGH -> getHighMethods.getDataFromMinute(data, parameters);
            case LOW -> getLowMethods.getDataFromMinute(data, parameters);
        };
    }
}
