package com.lti.application;

import java.util.ArrayList;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Student;
import com.lti.dao.ProfessorDaoImplementation;
import com.lti.exception.CourseNotAssignedToProfessorException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.service.ProfessorInterfaceOperation;
import com.lti.service.ProfessorService;

public class CRSProfessorMenu {
	public void show(int profID) {

		boolean quit = false;
		while (!quit) {
			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tProfessor menu\n");
			System.out.println("\t1) View enrolled students");
			System.out.println("\t2) Grade students");
			System.out.println("\t3) Exit");

			System.out.print("\n\tEnter option:\t");

			Scanner input = new Scanner(System.in);
			int option = input.nextInt();

			ProfessorInterfaceOperation service = new ProfessorService();
			ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();

			switch (option) {
			case 1: {
				ArrayList<Course> courseList = null;
				try {
					courseList = profDao.getCourseList(profID);
				} catch (ProfessorNotFoundException e) {
					System.out.println(e.getMessage());
					return;
				}

				System.out.println("\n\tEnrolled student list");

				for (Course course : courseList) {
					System.out.println("\n\tcourse: " + course.getCourseName());
					ArrayList<Student> students = null;
					students = service.viewStudentsEnrolled(course.getCourseID(), profID);

					for (Student student : students) {
						System.out.println("\t\tstudent name: " + student.getName());
					}
				}
			}
				break;

			case 2: {

				ArrayList<Course> courseList = null;
				System.out.println("\n\tCourse list");
				try {
					courseList = profDao.getCourseList(profID);
				} catch (ProfessorNotFoundException e) {
					System.out.println(e.getMessage());
				}
				for (Course course : courseList) {
					System.out.println("\tid: " + course.getCourseID() + ", course: " + course.getCourseName());
				}

				while (true) {
					System.out.print("\n\tEnter id of course to grade: ");
					int courseID = input.nextInt();

					ArrayList<Student> students = null;

					try {
						students = profDao.getStudentList(courseID, profID);
					} catch (CourseNotFoundException e) {
						System.out.println(e.getMessage());
						break;
					} catch (CourseNotAssignedToProfessorException e) {
						System.out.println(e.getMessage());
						break;
					}

					System.out.println("\n\tStudent list");

					for (Student student : students) {
						System.out.println("\tid: " + student.getId() + ", student name: " + student.getName());
					}

					while (true) {
						System.out.print("\tEnter id of student to grade: ");
						int studentID = input.nextInt();

						System.out.print("\tEnter grade (A,B,C,D,E,F): ");
						String grade = input.next();

						service.addGrade(studentID, courseID, grade);

						System.out.print("\tDo you want to grade another student? (Y/N): ");
						String option1 = input.next();

						if (option1.contains("N") || option1.contains("n")) {
							break;
						}
					}

					System.out.print("\tDo you want to grade another course? (Y/N): ");
					String option2 = input.next();

					if (option2.contains("N") || option2.contains("n")) {
						break;
					}
					break;
				}
			}

			case 3:
				quit = true;
				break;

			default:
				System.out.println("Enter a valid option!");
				break;
			}
		}
	}
}