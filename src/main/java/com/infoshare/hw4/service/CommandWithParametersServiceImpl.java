package com.infoshare.hw4.service;

import com.infoshare.hw4.logic.commands.Command;
import com.infoshare.hw4.logic.commands.CommandBody;
import com.infoshare.hw4.logic.commands.parameters_commands.GetWithTypeCommand;
import com.infoshare.hw4.logic.commands.parameters_commands.VolatilityCommand;
import com.infoshare.hw4.logic.history.HistorySaver;
import com.infoshare.hw4.logic.user_file.UserFileReaderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class CommandWithParametersServiceImpl implements CommandWithParametersService {
    private final Map<String, Command> commandMap = new HashMap<>();
    private final UserFileReaderImpl userFileReaderImpl;
    private final HistorySaver historySaver;

    public CommandWithParametersServiceImpl(UserFileReaderImpl userFileReaderImpl, HistorySaver historySaver) {
        this.userFileReaderImpl = userFileReaderImpl;
        this.historySaver = historySaver;
        addCommands();
    }

    @Override
    public BigDecimal getParametersData(String command) {
        try {
            CommandBody commandBody = new CommandBody(command.toLowerCase());
            BigDecimal commandExecuted = commandMap.get(commandBody.getName()).run(userFileReaderImpl.getData(), commandBody.getParameters());
            historySaver.saveHistoryEntity(command, true);
            return commandExecuted;
        } catch (NullPointerException | NoSuchElementException e) {
            log.error("Wrong command " + e.getMessage());
            historySaver.saveHistoryEntity(command, false);
            return BigDecimal.valueOf(-1);
        }
    }

    private void addCommands() {
        commandMap.put("get low", new GetWithTypeCommand());
        commandMap.put("get close", new GetWithTypeCommand());
        commandMap.put("get high", new GetWithTypeCommand());
        commandMap.put("get open", new GetWithTypeCommand());
        commandMap.put("volatility", new VolatilityCommand());
    }
}
