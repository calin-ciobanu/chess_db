package com.calinux.chessdb.dao;

import com.calinux.chessdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}