package com.infoshare.hw4.logic.user_file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "record")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordDto {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id")
    private UUID id;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private LocalTime time;
    @Column(name = "open")
    private String open;
    @Column(name = "high")
    private String high;
    @Column(name = "low")
    private String low;
    @Column(name = "close")
    private String close;
}
