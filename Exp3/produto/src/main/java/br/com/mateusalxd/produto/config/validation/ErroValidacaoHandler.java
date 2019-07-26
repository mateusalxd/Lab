package br.com.mateusalxd.produto.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroValidacaoFormularioDTO> validarParametros(MethodArgumentNotValidException notValidException) {
		List<ErroValidacaoFormularioDTO> dto = new ArrayList<>();
		List<FieldError> fieldErrors = notValidException.getBindingResult().getFieldErrors();
		fieldErrors.forEach(error -> {
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			dto.add(new ErroValidacaoFormularioDTO(error.getField(), mensagem));
		});

		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErroValidacaoFormularioDTO validarFormato(HttpMessageNotReadableException notReadableException) {
		return new ErroValidacaoFormularioDTO(null, notReadableException.getLocalizedMessage());
	}

}
