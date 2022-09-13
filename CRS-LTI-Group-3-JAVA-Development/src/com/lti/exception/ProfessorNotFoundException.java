/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710133
 *
 */
public class ProfessorNotFoundException extends Exception {

	private String message;

	public ProfessorNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
