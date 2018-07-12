package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class DP_Hanh extends CoreTestTemplate {
	
	/** Test case for Stop #1
	 * @author datle
	 * @throws Exception 
	 * @function compareStudentBoarded
	 */
	@Test
	public void Test1_Stop1() throws Exception {
		System.out.println("== START TEST CASE FOR STOP #1 ==");
		boolean check = false;

		/** Login to DP
		 * Updated: reduce waiting time to 3 sec for Retry btn, then reset waiting time.
		 * @author quingo
		 * @updater datle
		 */
		loginPage = new LoginPageObject(driver);
		setNewTimeOut(10);
		punchesPage = loginPage.loginAs(strSite, 1);
		resetTimeOut();
		
		//Changing time of tablet to 9:45 AM
		setTime(9, 45, "AM");

		//Select 03, Run To Novaland
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		drivingPage = selectRoutePage.selectRouteByID("03");
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Start Route
		drivingPage.clickStartRoute();
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();
		
		//Start GPX file
		setFileMock("Novaland_case1", "1000");
		
		//Driving mode
		stopPage = new StopPageObject(driver);
		//Set Timeout to 3000
		setNewTimeOut(3000);
				
		assertTrue(stopPage.isOpenStopView());
		stopPage.inputStudent("1");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.selectStudent("Hoc Sinh Hai");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		
		check = stopPage.compareStudentBoarded();
		assertTrue(check);
	}
	
	@Test
	public void Test3_UnplannedStop() {
		System.out.println("== START TEST CASE FOR UNPLANNED STOP & ADDING UNKNOWN STUDENTS ==");
		boolean check = false;
		
		drivingPage.clickAddStop();
		stopPage.inputStudent("a");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("6");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		
		assertTrue(check);
	}

	@Test(groups = "HNTestcase")
	public void test_DP_Hanh() throws AWTException, InterruptedException {
		System.out.println("Start DP_Hanh");

		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs("62auto1", "037506", "9999");

		//Changing time of tablet to 9:45 AM
		setTime(9, 45, "AM");

		//Select 717, Run To Forest high School
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		drivingPage = selectRoutePage.selectRouteByID("03");
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Start Route
		drivingPage.clickStartRoute();
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Start GPX file
		setFileMock("Novaland_case1", "1000");

		//Driving mode
		stopPage = new StopPageObject(driver);
		//Set Timeout to 3000
		setNewTimeOut(3000);

		//STOP 1
		assertTrue(stopPage.isOpenStopView());
		stopPage.inputStudent("1");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("2");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("3");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//STOP 2
		assertTrue(stopPage.isOpenStopView());
		stopPage.inputStudent("4");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("5");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Let the bus to visit unplanned Stop: swiping some unknown students
		drivingPage.clickAddStop();
		stopPage.inputStudent("a");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("6");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//STOP 3
		assertTrue(stopPage.isOpenStopView());
		stopPage.inputStudent("7");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("8");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("9");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("10");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		//Running to stop 4
		assertTrue(stopPage.isOpenStopView());
		stopPage.inputStudent("1");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("2");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("3");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("4");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("5");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("a");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("6");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("7");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("8");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("9");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.inputStudent("10");
		stopPage.getStudentInfo();
		stopPage.boardStudent();
		stopPage.closeStopView();

		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertCancel();
	}
}