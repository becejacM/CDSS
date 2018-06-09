package sbnz.ftn.uns.ac.rs.cdss.exceptions;

public class NotUniqueException extends RuntimeException {

	private static final long serialVersionUID = -8216472112867999459L;

	public NotUniqueException() {
		super();
	}

	public NotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotUniqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotUniqueException(String message) {
		super(message);
	}

	public NotUniqueException(Throwable cause) {
		super(cause);
	}
}
