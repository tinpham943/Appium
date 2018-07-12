package com.tcvn.testcase.bug;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.DrivingPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class Bug_254 extends CoreTestTemplate {

	@BeforeClass
	public void before() {
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs("12345", "022900", "9999");
		//Setting time
		setTime(8, 10, "AM");
	}

	@Test(groups = "BugDatabase", priority = 10)
	public void testBug254() throws InterruptedException, AWTException {
		System.out.println("Start Bug254");
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		DrivingPageObject previewRoute = selectRoutePage.selectRouteByID("717");
		System.out.println("Show alert message: "+previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		previewRoute.clickStartRoute();
		System.out.println("Show alert message: "+previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		//Driving mode
		stopPage = new StopPageObject(driver);
		//Set file .gpx
		setFileMock("001.002-KV0123-Final", "1000");
		//Set Timeout to 3000
		setNewTimeOut(3000);

		//Running to stop 1
		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F15378 L15378");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("us1");
		stopPage.boardStudent();
		stopPage.inputStudent("us2");
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Running to stop 2
		drivingPage = new DrivingPageObject(driver);
		assertTrue(drivingPage.isShowSelectStop());
		drivingPage.selectStop("Stop No.2");
		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F5079 L5079");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("us1");
		stopPage.closeStudentDetail();
		stopPage.inputStudent("F15378 L15378");
		stopPage.getStudentInfo();
		assertEquals(stopPage.getButtonName(), "DISEMBARK");
	}
}
