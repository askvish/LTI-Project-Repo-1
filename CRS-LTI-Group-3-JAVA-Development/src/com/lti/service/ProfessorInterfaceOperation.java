/**
 * 
 */
package com.lti.service;

import java.util.ArrayList;
import com.lti.bean.Student;

/**
 * @author 10710133
 *
 */
public interface ProfessorInterfaceOperation {
	/**
	 * Adds grade to a student for a particular course
	 * @param studentID
	 * @param courseID
	 * @param grade
	 */
	public void addGrade(int studentID, int courseID, String grade);
	
	/**
	 * Gets list of students enrolled for particular course taken by a professor
	 * @param courseID
	 * @param profID
	 * @return
	 */
	public ArrayList<Student> viewStudentsEnrolled(int courseID, int profID);
}
