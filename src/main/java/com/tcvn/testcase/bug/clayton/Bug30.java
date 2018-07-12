package com.tcvn.testcase.bug.clayton;

import static org.testng.Assert.fail;

import java.util.Calendar;

import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class Bug30 extends CoreTestTemplate {

	@Test(groups = "CTTestcase")
	public void test_Bug30() throws Exception {
		System.out.println("Start Bug30");
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs(strSite, 1);

		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);

		//Select 717, Run To Forest high School
		drivingPage = selectRoutePage.selectRouteByID("717");
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Changing time of tablet to 8:00 AM
		setTimeByCmd("20182706", "08", "00");

		//Start Route
		drivingPage.clickStartRoute();
		System.out.println("Show alert message: "+drivingPage.getAlertMsg());
		drivingPage.clickAlertOK();

		//Start GPX file
		setFileMock("717-001.002-KV0308SAE.gpx", "1000");

		//Driving mode
		stopPage = new StopPageObject(driver);

		//Get current time and set end time
		int GPXnote = 989;
		Calendar currentTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		endTime.add(Calendar.SECOND, GPXnote);
		System.out.println("Current Time :" + currentTime.getTime());
		System.out.println("End Time :" + endTime.getTime());

		//Set Timeout to 3000
		setNewTimeOut(3000);

		int i = 0 ;
		setNewTimeOut(100);
		boolean openStopSchool = false;
		do {
			//Get current time
			currentTime = Calendar.getInstance();
			//Check Driving mode every 5 minutes
			if (currentTime.get(Calendar.MINUTE)%5 == 0
					&& currentTime.get(Calendar.SECOND) == 00
					&& i == 0) {
				String currentAct = driver.currentActivity();
				System.out.println("Show time :"+currentTime.getTime());
				i = 1;
				//Check crash app
				if (currentAct == null) {
					System.out.println("Crash app");
					break;
				}
			}
			if (currentTime.get(Calendar.SECOND) != 00) {
				i = 0;
			}
			try {
				if (stopPage.isOpenStopView() && stopPage.getStopnumber().equals("5")) {
					openStopSchool = true;
					break;
				}
				drivingPage.clickAlertCancel();
			} catch (Exception ex) {}
		} while (currentTime.before(endTime));

		resetTimeOut();
		swapActivities("AndroidMockGpx");
		stopMock();

		if (openStopSchool == false) {
			driver.launchApp();
			fail("Not open stop school");
		}
	}
}
