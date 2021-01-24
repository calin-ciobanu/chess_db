package com.calinux.chessdb.api.v1.controller;

import com.calinux.chessdb.api.v1.UrlHelper;
import com.calinux.chessdb.api.v1.dto.UserResponseDTO;
import com.calinux.chessdb.api.v1.dto.SignupRequestDTO;
import com.calinux.chessdb.api.v1.facade.ChessUserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UrlHelper.V1 + UrlHelper.USERS)
@RequiredArgsConstructor
public class ChessUserController {

    private final ChessUserFacade chessUserFacade;

    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        UserResponseDTO userResponseDTO = chessUserFacade.create(signupRequestDTO);

        return userResponseDTO;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = chessUserFacade.getById(id);

        return userResponseDTO;
    }

}
