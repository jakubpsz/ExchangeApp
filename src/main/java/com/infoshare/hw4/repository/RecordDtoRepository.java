package com.infoshare.hw4.repository;

import com.infoshare.hw4.logic.user_file.RecordDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordDtoRepository extends JpaRepository<RecordDto, UUID> {
}
