package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lti.bean.Login;
import com.lti.constant.SQLConstant;
import com.lti.exception.InvalidUserException;
import com.lti.exception.UserAlreadyExistException;
import com.lti.utils.DbUtils;

public class UserDaoImplementation {

	Connection conn = null;

	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean isUsernameAlreadyTaken(String username) {
		ArrayList<Login> logins = this.getAllUserLoginDetails();

		boolean usernameAlreadyTaken = false;
		for (Login l : logins) {
			if (l.getUsername().equals(username)) {
				usernameAlreadyTaken = true;
				break;
			}
		}

		return usernameAlreadyTaken;
	}

	/**
	 * 
	 * @param login
	 * @throws UserAlreadyExistException
	 */
	public void createNewUser(Login login) throws UserAlreadyExistException {
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			if (this.isUsernameAlreadyTaken(login.getUsername())) {
				throw new UserAlreadyExistException("username already exists");
			}

			stmt = conn.prepareStatement(SQLConstant.CREATE_USER);
			stmt.setString(1, login.getUsername());
			stmt.setString(2, login.getPassword());

			int roleID = 0;

			String role = login.getRole();
			if (role.equals("student")) {
				roleID = 1;
			} else if (role.equals("professor")) {
				roleID = 2;
			} else if (role.equals("admin")) {
				roleID = 3;
			}

			stmt.setInt(3, roleID);
			stmt.setInt(4, login.getUserID());

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
	 * @param username
	 * @param password
	 * @param role
	 * @return
	 * @throws InvalidUserException
	 */
	public boolean validateUser(String username, String password, String role) throws InvalidUserException {
		PreparedStatement stmt = null;
		boolean result = false;

		int roleID = 0;

		if (role.equals("student")) {
			roleID = 1;
		} else if (role.equals("professor")) {
			roleID = 2;
		} else if (role.equals("admin")) {
			roleID = 3;
		}

		if (roleID == 0) {
			return false;
		}

		try {
			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.VALIDATE_USER, username, password, roleID);

			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			String name = null;
			while (queryResult.next()) {
				name = queryResult.getString("username");
			}

			stmt.close();

			if (name != null) {
				result = true;
			} else {
				throw new InvalidUserException("username or password invalid!");
			}

			return result;

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) {

			}
		}

		return false;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param role
	 * @return
	 * @throws InvalidUserException
	 */
	public int getUserID(String username, String password, String role) throws InvalidUserException {

		int roleID = 0;

		if (role.equals("student")) {
			roleID = 1;
		} else if (role.equals("professor")) {
			roleID = 2;
		} else if (role.equals("admin")) {
			roleID = 3;
		}

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.VALIDATE_USER, username, password, roleID);
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			int userID = 0;
			if (queryResult.next()) {
				userID = queryResult.getInt("userID");
			} else {
				throw new InvalidUserException("unable to getuser id");
			}

			stmt.close();

			return userID;

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {

			}
		}

		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Login> getAllUserLoginDetails() {
		ArrayList<Login> logins = new ArrayList<Login>();

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String sql = "select * from user";
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				String username = queryResult.getString("username");
				String password = queryResult.getString("password");
				Login login = new Login();
				login.setUsername(username);
				login.setPassword(password);
				logins.add(login);
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

		return logins;
	}

	public void updatePassword(String username, String newPassword) {

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.UPDATE_PASSWORD, newPassword, username);
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
}
