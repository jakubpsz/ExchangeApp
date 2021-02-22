package com.infoshare.hw4.logic.commands;

import com.infoshare.hw4.logic.commands.parameters_commands.VolatilityCommand;
import com.infoshare.hw4.logic.user_file.Record;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public abstract class Operations {

    private final VolatilityCommand volatilityCommand = new VolatilityCommand();

    protected BigDecimal average(List<BigDecimal> bigDecimals) {
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;
        for (BigDecimal bigDecimal : bigDecimals) {
            if (null != bigDecimal) {
                sum = sum.add(bigDecimal);
                count++;
            }
        }
        return sum.divide(new BigDecimal(count), RoundingMode.HALF_UP);
    }

    protected String findMaxVolatilityTime(Map<String, List<Record>> groupByDateAndHour) {
        BigDecimal max = BigDecimal.ZERO;
        String maxDateTime = null;
        BigDecimal current;
        for (Map.Entry<String, List<Record>> entry : groupByDateAndHour.entrySet()) {
            current = volatilityCommand.getDataFromAll(entry.getValue());
            if (current.compareTo(max) > 0) {
                max = current;
                maxDateTime = entry.getKey();
            }
        }
        return maxDateTime;
    }
}
