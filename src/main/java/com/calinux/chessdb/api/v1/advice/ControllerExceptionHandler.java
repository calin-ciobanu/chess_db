package com.calinux.chessdb.api.v1.advice;

import com.calinux.chessdb.api.v1.dto.ErrorResponseDTO;
import com.calinux.chessdb.api.v1.exception.GameImportException;
import com.calinux.chessdb.api.v1.exception.JobNotFoundException;
import org.quartz.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameImportException.class)
    protected ResponseEntity<ErrorResponseDTO> handleGameImportException(GameImportException e) {
        return buildGenericResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JobNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleJobNotFoundException(JobNotFoundException e) {
        return buildGenericResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDTO> buildGenericResponseEntity(Exception e, HttpStatus status) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                e.getLocalizedMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<ErrorResponseDTO>(errorResponse, status);
    }

}
