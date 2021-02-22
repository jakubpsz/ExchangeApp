package com.infoshare.hw4.service;

import com.infoshare.hw4.logic.commands.CommandBody;
import com.infoshare.hw4.logic.commands.Parameters;
import com.infoshare.hw4.logic.commands.parameters_commands.GetDataCommand;
import com.infoshare.hw4.logic.commands.parameters_commands.VolatilityCommand;
import com.infoshare.hw4.logic.history.HistorySaver;
import com.infoshare.hw4.logic.user_file.Record;
import com.infoshare.hw4.logic.user_file.UserFileReaderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GetCommandServiceImpl implements GetCommandService {
    private final UserFileReaderImpl userFileReaderImpl;
    private final GetDataCommand getDataCommand;
    private final VolatilityCommand volatilityCommand;
    private final HistorySaver historySaver;

    public GetCommandServiceImpl(UserFileReaderImpl userFileReaderImpl, GetDataCommand getDataCommand, VolatilityCommand volatilityCommand, HistorySaver historySaver) {
        this.userFileReaderImpl = userFileReaderImpl;
        this.getDataCommand = getDataCommand;
        this.volatilityCommand = volatilityCommand;
        this.historySaver = historySaver;
    }

    @Override
    public List<String> getGetCommandData(String command) {
        try {
            CommandBody commandBody = new CommandBody(command.toLowerCase());
            ArrayList<Record> records = getDataCommand.run(userFileReaderImpl.getData(), commandBody.getParameters());
            records.forEach(record -> record.setVolatility(getVolatilityForRecord(record)));
            List<String> recordsReady = records.stream()
                    .map(Record::toString)
                    .collect(Collectors.toList());
            historySaver.saveHistoryEntity(command, true);
            return recordsReady;
        } catch (NullPointerException | NoSuchElementException e) {
            log.error("Wrong command " + e.getMessage());
            historySaver.saveHistoryEntity(command, false);
            return new ArrayList<>();
        }

    }

    private BigDecimal getVolatilityForRecord(Record record) {
        return volatilityCommand
                .run(userFileReaderImpl.getData(), new Parameters(
                        record.getDate(),
                        record.getTime().getHour(),
                        record.getTime().getHour()));
    }
}
