package com.lti.service;

import java.util.Scanner;

import com.lti.bean.Login;
import com.lti.bean.Student;
import com.lti.dao.StudentDaoImplementation;
import com.lti.dao.UserDaoImplementation;
import com.lti.exception.InvalidUserException;
import com.lti.exception.UserAlreadyExistException;

/**
 * @author 10710133
 *
 */

public class UserService implements UserInterfaceOperation {
	
	public boolean verifyCredential(Login login) {
		
		boolean result = false;
		
		UserDaoImplementation userDao = new UserDaoImplementation();

		try {
			userDao.validateUser(login.getUsername(), login.getPassword(), login.getRole());
			result = true;
		} catch (InvalidUserException e) {
			
		}
		
		return result;
	}
	
	public void registerStudent(Student student, Login login) {

		StudentDaoImplementation studentDao = new StudentDaoImplementation();
		int id = studentDao.addStudent(student);

		login.setUserID(id);

		UserDaoImplementation userDao = new UserDaoImplementation();

		try {
			userDao.createNewUser(login);
		} catch (UserAlreadyExistException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void resetPassword(String username, String newPassword) {
		UserDaoImplementation userDao = new UserDaoImplementation();
		userDao.updatePassword(username, newPassword);
	}
	
	
}
