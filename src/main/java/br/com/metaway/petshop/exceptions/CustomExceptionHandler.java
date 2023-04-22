package br.com.metaway.petshop.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String userMessage = messageSource.getMessage("message.invalid_attribute", null, LocaleContextHolder.getLocale());
		String devMessage = ex.getCause().toString();
		List<ErrorResponse> erros = Arrays.asList(new ErrorResponse(userMessage, devMessage, new ArrayList<>()));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
    
    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	
    	
    	List<ErrorResponse> errorList = createErrorList(ex.getBindingResult());
		return handleExceptionInternal(ex, errorList, headers, status, request);
	}
    
    @ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("message.not_found", null, LocaleContextHolder.getLocale());
		String devMessage = ex.toString();
		
		List<ErrorResponse> erros = Arrays.asList(new ErrorResponse(userMessage, devMessage, new ArrayList<>()));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
    
    private List<ErrorResponse> createErrorList(BindingResult bindingResult) {
        List<ErrorResponse> errors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            errors.add(new ErrorResponse(userMessage, developerMessage, new ArrayList<>()));
        }
        return errors;
    }
}