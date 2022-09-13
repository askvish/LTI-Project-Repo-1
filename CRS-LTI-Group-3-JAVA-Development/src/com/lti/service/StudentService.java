package com.lti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.bean.Registration;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.RegistrationDaoImplementation;
import com.lti.dao.StudentDaoImplementation;
import com.lti.exception.CourseLimitExceedException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.StudentNotFoundException;

/**
 * @author 10710133
 *
 */

public class StudentService implements StudentInterfaceOperation {

	public void registerCourses(int studentID) {

	}

	public void addCourse(int studentID, int courseID) {
		try {
			RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
			regDao.registerCourse(studentID, courseID);
		} catch (StudentNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (CourseNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (CourseLimitExceedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void dropCourse(int studentID, int courseID) {
		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		try {
			regDao.deRegisterCourse(studentID, courseID);
		} catch (StudentNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (CourseNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Course> viewEnrolledCourses(int studentID) {
		ArrayList<Course> courseList = null;

		try {
			RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
			courseList = regDao.getStudentCourseList(studentID);
		} catch (StudentNotFoundException e) {
			System.out.println(e.getMessage());
		}

		return courseList;
	}

	public ArrayList<Grade> viewGrades(int studentID) {
		AdminDaoImplementation adminDoa = new AdminDaoImplementation();
		ArrayList<Grade> grades = adminDoa.getGrades(studentID);
		return grades;
	}

	public void payFee(Payment payment) {
		StudentDaoImplementation stuDao = new StudentDaoImplementation();
		stuDao.payFee(payment);
	}
}
