package com.lti.service;

import java.util.ArrayList;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;

public interface StudentInterfaceOperation {
	
	/**
	 * 
	 * @param studentID
	 */
	public void registerCourses(int studentID);
	
	/**
	 * Adds a course to student course list
	 * @param studentID
	 * @param courseID
	 */
	public void addCourse(int studentID, int courseID);
	
	/**
	 * Drops a course to student course list
	 * @param studentID
	 * @param courseID
	 */
	public void dropCourse(int studentID, int courseID);
	
	/**
	 * Get list of courses enrolled by the student
	 * @param studentID
	 * @return
	 */
	public ArrayList<Course> viewEnrolledCourses(int studentID);
	
	/**
	 * Get list of grades for a student
	 * @param studentID
	 * @return
	 */
	public ArrayList<Grade> viewGrades(int studentID);
	
	/**
	 * Pays fee for a student
	 * @param payment
	 */
	public void payFee(Payment payment);
}
