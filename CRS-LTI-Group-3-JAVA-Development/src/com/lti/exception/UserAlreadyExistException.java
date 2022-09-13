/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710133
 *
 */
public class UserAlreadyExistException extends Exception {

	private String message;

	public UserAlreadyExistException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
