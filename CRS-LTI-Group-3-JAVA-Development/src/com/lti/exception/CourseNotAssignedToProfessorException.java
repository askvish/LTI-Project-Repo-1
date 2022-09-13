/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710133
 *
 */
public class CourseNotAssignedToProfessorException extends Exception {

	private String message;

	public CourseNotAssignedToProfessorException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
