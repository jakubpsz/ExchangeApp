package com.infoshare.hw4.logic.commands.parameters_commands;

import com.infoshare.hw4.logic.commands.Command;
import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.user_file.Record;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class VolatilityCommand extends ParametersCommand implements Command {
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
        return getHighMethods.getDataFromDay(data, parameters)
                .subtract(getLowMethods.getDataFromDay(data, parameters));
    }

    private BigDecimal getDataFromHour(List<Record> data, Parameters parameters) {
        return getHighMethods.getDataFromHour(data, parameters)
                .subtract(getLowMethods.getDataFromHour(data, parameters));
    }

    private BigDecimal getDataFromMinute(List<Record> data, Parameters parameters) {
        return getHighMethods.getDataFromMinute(data, parameters)
                .subtract(getLowMethods.getDataFromMinute(data, parameters));
    }

    public BigDecimal getDataFromAll(List<Record> data) {
        return getHighMethods.getDataFromAll(data)
                .subtract(getLowMethods.getDataFromAll(data));
    }
}
