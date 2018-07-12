package com.tcvn.testcase.bug;

import java.awt.AWTException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;

/**
 * @author datle
 */
public class DP03 extends CoreTestTemplate {

	@BeforeClass
	public void setUp() throws Exception {
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs(strSite, 1);
		setAirPlane("ON");
	}

	@AfterClass
	public void endTest() {
		setAirPlane("OFF");
	}

	@Test
	public void testBuildRoute() throws InterruptedException, AWTException {
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		selectRoutePage.buildNewRoute();
		// WAITING FOR THE FEATURE IS ADDED TO CHECK.
	}
}