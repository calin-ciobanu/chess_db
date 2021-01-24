package com.calinux.chessdb.api.v1.advice;

import com.calinux.chessdb.api.v1.dto.ErrorResponseDTO;
import com.calinux.chessdb.api.v1.exception.ChessUserNotFoundException;
import com.calinux.chessdb.api.v1.exception.GameImportException;
import com.calinux.chessdb.api.v1.exception.JobNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameImportException.class)
    protected ResponseEntity<ErrorResponseDTO> handleGameImportException(GameImportException e) {
        return buildGenericResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JobNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleJobNotFoundException(JobNotFoundException e) {
        return buildGenericResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChessUserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleJobNotFoundException(ChessUserNotFoundException e) {
        return buildGenericResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDTO> buildGenericResponseEntity(Exception e, HttpStatus status) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                e.getLocalizedMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

}
