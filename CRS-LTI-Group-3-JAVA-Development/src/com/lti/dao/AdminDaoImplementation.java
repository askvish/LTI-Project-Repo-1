/**
 * 
 */
package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.constant.SQLConstant;
import com.lti.exception.CourseFoundException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.utils.DbUtils;

/**
 * @author 10710133
 *
 */

public class AdminDaoImplementation {

	Connection conn = null;
	
	/**
	 * 
	 */
	public void addAdmin() {
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.CREATE_ADMIN);
			stmt.setInt(1, 1);
			stmt.setString(2, "root");
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {

			}
		}
	}

	/**
	 * 
	 * @param course
	 * @throws CourseFoundException
	 * @throws ProfessorNotFoundException
	 */
	public void addCourse(Course course) throws CourseFoundException, ProfessorNotFoundException {

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String query = String.format(SQLConstant.CHECK_FOR_COURSENAME, course.getCourseName());
			stmt = conn.prepareStatement(query);
			ResultSet queryResult = stmt.executeQuery(query);

			queryResult.next();
			int courseFound = queryResult.getInt(1);

			if (courseFound == 1) {
				throw new CourseFoundException("course already added. cannot add same course");
			}

			stmt.close();

			query = String.format(SQLConstant.CHECK_FOR_PROFESSOR, course.getProfID());
			stmt = conn.prepareStatement(query);
			queryResult = stmt.executeQuery(query);

			queryResult.next();
			int profFound = queryResult.getInt(1);

			if (profFound != 1) {
				throw new ProfessorNotFoundException("professor not found");
			}

			stmt.close();

			stmt = conn.prepareStatement(SQLConstant.ADD_COURSE);
			stmt.setString(1, course.getCourseName());
			stmt.setString(2, course.getCourseDetails());
			stmt.setInt(3, course.getProfID());
			stmt.setInt(4, course.getEnrolledStudentCount());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {

			}
		}
	}

	public void removeCourse(int courseID) {

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.REMOVE_COURSE, courseID);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {

			}
		}
	}

	public ArrayList<Grade> getGrades(int studentID) {

		ArrayList<Grade> grades = new ArrayList<Grade>();

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.GET_STUDENT_GRADE_LIST, studentID);
			stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				int gradeID = result.getInt("gradeID");
				String grade = result.getString("grade");
				int courseID = result.getInt("courseID");

				sql = String.format(SQLConstant.GET_COURSENAME, courseID);
				PreparedStatement s = conn.prepareStatement(sql);
				ResultSet r = s.executeQuery(sql);
				r.next();
				String courseName = r.getString(1);
				s.close();

				Grade g = new Grade();
				g.setGradeID(gradeID);
				g.setGrade(grade);
				g.setCourseID(courseID);
				g.setStudentID(studentID);
				g.setCourseName(courseName);

				grades.add(g);
			}

			stmt.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {

			}
		}

		return grades;
	}
}
