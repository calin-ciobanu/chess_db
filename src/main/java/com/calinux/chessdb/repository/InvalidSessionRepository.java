package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.InvalidSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidSessionRepository extends JpaRepository<InvalidSession, Long> {
}
