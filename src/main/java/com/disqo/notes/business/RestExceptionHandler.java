package com.disqo.notes.business;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.disqo.notes.business.common.entity.EntityNotFoundException;
import com.disqo.notes.business.common.entity.ErrorResponseModel;

@ControllerAdvice
public class RestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        logger.error(String.valueOf(ex));
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        HttpStatus status;

        if (ex instanceof EntityNotFoundException) {
            errorResponseModel.setTitle("Entity not found");
            status = HttpStatus.NOT_FOUND;
        } else {
            errorResponseModel.setTitle("Internal Server Error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        errorResponseModel.setDetail(ex.getMessage());
        return new ResponseEntity<>(errorResponseModel, status);
    }

}
