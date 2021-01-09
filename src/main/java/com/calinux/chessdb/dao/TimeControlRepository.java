package com.calinux.chessdb.dao;

import com.calinux.chessdb.entity.TimeControl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeControlRepository extends JpaRepository<TimeControl, Long> {
}
