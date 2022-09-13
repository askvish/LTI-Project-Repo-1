/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710133
 *
 */
public class CourseLimitExceedException extends Exception {

	private String message;

	public CourseLimitExceedException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
