package com.infoshare.hw4.logic.commands.no_parameter_commands;

import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.user_file.Record;
import com.infoshare.hw4.logic.commands.Command;
import com.infoshare.hw4.logic.commands.Operations;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AverageMinutelyVolatility extends Operations implements Command {
    @Override
    public BigDecimal run(List<Record> data, Parameters parameters) {
        List<BigDecimal> volatiles = new ArrayList<>();
        data.forEach(record -> volatiles.add(record.getHigh().subtract(record.getLow())));
        return average(volatiles);
    }
}
