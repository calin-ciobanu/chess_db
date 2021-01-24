package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessUserRepository extends JpaRepository<ChessUser, Long> {

    @Query("select u from ChessUser u where u.username=?1")
    public ChessUser findByUsername(String username);
}