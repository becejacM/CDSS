package sbnz.ftn.uns.ac.rs.cdss.exceptions;

public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 8522636468097712781L;

	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistsException(String message) {
		super(message);
	}

	public AlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
