package com.infoshare.hw4.logic.commands;

import com.infoshare.hw4.logic.user_file.Record;

import java.math.BigDecimal;
import java.util.List;

public interface Command {
    BigDecimal run(List<Record> data, Parameters parameters);
}
