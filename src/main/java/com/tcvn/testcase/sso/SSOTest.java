package com.tcvn.testcase.sso;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.PunchesPageObject;
import com.tcvn.pageobjects.main.SwitchSessionPageObject;

/**
 * It would better to run all the test cases by order: Test1, Test2, ...
 * and they are should be run when no data shown on punching grid. 
 * @author datle
 */
public class SSOTest extends CoreTestTemplate {

	@Test
	public void Test4_SSO_DiffUserRelogin_LoadLastSession() throws Exception {
		boolean check = false;

		check = switchSessionPage.SSO_DiffUserRelogin_LoadLastSession();
		assertTrue(check);
	}

	@Test
	public void Test3_SSO_DiffUserRelogin_NotLoadLastSession() throws Exception {
		boolean check = false;

		menuPage.clickMenuOpt("Time & Attendance");
		punchesPage = new PunchesPageObject(driver);
		punchesPage.clickPunchIn();
		menuPage.clickMenuOpt("Switch Session Owner");
		switchSessionPage = new SwitchSessionPageObject(driver);
		switchSessionPage.clickAlertOK();
		punchesPage = loginPage.loginAs("Saigon", 2);

		check = switchSessionPage.SSO_DiffUserRelogin_NotLoadLastSession();
		assertTrue(check);
	}

	@Test
	public void Test2_SSO_SameUserRelogin_LoadLastSession() throws Exception {
		boolean check = false;

		// Switch to Select Route menu to check if 
		// the same user re-login + load last session test case redirects the user to S.Route menu  
		menuPage.clickMenuOpt("Select Route");
		menuPage.clickMenuOpt("Switch Session Owner");
		switchSessionPage = new SwitchSessionPageObject(driver);

		check = switchSessionPage.SSO_SameUserRelogin_LoadLastSession();
		assertTrue(check);
	}

	@Test
	public void Test1_SSO_SameUserRelogin_NotLoadLastSession() throws Exception {
		boolean check = false;

		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs("Saigon", 1);
		menuPage.clickMenuOpt("Switch Session Owner");
		switchSessionPage = new SwitchSessionPageObject(driver);

		check = switchSessionPage.SSO_SameUserRelogin_NotLoadLastSession();
		assertTrue(check);
	}
}