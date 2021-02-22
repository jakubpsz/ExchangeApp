package com.infoshare.hw4.logic.user_file;

import com.infoshare.hw4.repository.RecordDtoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserFileReaderImpl implements UserFileReader {
    private static final String COMMA_DELIMITER = ",";
    private final List<Record> data = new ArrayList<>();
    private final RecordDtoRepository recordDtoRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private BufferedReader br;

    public UserFileReaderImpl(RecordDtoRepository recordDtoRepository) throws IOException {
        this.recordDtoRepository = recordDtoRepository;
        processingUsersFile("src/main/resources/data_files/DAT_MT_EURUSD_M1_202011.csv");
    }

    @Override
    public void processingUsersFile(String fileName) throws IOException {
        try {
            readFile(fileName);
            log.info("File " + fileName + " loaded");
        } catch (IOException e) {
            log.error("No such file " + e.getMessage());
            throw e;
        } catch (DateTimeParseException e) {
            log.error("Wrong file format");
            throw e;
        }
    }

    private void readFile(String name) throws IOException {
        br = new BufferedReader(new FileReader(name));
        br.readLine();
        readValues();
    }

    private void readValues() throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(COMMA_DELIMITER);
            Record record = createRecord(values);
            RecordDto recordDto = createRecordDto(values);

            //saving records to database it might take a while
            recordDtoRepository.save(recordDto);

            data.add(record);
        }
        log.info("Done loading file");
    }

    private Record createRecord(String[] values) {
        Record record = new Record();
        record.setDate(LocalDate.parse(values[0], dateFormatter));
        record.setTime(LocalTime.parse(values[1], timeFormatter));
        record.setOpen(BigDecimal.valueOf(Double.parseDouble(values[2])));
        record.setHigh(BigDecimal.valueOf(Double.parseDouble(values[3])));
        record.setLow(BigDecimal.valueOf(Double.parseDouble(values[4])));
        record.setClose(BigDecimal.valueOf(Double.parseDouble(values[5])));
        return record;
    }
    private RecordDto createRecordDto(String[] values) {
        RecordDto record = new RecordDto();
        record.setDate(LocalDate.parse(values[0], dateFormatter));
        record.setTime(LocalTime.parse(values[1], timeFormatter));
        record.setOpen(values[2]);
        record.setHigh(values[3]);
        record.setLow(values[4]);
        record.setClose(values[5]);
        return record;
    }

    @Override
    public List<Record> getData() {
        return data;
    }
}
