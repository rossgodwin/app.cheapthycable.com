package com.gwn.xcbl.data.model;

public class PermissionDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6445037353636408807L;

	public PermissionDeniedException(String message) {
		super(message);
	}

	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}
}
