package com.infoshare.hw4.logic.commands.no_parameter_commands;

import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.commands.parameters_commands.VolatilityCommand;
import com.infoshare.hw4.logic.user_file.Record;
import com.infoshare.hw4.logic.commands.Command;
import com.infoshare.hw4.logic.commands.Operations;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AverageHourlyVolatility extends Operations implements Command {
    private final VolatilityCommand volatilityCommand = new VolatilityCommand();

    @Override
    public BigDecimal run(List<Record> data, Parameters parameters) {
        Map<String, List<Record>> groupByDateAndHour = groupByDateAndHour(data);
        List<BigDecimal> volatiles = getVolatilesPerHour(groupByDateAndHour);
        return average(volatiles);
    }

    private Map<String, List<Record>> groupByDateAndHour(List<Record> data) {
        return data.stream()
                .collect(Collectors.groupingBy(record -> record.getDate().toString()
                        + " " + record.getTime().getHour()));
    }

    private List<BigDecimal> getVolatilesPerHour(Map<String, List<Record>> groupByDateAndHour) {
        List<BigDecimal> volatiles = new ArrayList<>();
        for (Map.Entry<String, List<Record>> entry : groupByDateAndHour.entrySet()) {
            volatiles.add(volatilityCommand.getDataFromAll(entry.getValue()));
        }
        return volatiles;
    }
}