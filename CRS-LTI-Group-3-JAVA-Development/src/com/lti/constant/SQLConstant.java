package com.lti.constant;

public class SQLConstant {
	public final static String VALIDATE_USER = "select * from user where username = '%s' AND password = '%s' AND roleID = '%d'";
	public final static String CREATE_USER = "insert into user(username, password, roleID, userID) values (?,?,?,?)";
	public final static String CREATE_STUDENT = "insert into student(studentName, isApproved, age, mobileNumber, departmentID, address) values(?,?,?,?,?,?)";
	public final static String APPROVE_STUDENT = "update student set isApproved=1 where studentID = '%d'";
	public final static String STUDENT_APPROVAL_STATUS = "select * from student where studentID = '%d'";

	public final static String GET_STUDENT_LIST = "select * from student";
	public final static String CREATE_PAYMENT = "insert into payment(paymentMethod, studentID, totalAmount) values(?,?,?)";
	public final static String GET_COURSE_LIST = "select * from course";

	public final static String REGISTER_COURSE = "insert into registration(studentID, courseID) values(?,?)";
	public final static String DEREGISTER_COURSE = "delete from registration where courseID = %d AND studentID = %d";
	public final static String GET_STUDENT_COURSE_LIST = "select * from registration where studentID = %d";
	public final static String GET_COURSE_DETAILS = "select * from course where courseID = %d";

	public final static String CREATE_PROFESSOR = "insert into professor(professorName, mobileNumber, address, departmentID, age) values(?,?,?,?,?)";
	public final static String GET_PROFESSOR_COURSE_LIST = "select * from course where professorID = %d";
	public final static String GET_COURSE_STUDENT_LIST = "select * from registration where courseID = %d";
	public final static String GET_STUDENT_DETAILS = "select * from student where studentID = %d";
	public final static String ADD_GRADE = "insert into grade(studentID, courseID, grade) values(?,?,?)";

	public final static String CREATE_ADMIN = "insert into admin values(?,?)";
	public final static String ADD_COURSE = "insert course(courseName, courseDetails, professorID, enrolledStudentCount) values(?, ?, ?, ?)";
	public final static String REMOVE_COURSE = "delete from course where courseID = %d";
	public final static String GET_STUDENT_GRADE_LIST = "select * from grade where studentID = %d";
	
	public final static String UPDATE_PASSWORD = "update user set password = '%s' where username = '%s'";
	public final static String GET_COURSE_STUDNET_COUNT = "select enrolledStudentCount from course where courseID = %d";
	public final static String CHECK_FOR_PROFESSOR = "SELECT EXISTS(SELECT * FROM professor WHERE professorID = %d)";
	public final static String GET_PROFESSOR_FOR_COURSE = "SELECT professorID FROM course WHERE courseID = %d";
	public final static String CHECK_FOR_COURSE = "SELECT EXISTS(SELECT * FROM course WHERE courseID = %d)";
	public final static String CHECK_FOR_STUDENT = "SELECT EXISTS(SELECT * FROM student WHERE studentID = %d)";
	public final static String CHECK_FOR_COURSENAME = "SELECT EXISTS(SELECT * FROM course WHERE courseName = '%s')";
	public final static String GET_COURSENAME = "select courseName from course where courseID = %d";
}