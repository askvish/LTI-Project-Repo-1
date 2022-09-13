/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710133
 *
 */
public class CourseFoundException extends Exception {
	private String message;

	public CourseFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
