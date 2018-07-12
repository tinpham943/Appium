package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.AssignedRoutePageObject;
import com.tcvn.pageobjects.main.PunchesPageObject;

/**
 * @author datle
 */
public class DP02 extends CoreTestTemplate {

	@BeforeClass
	public void setUp() throws Exception {
		//Login to DP
		loginPage = new LoginPageObject(driver);
		loginPage.setSite(strIPServer, strIPArea);
		punchesPage = loginPage.loginAs(strSite, 1);
	}

	@Test
	public void testAssignedRoute() throws InterruptedException {
		//Change to Assigned Route screen
		menuPage.clickMenuOpt("Assigned Route");
		assignedRoutePage = new AssignedRoutePageObject(driver);
		boolean check = false;
		check = assignedRoutePage.checkAssignedRoute("717");
		try {
			assertTrue(check); 
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(check);
		}
	}

	@Test
	public void testPunching() throws InterruptedException {
		//Change to Assigned Route screen
		menuPage.clickMenuOpt("Time & Attendance");
		punchesPage = new PunchesPageObject(driver);
		punchesPage.clickPunchIn();
		//get the data to compare for the 1st punching in
		String cbxPunchInAs1 = punchesPage.getValueDisplayed("title", 0);
		String cbxPerTask1 = punchesPage.getValueDisplayed("title", 1);
		String txtPunchInAs1 = punchesPage.getValueDisplayed("punchInAs", 1);
		String txtPerTask1 = punchesPage.getValueDisplayed("perTask", 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		String currentDate1 = dateFormat.format(new Date()).toString();
		String txtTimeIn1 = punchesPage.getValueDisplayed("timeIn", 1);

		punchesPage.switchPunchInAsComboBox();
		punchesPage.switchPerTaskComboBox();
		punchesPage.clickPunchIn();
		//get the data to compare for the 2nd punching in
		String txtTimeOut1 = punchesPage.getValueDisplayed("timeOut", 1);
		String cbxPunchInAs2 = punchesPage.getValueDisplayed("title", 0);
		String cbxPerTask2 = punchesPage.getValueDisplayed("title", 1);
		String txtPunchInAs2 = punchesPage.getValueDisplayed("punchInAs", 2);
		String txtPerTask2 = punchesPage.getValueDisplayed("perTask", 2);
		String currentDate2 = dateFormat.format(new Date()).toString();
		String txtTimeIn2 = punchesPage.getValueDisplayed("timeIn", 2);

		//wait for auto-logout
		Thread.sleep(604000);
		//get the time when the app is auto-logout
		String currentDate3 = dateFormat.format(new Date()).toString();
		boolean check = false;
		System.out.println("Re-login!");
		try {
			loginPage.inputPassword("9999");
			loginPage.clickLogin();
			String txtTimeOut2 = punchesPage.getValueDisplayed("timeOut", 2);
			if (txtPunchInAs1.equalsIgnoreCase(cbxPunchInAs1) 
					&& txtPerTask1.equalsIgnoreCase(cbxPerTask1)
					&& txtTimeIn1.equalsIgnoreCase(currentDate1)
					&& txtTimeOut1.equalsIgnoreCase(currentDate1)
					&& txtPunchInAs2.equalsIgnoreCase(cbxPunchInAs2) 
					&& txtPerTask2.equalsIgnoreCase(cbxPerTask2)
					&& txtTimeIn2.equalsIgnoreCase(currentDate2)
					&& txtTimeOut2.equalsIgnoreCase(currentDate3)) {
				check = true;
				assertTrue(check);
			}
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(check);
		}
	}
}