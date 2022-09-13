package com.lti.service;

import java.util.ArrayList;

import com.lti.bean.Student;
import com.lti.dao.ProfessorDaoImplementation;
import com.lti.exception.CourseNotAssignedToProfessorException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.StudentNotFoundException;

/**
 * @author 10710133
 *
 */

public class ProfessorService implements ProfessorInterfaceOperation {

	public void addGrade(int studentID, int courseID, String grade) {

		ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();
		try {
			profDao.addGrade(studentID, courseID, grade);
		} catch (StudentNotFoundException e) {
			System.out.println("\n\t" + e.getMessage());
		} catch (CourseNotFoundException e) {
			System.out.println("\n\t" + e.getMessage());
		}
	}

	public ArrayList<Student> viewStudentsEnrolled(int courseID, int profID) {

		ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();
		ArrayList<Student> students = null;
		try {
			students = profDao.getStudentList(courseID, profID);
		} catch (CourseNotFoundException e) {
			System.out.println("\n\t" + e.getMessage());
			return null;
		} catch (CourseNotAssignedToProfessorException e) {
			System.out.println("\n\t" + e.getMessage());
			return null;
		}
		return students;

	}
}