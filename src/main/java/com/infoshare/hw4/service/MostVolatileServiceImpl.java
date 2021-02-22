package com.infoshare.hw4.service;

import com.infoshare.hw4.logic.commands.no_parameter_commands.MostVolatileDay;
import com.infoshare.hw4.logic.commands.no_parameter_commands.MostVolatileHour;
import com.infoshare.hw4.logic.history.HistorySaver;
import com.infoshare.hw4.logic.user_file.UserFileReaderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MostVolatileServiceImpl implements MostVolatileService {
    private final UserFileReaderImpl userFileReaderImpl;
    private final MostVolatileDay mostVolatileDay;
    private final MostVolatileHour mostVolatileHour;
    private final HistorySaver historySaver;

    public MostVolatileServiceImpl(UserFileReaderImpl userFileReaderImpl, MostVolatileDay mostVolatileDay, MostVolatileHour mostVolatileHour, HistorySaver historySaver) {
        this.userFileReaderImpl = userFileReaderImpl;
        this.mostVolatileDay = mostVolatileDay;
        this.mostVolatileHour = mostVolatileHour;
        this.historySaver = historySaver;
    }

    @Override
    public String getData(String type) {
        if (type.equals("day")) {
            historySaver.saveHistoryEntity("most_volatile_" + type, true);
            return mostVolatileDay.run(userFileReaderImpl.getData());
        } else if (type.equals("hour")) {
            historySaver.saveHistoryEntity("most_volatile_" + type, true);
            return mostVolatileHour.run(userFileReaderImpl.getData());
        } else {
            historySaver.saveHistoryEntity("most_volatile_" + type, false);
            log.error("Wrong command type");
            return "Wrong command type";
        }
    }
}
