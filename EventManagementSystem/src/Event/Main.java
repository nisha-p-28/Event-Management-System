package Event;

import login.eventLogin;

import java.sql.SQLException;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		System.out.println("=========== Welcome to Elegant Events ===========");
		
		System.out.println("Login to Your Event Dashboard");
		
		eventLogin eventlogin = new eventLogin();
		
		eventlogin.doLogin();
		
	}

}
