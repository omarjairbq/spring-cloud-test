package mx.com.omarjbq.productos.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador de excepciones.
 * 
 * @author Omar Balbuena
 *
 */
@RestControllerAdvice
public class HttpExceptionHandler {

	/**
	 * Maneja la excepción.
	 *
	 * @param ex EntityNotFoundException
	 * @return ApiError
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	protected ApiError handleEntityNotFound(final EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(String.format("El recurso no existe {id: %s}", ex.getMessage()));

		return apiError;
	}

	/**
	 * Maneja la excepción.
	 *
	 * @param ex DataIntegrityViolationException
	 * @return ApiError
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ApiError handleEntityNotFound(final DataIntegrityViolationException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Solicitud Invalida");

		return apiError;
	}
}
