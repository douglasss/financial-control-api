package br.com.douglass.finances.controller;

import br.com.douglass.finances.controller.dto.RestErrorMessageDTO;
import br.com.douglass.finances.controller.dto.RestFieldErrorDTO;
import br.com.douglass.finances.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Iterator;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionsControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorMessageDTO handleException(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return new RestErrorMessageDTO(HttpStatus.BAD_REQUEST.value(),
                "Internal server error",
                request.getRequestURI());
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestErrorMessageDTO handleBusinessException(BusinessException ex,
                                                       HttpServletRequest request) {
        return new RestErrorMessageDTO(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler({MethodNotAllowedException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RestErrorMessageDTO handleMethodNotAllowedException(Exception ex, HttpServletRequest request) {
        return new RestErrorMessageDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorMessageDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                     HttpServletRequest request) {
        var restErrorMessage = new RestErrorMessageDTO(HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                request.getRequestURI());

        for (ObjectError globalError : ex.getGlobalErrors()) {
            restErrorMessage.addFieldError(new RestFieldErrorDTO("root",
                    globalError.getDefaultMessage()));
        }
        for (FieldError fieldError : ex.getFieldErrors()) {
            restErrorMessage.addFieldError(new RestFieldErrorDTO(fieldError.getField(),
                    fieldError.getDefaultMessage()));
        }

        return restErrorMessage;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorMessageDTO handleConstraintViolationException(ConstraintViolationException ex,
                                                                  HttpServletRequest request) {
        var restErrorMessage = new RestErrorMessageDTO(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI());

        for (ConstraintViolation<?> fieldError : ex.getConstraintViolations()) {
            log.info(fieldError.toString());
            log.info(fieldError.getClass().toString());
            log.info(fieldError.getRootBeanClass().toString());
            Iterator<Path.Node> nodeIterator = fieldError.getPropertyPath().iterator();
            Path.Node fieldNode = nodeIterator.next();
            while (nodeIterator.hasNext()) {
                fieldNode = nodeIterator.next();
            }

            restErrorMessage.addFieldError(new RestFieldErrorDTO(
                    Optional.ofNullable(fieldNode)
                            .map(Path.Node::getName)
                            .orElse(""),
                    fieldError.getMessage())
            );
        }

        return restErrorMessage;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorMessageDTO handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return new RestErrorMessageDTO(
                HttpStatus.BAD_REQUEST.value(),
                Optional.ofNullable(ex.getRootCause())
                        .map(Throwable::getMessage)
                        .orElse(ex.getMessage()),
                request.getRequestURI());
    }
}
