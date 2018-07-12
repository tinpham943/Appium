package com.tcvn.testcase.login;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;

public class RememberOptTest extends CoreTestTemplate {

	boolean check = false;

	@Test
	public void Test_RememberOpt() {
		try {
			loginPage = new LoginPageObject(driver);

			// Uncheck "Remember My Driver ID" check box
			loginPage.setRemember("false");

			punchesPage = loginPage.loginAs("Saigon", 1);
			// Log out then check if driver ID is cleared
			menuPage.clickMenuOpt("Log Out");

			check = loginPage.checkDriverID();
			assertTrue(check);

		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(check);
		}
	}
}
