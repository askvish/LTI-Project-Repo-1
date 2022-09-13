/**
 * 
 */
package com.lti.service;

import java.util.ArrayList;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Login;
import com.lti.bean.Professor;

/**
 * @author 10710133
 *
 */

public interface AdminInterfaceOperation {
	
	/**
	 * Add a professor
	 * @param professor
	 * @param login
	 */
	public void addProfessor(Professor professor, Login login);
	
	/**
	 * Approves registration of a student
	 * @param studentID
	 */
	public void approveStudentRegistration(int studentID);
	
	/**
	 * Adds a course to the course list
	 * @param course
	 */
	public void addCourse(Course course);
	
	/**
	 * Removes a course to the course list
	 * @param courseId
	 */
	public void removeCourse(int courseId);
	
	/**
	 * Generates report card for a particular student
	 * @param studentID
	 * @return
	 */
	public ArrayList<Grade> generateReportCard(int studentID);
}
