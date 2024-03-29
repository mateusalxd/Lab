package br.com.mateusalxd.unilab.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mateusalxd.unilab.resource.dto.ErroFormDTO;

@RestControllerAdvice
public class ErroFormHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroFormDTO> getMethodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
		List<ErroFormDTO> errosDTO = new ArrayList<>();

		List<FieldError> camposErro = exception.getBindingResult().getFieldErrors();
		camposErro.forEach(erro -> {
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			errosDTO.add(new ErroFormDTO(erro.getField(), mensagem));
		});

		return errosDTO;
	}

}
