package com.calinux.chessdb.security.auth.service;

import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.repository.ChessUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ChessUserRepository chessUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ChessUser chessUser = chessUserRepository.findByUsername(username);

        if (chessUser == null) {
            return new User(Strings.EMPTY, Strings.EMPTY, Collections.emptyList());
        }

        return new User(chessUser.getUsername(), chessUser.getPassword(), Collections.emptyList());
    }
}
