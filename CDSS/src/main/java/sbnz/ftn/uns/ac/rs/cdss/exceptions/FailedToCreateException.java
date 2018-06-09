package sbnz.ftn.uns.ac.rs.cdss.exceptions;

public class FailedToCreateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedToCreateException() {
		super();
	}

	public FailedToCreateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FailedToCreateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedToCreateException(String message) {
		super(message);
	}

	public FailedToCreateException(Throwable cause) {
		super(cause);
	}

}
