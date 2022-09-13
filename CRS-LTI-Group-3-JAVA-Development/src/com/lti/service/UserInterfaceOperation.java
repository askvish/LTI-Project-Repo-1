/**
 * 
 */
package com.lti.service;

import com.lti.bean.Login;
import com.lti.bean.Student;

/**
 * @author 10710133
 *
 */

public interface UserInterfaceOperation {
	/**
	 * Verifies user credential
	 * @param login
	 * @return
	 */
	public boolean verifyCredential(Login login);
	
	/**
	 * Registers a new student
	 * @param student
	 * @param login
	 */
	public void registerStudent(Student student, Login login);
	
	/**
	 * Resets or updates student password
	 * @param username
	 * @param newPassword
	 */
	public void resetPassword(String username, String newPassword);
}
