package com.calinux.chessdb.api.v1.controller;

import com.calinux.chessdb.api.v1.UrlHelper;
import com.calinux.chessdb.api.v1.dto.AuthenticatedResponseDTO;
import com.calinux.chessdb.api.v1.dto.ChessUserDTO;
import com.calinux.chessdb.api.v1.dto.SignupRequestDTO;
import com.calinux.chessdb.api.v1.dto.SignupResponseDTO;
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
    public SignupResponseDTO create(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        SignupResponseDTO signupResponseDTO = chessUserFacade.create(signupRequestDTO);

        return signupResponseDTO;
    }

    @GetMapping("/{id}")
    public ChessUserDTO getUserById(@PathVariable Long id) {
        ChessUserDTO authenticatedResponseDTO = chessUserFacade.getById(id);

        return authenticatedResponseDTO;
    }

}
