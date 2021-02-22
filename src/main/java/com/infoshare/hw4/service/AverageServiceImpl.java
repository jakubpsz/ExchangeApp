package com.infoshare.hw4.service;

import com.infoshare.hw4.logic.commands.Command;
import com.infoshare.hw4.logic.commands.CommandBody;
import com.infoshare.hw4.logic.commands.no_parameter_commands.AverageDailyVolatility;
import com.infoshare.hw4.logic.commands.no_parameter_commands.AverageHourlyVolatility;
import com.infoshare.hw4.logic.commands.no_parameter_commands.AverageMinutelyVolatility;
import com.infoshare.hw4.logic.history.HistorySaver;
import com.infoshare.hw4.logic.user_file.UserFileReaderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AverageServiceImpl implements AverageService {
    private final Map<String, Command> commandMap = new HashMap<>();
    private final UserFileReaderImpl userFileReaderImpl;
    private final HistorySaver historySaver;

    public AverageServiceImpl(UserFileReaderImpl userFileReaderImpl, HistorySaver historySaver) {
        this.userFileReaderImpl = userFileReaderImpl;
        this.historySaver = historySaver;
        addCommands();
    }

    @Override
    public BigDecimal getAverage(String command) {
        try {
            CommandBody commandBody = new CommandBody(command.toLowerCase());
            BigDecimal commandExecution = commandMap.get(commandBody.getName()).run(userFileReaderImpl.getData(), commandBody.getParameters());
            historySaver.saveHistoryEntity(command, true);
            return commandExecution;
        } catch (NullPointerException e) {
            log.error("Wrong command " + e.getMessage());
            historySaver.saveHistoryEntity(command, false);
            return BigDecimal.valueOf(-1);
        }
    }

    private void addCommands() {
        commandMap.put("average_minutely_volatility", new AverageMinutelyVolatility());
        commandMap.put("average_hourly_volatility", new AverageHourlyVolatility());
        commandMap.put("average_daily_volatility", new AverageDailyVolatility());
    }
}
