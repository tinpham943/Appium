package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.DrivingPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class Bug_296 extends CoreTestTemplate {

	@BeforeClass
	public void before() {
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs("12345", "022900", "9999");
		//Setting time
		setTime(7, 45, "AM");
	}

	@Test(groups = "BugDatabase", priority = 10)
	public void testBug254() throws AWTException, InterruptedException {
		System.out.println("Start Bug296");
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		DrivingPageObject previewRoute = selectRoutePage.selectRouteByID("717");
		System.out.println("Show alert message: "+previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		previewRoute.clickStartRoute();
		System.out.println("Show alert message: "+previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		//Set file .gpx
		setFileMock("001.002-KV0123-Final", "1000");
		//Driving mode
		drivingPage = new DrivingPageObject(driver);
		stopPage = new StopPageObject(driver);
		//Set Timeout to 3000
		setNewTimeOut(3000);

		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F15378 L15378");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F60913 L60913");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F62871 L62871");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F69226 L69226");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F79106 L79106");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Running to stop 2
		assertTrue(drivingPage.isShowSelectStop());
		drivingPage.selectStop("Stop No.2");
		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F5079 L5079");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F6375 L6375");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F8733 L8733");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F25676 L25676");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F28292 L28292");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F29879 L29879");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F40340 L40340");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F48883 L48883");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F932 L932");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F1969 L1969");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F3476 L3476");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F5077 L5077");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F5778 L5778");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F6113 L6113");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F6343 L6343");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F8786 L8786");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();
		setNewTimeOut(5000);
		System.out.println("Waiting to stop 5");

		assertTrue(stopPage.isOpenStopView());
		System.out.println("Show alert message: "+previewRoute.getAlertMsg());
		previewRoute.clickAlertCancel();
	}
}
