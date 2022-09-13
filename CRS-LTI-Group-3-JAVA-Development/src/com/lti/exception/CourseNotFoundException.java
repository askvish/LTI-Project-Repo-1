/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710137
 *
 */
public class CourseNotFoundException extends Exception {

	private String message;

	public CourseNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
