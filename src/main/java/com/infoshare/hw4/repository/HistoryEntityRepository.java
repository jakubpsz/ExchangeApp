package com.infoshare.hw4.repository;

import com.infoshare.hw4.logic.history.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoryEntityRepository extends JpaRepository<HistoryEntity, UUID> {
}
