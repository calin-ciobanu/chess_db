package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username=?1")
    public User findByUsername(String username);
}