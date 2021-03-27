package com.calinux.chessdb.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedResponseDTO extends ChessUserDTO {

    @NotNull
    @NotEmpty
    private String token;

    @NotNull
    @NotEmpty
    private String refreshToken;
}
