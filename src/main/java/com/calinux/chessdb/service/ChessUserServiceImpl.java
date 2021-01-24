package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.UserResponseDTO;
import com.calinux.chessdb.api.v1.exception.ChessUserNotFoundException;
import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.repository.ChessUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ChessUserServiceImpl implements ChessUserService {

    private final ChessUserRepository chessUserRepository;

    @Override
    public ChessUser create(ChessUser chessUser) {
        ChessUser savedUser = chessUserRepository.save(chessUser);
        return savedUser;
    }

    @Override
    public ChessUser getById(Long id) {
        Optional<ChessUser> chessUserOptional = chessUserRepository.findById(id);
        if (chessUserOptional.isPresent()) {
            return chessUserOptional.get();
        } else {
            throw(new ChessUserNotFoundException(id));
        }
    }
}
