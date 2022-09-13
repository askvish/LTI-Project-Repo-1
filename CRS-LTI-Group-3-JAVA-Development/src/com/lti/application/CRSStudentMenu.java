package com.lti.application;

import java.util.ArrayList;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.RegistrationDaoImplementation;
import com.lti.dao.StudentDaoImplementation;
import com.lti.exception.CourseLimitExceedException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.service.StudentInterfaceOperation;
import com.lti.service.StudentService;

/**
 * @author 10710133
 *
 */

public class CRSStudentMenu {

	public void show(int studentID) {

		boolean quit = false;

		while (!quit) {
			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tStudent menu\n");
			System.out.println("\t1) Register for course");
			System.out.println("\t2) Add course");
			System.out.println("\t3) Drop course");
			System.out.println("\t4) View enrolled courses");
			System.out.println("\t5) Fee payment");
			System.out.println("\t6) View grade card");
			System.out.println("\t7) Exit");

			System.out.print("\n\tEnter option:\t");

			Scanner input = new Scanner(System.in);
			int option = input.nextInt();

			StudentInterfaceOperation service = new StudentService();

			switch (option) {
			case 1:
				service.registerCourses(studentID);
				break;

			case 2: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				
				RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
				ArrayList<Course> courseList = regDao.getCourseList();

				while (true) {
					
					System.out.print("\n\tCourse list:\t");
					for (Course course : courseList) {
						System.out.println("\tid: " + course.getCourseID() + "\t" + "course: " + course.getCourseName());
					}
					
					System.out.print("\n\tEnter course id to add:\t");
					int courseID = input.nextInt();

					System.out.print("\n\tConfirm course " + "Id: " + courseID + "(Y/N):\t");
					String opt = input.next();

					if (opt.contains("Y") || opt.contains("y")) {

						service.addCourse(studentID, courseID);

						System.out.print("\n\tAdd another course? (Y/N):\t");
						opt = input.next();
						if (opt.contains("N") || opt.contains("n")) {
							break;
						}
					}
				}
			}
				break;

			case 3: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
				ArrayList<Course> courseList = regDao.getCourseList();

				while (true) {
					
					System.out.print("\n\tCourse list:\t");
					for (Course course : courseList) {
						System.out.println("\tid: " + course.getCourseID() + "\t" + "course: " + course.getCourseName());
					}
					
					System.out.print("\tEnter course id to drop:\t");
					int courseID = input.nextInt();

					service.dropCourse(studentID, courseID);

					System.out.print("\n\tRemove another course? (Y/N):\t");
					String opt = input.next();

					if (opt.contains("N") || opt.contains("n")) {
						break;
					}
				}

			}
				break;

			case 4: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("\n\tEnrolled courses");

				ArrayList<Course> courseList = service.viewEnrolledCourses(studentID);

				for (Course course : courseList) {
					System.out.println("\tid: " + course.getCourseID() + "\t" + "course: " + course.getCourseName());
				}
			}
				break;

			case 5: {
				System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("\n\t\tFee Payment Portal");
				System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				ArrayList<Course> courseList = null;

				try {
					RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
					courseList = regDao.getStudentCourseList(studentID);
				} catch (StudentNotFoundException e) {
					System.out.println(e.getMessage());
					return;
				}

				int pricePerCourse = 10000;

				for (Course course : courseList) {
					System.out.println("\tid: " + course.getCourseID() + "\tcourse: " + course.getCourseName()
							+ "\tfee: " + pricePerCourse);
				}

				int fee = courseList.size() * pricePerCourse;
				System.out.println("\tTotal fee is: " + fee + " INR.");
				System.out.println("\tNote: Price for each course is " + pricePerCourse + " INR.");

				Payment payment = new Payment();
				payment.setStudentID(studentID);
				payment.setTotalAmount(fee);

				boolean paymentDone = false;
				while (!paymentDone) {

					System.out.print("\n\tDo you want to make payment (Y/N)?\t");
					String in = input.next();

					if (in.contains("N") || in.contains("n")) {
						break;
					}

					System.out.println("\n\tSelect Payment Method:\n");
					System.out.println("\t\t1. Credit Card");
					System.out.println("\t\t2. Debit Card");
					System.out.println("\t\t3. Net Banking");
					System.out.println("\t\t4. UPI");

					System.out.print("\n\t\tSelect Payment Method:\t");

					int opt = input.nextInt();

					switch (opt) {
					case 1:

						payment.setPaymentMethod("credit card");

						System.out.print("\t\tEnter credit card number:\t");
						Long cCardNumber = input.nextLong();
						System.out.print("\t\tEnter Expiry Month:\t");
						int cExpiryMonth = input.nextInt();
						System.out.print("\t\tEnter Expiry Year:\t");
						int cExpiryYear = input.nextInt();
						System.out.print("\t\tEnter CVV:\t");
						int cCVV = input.nextInt();

						// Details
						System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

						System.out.println("\n\t\tYour payment is processing.....");

						System.out.print("\n\t\tEnter the OTP:\t");
						int cOTP = input.nextInt();

						// Check for transaction successful
						System.out.println("\n\t\tValidating OTP.....");

						System.out.println("\t\tYour payment is successful.");
						paymentDone = true;
						break;

					case 2:

						payment.setPaymentMethod("debit card");

						System.out.print("\t\tEnter debit card number:\t");
						Long dCardNumber = input.nextLong();
						System.out.print("\t\tEnter Expiry Month:\t");
						int dExpiryMonth = input.nextInt();
						System.out.print("\t\tEnter Expiry Year:\t");
						int dExpiryYear = input.nextInt();
						System.out.print("\t\tEnter CVV:\t");
						int dCVV = input.nextInt();

						// Details
						System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

						System.out.println("\t\tYour payment is processing.....");

						System.out.print("\n\t\tEnter the OTP:\t");
						int dOTP = input.nextInt();

						// Check for transaction successful
						System.out.println("\n\t\tValidating OTP.....");

						System.out.println("\n\t\tYour payment is successful.");
						paymentDone = true;
						break;

					case 3:

						payment.setPaymentMethod("net card");

						System.out.println("\t\tSelect your Bank:");
						System.out.println("\t\tSBI\t\t\nPNB\t\t\nPAYTM\n");
						System.out.print("\n\t\tEnter Bank Name for net banking:\t");

						String bankName = input.next();
						System.out.println("Bank Name:" + bankName);

						// Check for transaction successful
						System.out.println("Your payment is successful");
						paymentDone = true;
						break;

					case 4:

						payment.setPaymentMethod("upi");

						System.out.print("Enter you UPI Id:\t");
						int upiId = input.nextInt();
						System.out.println("UPI ID:" + upiId);
						// Check for transaction successful

						System.out.println("Your payment is successful");
						paymentDone = true;
						break;
					default:
						System.out.println("Please enter a valid option!");
						break;
					}

					if (paymentDone) {
						service.payFee(payment);
					}
				}
			}
				break;

			case 6: {
				System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				System.out.println("\n\tGrade card");

				ArrayList<Grade> grades = service.viewGrades(studentID);

				for (Grade grade : grades) {
					System.out.println("\tcourse: " + grade.getCourseName() + ", grade: " + grade.getGrade());
				}

				if (grades.size() == 0) {
					System.out.println("\tYet to be graded");
				}
			}
				break;

			case 7:
				quit = true;
				break;

			default:
				System.out.println("\tEnter a valid option.");
				break;
			}
		}
	}
}
