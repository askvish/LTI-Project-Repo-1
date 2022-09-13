package com.lti.service;

import java.util.ArrayList;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Login;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.ProfessorDaoImplementation;
import com.lti.dao.RegistrationDaoImplementation;
import com.lti.dao.StudentDaoImplementation;
import com.lti.dao.UserDaoImplementation;
import com.lti.exception.CourseFoundException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.exception.UserAlreadyExistException;

public class AdminService implements AdminInterfaceOperation {
	
	public void addProfessor(Professor professor, Login login) {
		ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();
		int id = profDao.addProfessor(professor);

		login.setUserID(id);

		try {
			UserDaoImplementation userDao = new UserDaoImplementation();
			userDao.createNewUser(login);
		} catch (UserAlreadyExistException e) {
			System.out.println(e.getMessage());
		}
	}

	public void approveStudentRegistration(int studentID) {
		StudentDaoImplementation stuDao = new StudentDaoImplementation();
		try {
			stuDao.approveStudent(studentID);
		} catch (StudentNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void addCourse(Course course) {
		AdminDaoImplementation adminDao = new AdminDaoImplementation();

		try {
			adminDao.addCourse(course);
		} catch (CourseFoundException e) {
			System.out.println("\n\t" + e.getMessage());
			return;
		} catch (ProfessorNotFoundException e) {
			System.out.println("\n\t" + e.getMessage());
			return;
		}
	}

	public void removeCourse(int courseId) {
		AdminDaoImplementation adminDao = new AdminDaoImplementation();
		adminDao.removeCourse(courseId);
	}

	public ArrayList<Grade> generateReportCard(int studentID) {
		AdminDaoImplementation adminDoa = new AdminDaoImplementation();
		ArrayList<Grade> grades = adminDoa.getGrades(studentID);
		return grades;
	}
}
