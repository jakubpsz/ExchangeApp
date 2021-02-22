package com.infoshare.hw4.service;

import com.infoshare.hw4.logic.history.HistoryEntity;
import com.infoshare.hw4.logic.history.HistorySaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {
    private final HistorySaver historySaver;

    public HistoryServiceImpl(HistorySaver historySaver) {
        this.historySaver = historySaver;
    }

    @Override
    public boolean save() {
        try {
            historySaver.writeToFile();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean reset() {
        try {
            historySaver.writeToFile();
            historySaver.resetHistory();
            return true;
        } catch (NullPointerException | IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<HistoryEntity> getHistory() {
        return historySaver.getHistory();
    }
}
