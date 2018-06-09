package sbnz.ftn.uns.ac.rs.cdss.exceptions;

public class NotExistsException extends RuntimeException{

	private static final long serialVersionUID = -8834140594803763637L;

	public NotExistsException() {
        super();
    }

    public NotExistsException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistsException(String message) {
        super(message);
    }

    public NotExistsException(Throwable cause) {
        super(cause);
    }
}
