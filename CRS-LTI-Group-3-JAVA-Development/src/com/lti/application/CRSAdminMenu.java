package com.lti.application;

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
import com.lti.exception.StudentNotFoundException;
import com.lti.exception.UserAlreadyExistException;
import com.lti.service.AdminInterfaceOperation;
import com.lti.service.AdminService;

public class CRSAdminMenu {
	public void show() {

		boolean quit = false;

		while (!quit) {
			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tAdmin menu\n");
			System.out.println("\t1) Add professor");
			System.out.println("\t2) Approve students registration");
			System.out.println("\t3) Add course");
			System.out.println("\t4) Remove course");
			System.out.println("\t5) Generate grade card");
			System.out.println("\t6) Exit");

			System.out.print("\n\tEnter option:\t");

			Scanner input = new Scanner(System.in);
			int option = input.nextInt();

			AdminInterfaceOperation service = new AdminService();

			switch (option) {
			case 1: {

				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				while (true) {

					UserDaoImplementation userDao = new UserDaoImplementation();

					System.out.print("\n\tEnter username: ");
					String username = input.next();

					if (userDao.isUsernameAlreadyTaken(username)) {
						System.out.println("\n\tusername not available. try again!");
						break;
					}

					System.out.print("\n\tCreate password: ");
					String password = input.next();

					Login login = new Login();
					login.setUsername(username);
					login.setPassword(password);
					login.setRole("professor");

					System.out.print("\n\tEnter professor name: ");
					String name = input.next();

					System.out.print("\n\tmobile number: ");
					String mobileNumber = input.next();

					System.out.print("\n\taddress: ");
					String address = input.next();

					System.out.print("\n\tage: ");
					int age = input.nextInt();

					System.out.print("\n\tdepartment id: ");
					int deptID = input.nextInt();

					Professor professor = new Professor();
					professor.setName(name);
					professor.setMobileNumber(mobileNumber);
					professor.setAddress(address);
					professor.setAge(age);
					professor.setDepartmentID(deptID);

					service.addProfessor(professor, login);

					System.out.print("\n\tProfessor added successfully");

					System.out.print("\n\tAdd another professor? (Y/N): ");
					String opt = input.next();

					if (opt.contains("N") || opt.contains("n")) {
						break;
					}
				}
			}

				break;

			case 2: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("\n\tApprove students:");

				StudentDaoImplementation stuDao = new StudentDaoImplementation();
				ArrayList<Student> students = stuDao.getStudentList();

				for (Student s : students) {
					System.out.println(
							"\n\tStudent name: " + s.getName() + ", Approved: " + (s.isApproved() ? "Y" : "N"));
				}

				for (Student s : students) {

					if (!s.isApproved()) {
						System.out.print("\n\tApprove student: " + s.getName() + " (Y/N) ?");
						String opt = input.next();

						if (opt.equals("Y") || opt.equals("y")) {
							service.approveStudentRegistration(s.getId());
						}

						System.out.print("\n\tContinue with approval? (Y/N):\t");
						opt = input.next();

						if (opt.equals("N") || opt.equals("n")) {
							break;
						}
					}
				}
			}
				break;

			case 3: {

				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();

				while (true) {

					System.out.print("\n\tEnter course name:\t");
					String name = input.next();

					System.out.print("\n\tEnter course details:\t");
					String details = input.next();

					ArrayList<Professor> professorList = profDao.getProfessorList();

					System.out.println("\n\tProfessor list\t");

					for (Professor p : professorList) {
						System.out.println("\n\tid: " + p.getId() + " , name: " + p.getName());
					}

					System.out.print("\n\tEnter teaching professor id:\t");
					int profID = input.nextInt();

					Course course = new Course();
					course.setCourseName(name);
					course.setCourseDetails(details);
					course.setEnrolledStudentCount(0);
					course.setProfID(profID);

					service.addCourse(course);

					System.out.print("\n\tAdd another course? (Y/N):\t");
					String opt = input.next();

					if (opt.contains("N") || opt.contains("n")) {
						break;
					}
				}

			}
				break;

			case 4: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();

				ArrayList<Course> courseList = regDao.getCourseList();

				System.out.println("\n\tCourse list");

				for (Course course : courseList) {
					System.out.println("\tId: " + course.getCourseID() + "\t" + "Name: " + course.getCourseName());
				}

				while (true) {
					System.out.print("\n\tEnter course id:\t");
					int id = input.nextInt();

					service.removeCourse(id);

					System.out.print("\n\tRemove another course? (Y/N):\t");
					String opt = input.next();

					if (opt.contains("N") || opt.contains("n")) {
						break;
					}
				}
			}
				break;

			case 5: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				StudentDaoImplementation stuDao = new StudentDaoImplementation();
				ArrayList<Student> students = stuDao.getStudentList();

				System.out.println("\n\tStudent list");

				for (Student s : students) {
					System.out.println("\tid: " + s.getId() + ", name: " + s.getName());
				}

				while (true) {
					System.out.print("\tEnter student id to generate grade card: ");
					int studentID = input.nextInt();

					ArrayList<Grade> grades = service.generateReportCard(studentID);

					System.out.println("\n\tGrade card");

					for (Grade grade : grades) {
						System.out.println("\tcourse: " + grade.getCourseName() + ", grade: " + grade.getGrade());
					}

					if (grades.size() == 0) {
						System.out.println("\n\tgrades yet to be added!");
					}

					service.generateReportCard(studentID);

					System.out.print("\n\tGenerate grade card for another student? (Y/N): ");
					String opt = input.next();

					if (opt.contains("N") || opt.contains("n")) {
						break;
					}
				}
			}
				break;

			case 6:
				quit = true;
				break;

			default:
				System.out.println("Enter a valid option!");
				break;
			}
		}
	}
}
