package sbnz.ftn.uns.ac.rs.cdss.exceptions;

public class ValidationFormException extends RuntimeException {

	private static final long serialVersionUID = -3436599474961095597L;

	public ValidationFormException() {
		super();
	}

	public ValidationFormException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidationFormException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationFormException(String message) {
		super(message);
	}

	public ValidationFormException(Throwable cause) {
		super(cause);
	}

}
