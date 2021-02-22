package com.infoshare.hw4.logic.history;

import com.infoshare.hw4.repository.HistoryEntityRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class HistorySaver {
    private List<HistoryEntity> history = new ArrayList<>();
    private final HistoryEntityRepository historyEntityRepository;

    public HistorySaver(HistoryEntityRepository historyEntityRepository) {
        this.historyEntityRepository = historyEntityRepository;
    }

    public void writeToFile() throws IOException {
        String fileName = "log";
        int num = 1;
        String save = fileName + "_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss")) + ".txt";
        File file = new File(save);
        while (file.exists()) {
            save = fileName + (num++) + "_"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss")) + ".txt";
            file = new File(save);
        }

        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file));
        outputWriter.write("Time Command isExecuted");

        for (HistoryEntity historyEntity : history) {
            outputWriter.newLine();
            outputWriter.write(historyEntity.toString());
        }
        outputWriter.flush();
        outputWriter.close();
    }

    public void saveHistoryEntity(String command, boolean isExecuted) {
        HistoryEntity historyEntity = new HistoryEntity(command, isExecuted);
        historyEntity.setLocalDateTime(LocalDateTime.now());
        history.add(historyEntity);
        historyEntityRepository.save(historyEntity);
    }

    public void resetHistory() {
        this.history = new ArrayList<>();
    }
}
