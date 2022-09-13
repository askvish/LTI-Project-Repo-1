package com.lti.application;

import java.util.Scanner;

import com.lti.service.UserService;
import com.lti.bean.Login;
import com.lti.bean.Student;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.StudentDaoImplementation;
import com.lti.dao.UserDaoImplementation;
import com.lti.exception.InvalidUserException;
import com.lti.exception.StudentNotFoundException;

/**
 * @author 10710133
 *
 */

public class CRSApplication {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		UserService userService = new UserService();

		boolean quit = false;
		while (!quit) {
			System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tWelcome to CRS Application !\n");
			System.out.println("\t1) Login");
			System.out.println("\t2) Registration");
			System.out.println("\t3) Update password");
			System.out.println("\t4) Exit");

			System.out.print("\n\tEnter option:\t");

			int option = input.nextInt();

			switch (option) {
			case 1: {
				boolean quitLoginMenu = false;
				while (!quitLoginMenu) {

					System.out
							.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					System.out.println("\n\tLogin screen");

					System.out.print("\n\tEnter username:\t");
					String username = input.next();

					System.out.print("\tEnter password:\t");
					String password = input.next();

					System.out.print("\tEnter role:\t");
					String role = input.next();
					
					Login login = new Login();
					login.setUsername(username);
					login.setPassword(password);
					login.setRole(role);
					
					if(!userService.verifyCredential(login)) {
						System.out.println("\n\tusername or password invalid");
						break;
					}
					
					int userID = 0;
					try {
						UserDaoImplementation userDao = new UserDaoImplementation();
						userID = userDao.getUserID(username, password, role);
					} catch (InvalidUserException e) {
						System.out.println("\n\t" + e.getMessage());
						break;
					}

					if (role.equals("student")) {

						StudentDaoImplementation stuDao = new StudentDaoImplementation();

						try {
							if (!stuDao.isStudentApproved(userID)) {
								System.out.println("\n\tWaiting for approval by admin");
								quit = true;
								continue;
							}
						} catch (StudentNotFoundException e) {
							System.out.println(e.getMessage());
						}

						CRSStudentMenu menu = new CRSStudentMenu();
						menu.show(userID);

					} else if (role.equals("professor")) {
						CRSProfessorMenu menu = new CRSProfessorMenu();
						menu.show(userID);
					} else if (role.equals("admin")) {
						CRSAdminMenu menu = new CRSAdminMenu();
						menu.show();
					} else {
						System.out.println("\tEnter a valid role.");
					}
					quitLoginMenu = true;
				}
			}
				break;

			case 2: {

				System.out.println("\n\tRegister student");

				UserDaoImplementation userDao = new UserDaoImplementation();

				System.out.print("\n\tEnter username: ");
				String username = input.next();

				if (userDao.isUsernameAlreadyTaken(username)) {
					System.out.println("\n\tusername not available. try again!");
					return;
				}

				System.out.print("\tCreate password:");
				String password = input.next();

				System.out.print("\tEnter name: ");
				String name = input.next();

				System.out.print("\tEnter age: ");
				int age = input.nextInt();

				System.out.print("\tEnter mobile number: ");
				String mobileNumber = input.next();

				System.out.print("\tEnter address: ");
				String address = input.next();

				Student student = new Student();
				student.setName(name);
				student.setAge(age);
				student.setMobileNumber(mobileNumber);
				student.setAddress(address);

				Login login = new Login();
				login.setUsername(username);
				login.setPassword(password);
				login.setRole("student");

				userService.registerStudent(student, login);
			}
				break;

			case 3: {
				System.out.println("\nUpdate password");

				System.out.print("\tEnter username: ");
				String username = input.next();

				System.out.print("\tEnter old password: ");
				String oldPassword = input.next();
				
				Login login = new Login();
				login.setUsername(username);
				login.setPassword(oldPassword);
				login.setRole("student");
				
				if(userService.verifyCredential(login)) {
					System.out.print("\tEnter new password: ");
					String newPassword = input.next();
					userService.resetPassword(username, newPassword);					
				} else {
					System.out.println("\n\tusername or password invalid");
				}
				
			}
				break;

			case 4:
				System.out.println("\tExit successful!");
				quit = true;
				break;

			default:
				System.out.println("\tInvalid option!");
				break;
			}
		}
	}
}