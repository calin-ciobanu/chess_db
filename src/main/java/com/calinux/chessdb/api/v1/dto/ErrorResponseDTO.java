package com.calinux.chessdb.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {
    private int status;
    private String message;
    private Long timestamp;
}
