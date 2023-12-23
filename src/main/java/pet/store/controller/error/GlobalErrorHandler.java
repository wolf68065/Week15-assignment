package pet.store.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j

public class GlobalErrorHandler {
@ExceptionHandler(NoSuchElementException.class)
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public Map<String, String>handleNoSuchElementException(NoSuchElementException ex){
	log.debug("NoSuchElementException occured: {}", ex.getMessage());
	
	Map<String, String> errorResponse = new HashMap<>();
	errorResponse.put("Message", ex.toString());
	return errorResponse;
}
}
