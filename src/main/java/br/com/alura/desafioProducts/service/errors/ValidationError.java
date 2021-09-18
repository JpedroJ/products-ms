package br.com.alura.desafioProducts.service.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ValidationError {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handle(MethodArgumentNotValidException exception) {
		Map<String, Object> errors = new HashMap<>();
		
		StringBuilder sb = new StringBuilder();
		int status_code = HttpStatus.BAD_REQUEST.value();
		
		exception.getBindingResult().getFieldErrors().forEach(e -> {
			sb.append("O campo " + e.getField()+ " "  + messageSource.getMessage(e, LocaleContextHolder.getLocale()));
			if(exception.getBindingResult().getErrorCount() > 1) {
				sb.append("; ");
			}
		});
		errors.put("status_code", status_code);
		errors.put("message", sb);

		
	
		
		return errors;
	}
}
