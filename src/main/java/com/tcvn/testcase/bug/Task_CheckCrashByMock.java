package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
import org.testng.annotations.Test;
import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class Task_CheckCrashByMock extends CoreTestTemplate {
	
	@Test
	public void CheckCrashByMock() throws AWTException {
		
		boolean loop = true; // Condition to infinitely run the test case.
		int max = 1; // Maximum number of stops to be checked before restarting the test case.
		int count = 2; // Loop counter.

		try {
			loginPage = new LoginPageObject(driver);
//			printConsole("DPcheckCr.txt");
			punchesPage = loginPage.loginAs("Saigon", 1);
			
			//Changing time of tablet to 9:45 AM
			setTime(9, 45, "AM");
			
			System.out.println("Run Lan Thu: 1");

			//Select 03, Run To Novaland
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
			
			setNewTimeOut(100);
			
			for(int i = 1; i <= max; i++) {
				try {
					assertTrue(stopPage.isOpenStopView());
					stopPage.closeStopView();
				} catch(Exception e) {
					System.out.println("Skip!");
				}
			}
			
			while(loop == true) {
				try {
					System.out.println("== Run Lan Thu: " + count + " ==");
					
					setNewTimeOut(3);
					swapActivities("AndroidMockGpx");
					stopMock();
					swapActivities("Edulog Driver Portal");
					resetTimeOut();
					
					menuPage.clickMenuOpt("Select Route");
					selectRoutePage = new SelectRoutePageObject(driver);
					selectRoutePage.selectRouteByID("03");
					drivingPage.clickAlertOK();
					//Start Route
					drivingPage.clickStartRoute();
					drivingPage.clickAlertOK();
					setFileMock("Novaland_case1", "1000");
					
					setNewTimeOut(100);
	
					for(int i = 1; i <= max; i++) {
						try {
							assertTrue(stopPage.isOpenStopView());
							stopPage.closeStopView();
						} catch(Exception e) {
							System.out.println("Skip!");
						}
					}
						
					count++;
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}