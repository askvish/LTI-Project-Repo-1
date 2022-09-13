package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lti.bean.Login;
import com.lti.bean.Payment;
import com.lti.bean.Student;
import com.lti.constant.SQLConstant;
import com.lti.exception.StudentNotFoundException;
import com.lti.utils.DbUtils;

public class StudentDaoImplementation {

	Connection conn = null;

	/**
	 * 
	 * @param student
	 * @return
	 */
	public int addStudent(Student student) {

		int insertedID = 0;

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement(SQLConstant.CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, student.getName());
			stmt.setInt(2, 0);
			stmt.setInt(3, student.getAge());
			stmt.setString(4, student.getMobileNumber());
			stmt.setInt(5, student.getDepartmentID());
			stmt.setString(6, student.getAddress());
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
	 * @param studentID
	 * @throws StudentNotFoundException
	 */
	public void approveStudent(int studentID) throws StudentNotFoundException {

		PreparedStatement stmt = null;

		try {

			StudentDaoImplementation stuDao = new StudentDaoImplementation();
			ArrayList<Student> stu = stuDao.getStudentList();

			boolean studentIDFound = false;
			for (Student s : stu) {
				if (s.getId() == studentID) {
					studentIDFound = true;
					break;
				}
			}

			if (!studentIDFound) {
				throw new StudentNotFoundException("Student id doesn't exist");
			}

			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.APPROVE_STUDENT, studentID);
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

	/**
	 * 
	 * @param studentID
	 * @return
	 * @throws StudentNotFoundException
	 */
	public boolean isStudentApproved(int studentID) throws StudentNotFoundException {

		PreparedStatement stmt = null;

		boolean result = false;

		try {

			StudentDaoImplementation stuDao = new StudentDaoImplementation();
			ArrayList<Student> stu = stuDao.getStudentList();

			boolean studentIDFound = false;
			for (Student s : stu) {
				if (s.getId() == studentID) {
					studentIDFound = true;
					break;
				}
			}

			if (!studentIDFound) {
				throw new StudentNotFoundException("Student id doesn't exist");
			}

			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.STUDENT_APPROVAL_STATUS, studentID);
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				result = queryResult.getBoolean("isApproved");
			}

			stmt.close();

			return result;

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Student> getStudentList() {
		ArrayList<Student> students = new ArrayList<Student>();
		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.GET_STUDENT_LIST);
			ResultSet queryResult = stmt.executeQuery(SQLConstant.GET_STUDENT_LIST);

			while (queryResult.next()) {

				int id = queryResult.getInt("studentID");
				String name = queryResult.getString("studentName");
				boolean approved = queryResult.getBoolean("isApproved");
				int age = queryResult.getInt("age");
				String mobileNumber = queryResult.getString("mobileNumber");
				int deptID = queryResult.getInt("departmentID");
				String address = queryResult.getString("address");

				Student student = new Student();
				student.setId(id);
				student.setName(name);
				student.setApproved(approved);
				student.setAge(age);
				student.setMobileNumber(mobileNumber);
				student.setDepartmentID(deptID);
				student.setAddress(address);

				students.add(student);
			}

			stmt.close();

			return students;

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
	 * @param payment
	 */
	public void payFee(Payment payment) {

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.CREATE_PAYMENT);
			stmt.setString(1, payment.getPaymentMethod());
			stmt.setInt(2, payment.getStudentID());
			stmt.setFloat(3, payment.getTotalAmount());
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
