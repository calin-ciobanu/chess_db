package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessUserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessUserDetailsRepository extends JpaRepository<ChessUserDetail, Long> {
}