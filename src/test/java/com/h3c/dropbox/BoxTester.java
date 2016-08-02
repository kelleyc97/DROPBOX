package com.h3c.dropbox;

import java.sql.SQLException;

public class BoxTester {

	public static void main(String[] args) throws SQLException {
		BoxController tester = new BoxController();
		// Wrong user name and/or password
//		tester.updateOrCreateBox("123", "123", "abcdef", "56789", 5, 30, "w", "this is a tester box");
		
		//Max storage and life time is too big 
//		System.out.print(tester.updateOrCreateBox("qbn6736", "c-m8q4", "testname3", "test3password", 100000, 30000, "r", 
//				"this is a tester box"));
//		
		// password not given, system self generate password
//	    tester.updateOrCreateBox("sfdc", "sfdc123", "12j334", null, 5, 30, "w", "this is a tester box");
		
		// Create a box with all the correct information 
		tester.updateOrCreateBox("admin", "userabc", "tester1", "testPass", 25, 8, "w", 
				null);
//		// Reactivate a pre-existing box 
//		System.out.println(tester.updateOrCreateBox("qu3049m", "cm-8342", "test", "test", 30, 10, "w", null));
	}

}
