package mx.com.omarjbq.productos.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Objeto de respuesta de errores.
 * 
 * @author Omar Balbuena
 *
 */
@JsonInclude(Include.NON_NULL)
public class ApiError {
	/**
	 * Fecha en la que ocurrio el error.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;

	/**
	 * Estatus de la solicitud.
	 */
	private HttpStatus status;

	/**
	 * Mensaje.
	 */
	private String message;

	/**
	 * Mensaje detallado del error.
	 */
	private String debugMessage;

	/**
	 * Lista de errores.
	 */
	private List<String> messages;

	/**
	 * Constructor.
	 */
	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	/**
	 * 
	 * @param status codigo http de la solicitud.
	 */
	public ApiError(final HttpStatus status) {
		this();
		timestamp = LocalDateTime.now();
		this.status = status;
	}

	/**
	 * 
	 * @param status codigo http de la solicitud.
	 * @param ex     excepcion.
	 */
	public ApiError(final HttpStatus status, final Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	/**
	 * 
	 * @param status  codigo http de la solicitud.
	 * @param message mensaje de error.
	 * @param ex      excepcion.
	 */
	public ApiError(final HttpStatus status, final String message, final Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	/**
	 * 
	 * @param status  codigo http de la solicitud.
	 * @param message mensaje de error.
	 */
	public ApiError(final HttpStatus status, final String message) {
		this();
		this.status = status;
		this.message = message;
		timestamp = LocalDateTime.now();
	}

	/**
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the debugMessage
	 */
	public String getDebugMessage() {
		return debugMessage;
	}

	/**
	 * @param debugMessage the debugMessage to set
	 */
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
