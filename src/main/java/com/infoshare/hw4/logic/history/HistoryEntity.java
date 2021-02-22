package com.infoshare.hw4.logic.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "history")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id")
    private UUID id;

    @Column(name = "time")
    private LocalDateTime localDateTime;
    @Column(name = "command")
    private String commandName;
    @Column(name = "isExecuted")
    private boolean isExecuted;

    public HistoryEntity(String command, boolean isExecuted) {
        this.commandName = command;
        this.isExecuted = isExecuted;
    }

    @Override
    public String toString() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")) + " " + commandName + " " + isExecuted;
    }
}
