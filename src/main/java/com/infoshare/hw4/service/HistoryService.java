package com.infoshare.hw4.service;

import com.infoshare.hw4.logic.history.HistoryEntity;

import java.util.List;

public interface HistoryService {
    boolean save();

    boolean reset();

    List<HistoryEntity> getHistory();
}
