/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710133
 *
 */

public class InvalidUserException extends Exception {

	private String message;

	public InvalidUserException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
