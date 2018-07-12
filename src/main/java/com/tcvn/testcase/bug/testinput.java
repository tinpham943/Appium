package com.tcvn.testcase.bug;

import java.awt.AWTException;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class testinput extends CoreTestTemplate {

	@Test(groups = "KVTestcase")
	public void testBug254() throws AWTException, InterruptedException {
		System.out.println("Start DP08");
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs("12345", "022900", "9999");

		//Select 717, Run To Forest high School
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		drivingPage = selectRoutePage.selectRouteByID("717");
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();
		//Start Route
		drivingPage.clickStartRoute();
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		drivingPage.clickAddStop();
		//Driving mode
		stopPage = new StopPageObject(driver);
		stopPage.inputStudent("us1");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
