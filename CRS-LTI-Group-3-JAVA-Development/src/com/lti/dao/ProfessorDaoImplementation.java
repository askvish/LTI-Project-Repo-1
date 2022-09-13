package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lti.bean.Login;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.constant.SQLConstant;
import com.lti.exception.CourseNotAssignedToProfessorException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.utils.DbUtils;
import com.lti.bean.Course;

public class ProfessorDaoImplementation {

	Connection conn = null;

	/**
	 * 
	 * @param professor
	 * @return id in professsor table
	 */
	public int addProfessor(Professor professor) {
		PreparedStatement stmt = null;

		int insertedID = 0;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.CREATE_PROFESSOR, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, professor.getName());
			stmt.setString(2, professor.getMobileNumber());
			stmt.setString(3, professor.getAddress());
			stmt.setInt(4, professor.getDepartmentID());
			stmt.setInt(5, professor.getAge());

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				insertedID = rs.getInt(1);
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

		return insertedID;
	}

	/**
	 * 
	 * @return list of professors
	 */
	public ArrayList<Professor> getProfessorList() {

		ArrayList<Professor> professsors = new ArrayList<Professor>();

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();

			String query = "select * from professor";
			stmt = conn.prepareStatement(query);
			ResultSet queryResult = stmt.executeQuery(query);

			while (queryResult.next()) {

				int id = queryResult.getInt("professorID");
				String name = queryResult.getString("professorName");
				String mobileNumber = queryResult.getString("mobileNumber");
				String address = queryResult.getString("address");
				int deptID = queryResult.getInt("departmentID");
				int age = queryResult.getInt("age");

				Professor p = new Professor();
				p.setId(id);
				p.setName(name);
				p.setMobileNumber(mobileNumber);
				p.setAddress(address);
				p.setDepartmentID(deptID);
				p.setAge(age);

				professsors.add(p);
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

		return professsors;
	}

	/**
	 * 
	 * @param profID
	 * @return list of course
	 * @throws ProfessorNotFoundException
	 */
	public ArrayList<Course> getCourseList(int profID) throws ProfessorNotFoundException {

		ArrayList<Course> courses = new ArrayList<Course>();

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();

			String query = String.format(SQLConstant.CHECK_FOR_PROFESSOR, profID);
			stmt = conn.prepareStatement(query);
			ResultSet queryResult = stmt.executeQuery(query);

			queryResult.next();
			int profFound = queryResult.getInt(1);

			if (profFound != 1) {
				throw new ProfessorNotFoundException("professor not found");
			}

			stmt.close();

			String sql = String.format(SQLConstant.GET_PROFESSOR_COURSE_LIST, profID);
			stmt = conn.prepareStatement(sql);
			queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				int id = queryResult.getInt("courseID");
				String name = queryResult.getString("courseName");
				Course course = new Course();
				course.setCourseName(name);
				course.setCourseID(id);
				courses.add(course);
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

		return courses;
	}

	/**
	 * 
	 * @param courseID course id
	 * @param profID   professor id
	 * @return list of students
	 * @throws CourseNotFoundException
	 * @throws CourseNotAssignedToProfessorException
	 */
	public ArrayList<Student> getStudentList(int courseID, int profID)
			throws CourseNotFoundException, CourseNotAssignedToProfessorException {

		ArrayList<Integer> studentIDs = new ArrayList<Integer>();
		ArrayList<Student> students = new ArrayList<Student>();

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();

			String query = String.format(SQLConstant.GET_PROFESSOR_FOR_COURSE, courseID);
			stmt = conn.prepareStatement(query);
			ResultSet queryResult = stmt.executeQuery(query);

			queryResult.next();
			int profIDofCourse = queryResult.getInt(1);

			if (profIDofCourse != profID) {
				throw new CourseNotAssignedToProfessorException("course not assigned to professor");
			}

			stmt.close();

			query = String.format(SQLConstant.CHECK_FOR_COURSE, courseID);
			stmt = conn.prepareStatement(query);
			queryResult = stmt.executeQuery(query);

			queryResult.next();
			int courseFound = queryResult.getInt(1);

			if (courseFound != 1) {
				throw new CourseNotFoundException("course not found");
			}

			stmt.close();

			String sql = String.format(SQLConstant.GET_COURSE_STUDENT_LIST, courseID);
			stmt = conn.prepareStatement(sql);
			queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				int id = queryResult.getInt("studentID");
				studentIDs.add(id);
			}

			for (int n : studentIDs) {
				String sql1 = String.format(SQLConstant.GET_STUDENT_DETAILS, n);
				stmt = conn.prepareStatement(sql1);
				ResultSet queryResult1 = stmt.executeQuery(sql1);
				queryResult1.next();

				int id = queryResult1.getInt("studentID");
				String name = queryResult1.getString("studentName");

				Student student = new Student();
				student.setId(id);
				student.setName(name);
				students.add(student);
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

		return students;
	}

	/**
	 * 
	 * @param studentID
	 * @param courseID
	 * @param grade
	 * @throws StudentNotFoundException
	 * @throws CourseNotFoundException
	 */
	public void addGrade(int studentID, int courseID, String grade)
			throws StudentNotFoundException, CourseNotFoundException {
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String query = String.format(SQLConstant.CHECK_FOR_COURSE, courseID);
			stmt = conn.prepareStatement(query);
			ResultSet queryResult = stmt.executeQuery(query);

			queryResult.next();
			int courseFound = queryResult.getInt(1);

			if (courseFound != 1) {
				throw new CourseNotFoundException("course not found");
			}

			stmt.close();

			query = String.format(SQLConstant.CHECK_FOR_STUDENT, studentID);
			stmt = conn.prepareStatement(query);
			queryResult = stmt.executeQuery(query);

			queryResult.next();
			int studentFound = queryResult.getInt(1);

			if (studentFound != 1) {
				throw new StudentNotFoundException("student not found");
			}

			stmt.close();

			stmt = conn.prepareStatement(SQLConstant.ADD_GRADE);
			stmt.setInt(1, studentID);
			stmt.setInt(2, courseID);
			stmt.setString(3, grade);
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
}
