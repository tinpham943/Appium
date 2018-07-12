package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class DP06 extends CoreTestTemplate {

	@Test(groups = "KVTestcase")
	public void test_DP06() throws Exception {
		System.out.println("Start DP06");
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs(strSite, 1);

		//Select 717, Run To Forest high School
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		drivingPage = selectRoutePage.selectRouteByID("717");
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Changing time of tablet to 8:00 AM
		setTime(8, 0, "AM");

		//Start Route
		drivingPage.clickStartRoute();
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Start GPX file
		setFileMock("717-001.002-KV0123-Q.gpx", "1000");

		//Driving mode
		stopPage = new StopPageObject(driver);
		//Set Timeout to 3000
		setNewTimeOut(3000);

		//Let the bus to visit Stop1: swiping card for all students
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

		//Let the bus to visit unplanned Stop: swiping some unknown students
		drivingPage.clickAddStop();
		stopPage.inputStudent("us1");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("us2");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Let the bus to visit Stop2: swiping card for 1 student
		assertTrue(drivingPage.isShowSelectStop());
		drivingPage.selectStop("Stop No.2");
		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F5079 L5079");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Let the bus to visit Stop3: swiping card for 2 students
		assertTrue(stopPage.isOpenStopView());
		stopPage.selectStudent("F10068 L10068");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("F13962 L13962");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		//Swiping card for 2 students disembark
		stopPage.inputStudent("us1");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("us2");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Let the bus to visit Stop4: input unknown students
		assertTrue(stopPage.isOpenStopView());
		stopPage.inputStudent("us3");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Let the bus comes to school
		assertTrue(stopPage.isOpenStopView());
	}
}