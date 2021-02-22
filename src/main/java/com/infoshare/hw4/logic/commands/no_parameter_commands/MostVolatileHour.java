package com.infoshare.hw4.logic.commands.no_parameter_commands;

import com.infoshare.hw4.logic.commands.Operations;
import com.infoshare.hw4.logic.user_file.Record;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MostVolatileHour extends Operations {
    public String run(List<Record> data) {
        Map<String, List<Record>> groupByDateAndHour = groupByDateAndHour(data);
        return findMaxVolatilityTime(groupByDateAndHour);
    }

    private Map<String, List<Record>> groupByDateAndHour(List<Record> data) {
        return data.stream()
                .collect(Collectors.groupingBy(record -> record.getDate().toString()
                        + " " + record.getTime().getHour()));
    }
}
