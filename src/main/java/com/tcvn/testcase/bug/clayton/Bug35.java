package com.tcvn.testcase.bug.clayton;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;

public class Bug35 extends CoreTestTemplate {

	@Test(groups = "CTTestcase")
	public void test_Bug35() throws Exception {
		System.out.println("Start Bug35");
		//Changing time of tablet to 8:00 AM
		setTimeByCmd("20182706", "04", "10");

		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs(strSite, 1);

		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");

		selectRoutePage = new SelectRoutePageObject(driver);
		try {
			assertEquals(selectRoutePage.getTitle(), "SELECT ROUTE");
		} catch (Exception ex) {
			fail();
		}
	}
}
