package com.calinux.chessdb.api.v1.dto;

import com.calinux.chessdb.api.v1.validator.StrongPasswordConstraint;
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
public class SignupRequestDTO extends ChessUserDTO {

    @NotNull
    @NotEmpty
    @StrongPasswordConstraint
    private String password;
}
