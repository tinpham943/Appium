package com.tcvn.testcase.drivingmode;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.core.DriverPortalChecker;
import com.tcvn.core.ExcelUtils;
import com.tcvn.driverhelp.Constant;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;

import io.appium.java_client.android.AndroidDriver;

/**
 * Test case to test all runs, all routes. Verify if there are any crashing cases.
 * @author DatLe
 */
public class Task_AllRunTest extends CoreTestTemplate {

	final static Logger logger = Logger.getLogger(Task_AllRunTest.class);

	// Declare the isSwapped checker for function swapActivities
	boolean isSwapped = false;

	// Declare GPX file to get if there are any errors
	String gpxFile = null;

	// Declare the Excel file to get/set data
	String excelFile = Constant.File_RouteToTest;

	// Declare the driver No. to log in (get from UI)
	String strDriverNo = DriverPortalChecker.getUIDriverNo();
	int loginDriverNo = Integer.parseInt(strDriverNo);

	// Declare the site to set
	String strIPServer = DriverPortalChecker.getUIIPServer();
	String strIPArea = DriverPortalChecker.getUIIPArea();
	String site = setSiteBeforeTest();

	@Test
	public void AllRunTest_InputAPI() {
		try {
			// Declare the result to write into Excel and the result checker to skip tests
			String result = null;
			String resultChecker = null;

			// Access to Excel
			ExcelUtils.setExcelFile(Constant.Path_TestData + excelFile, "AllRun_"+site);

			// Declare the testing value
			String loopIniExcel = ExcelUtils.getCellData(3, 5);
			int loopIni = Integer.parseInt(loopIniExcel);

			String loopMaxExcel = ExcelUtils.getCellData(1, 5);
			int loopMax = Integer.parseInt(loopMaxExcel);

			// Select site then log in to DP
			loginPage = new LoginPageObject(driver);
			loginPage.setSite(strIPServer, strIPArea);
			loginPage.loginAs(site, loginDriverNo);

			do {
				ExcelUtils.setExcelFile(Constant.Path_TestData + excelFile, "AllRun_"+site);
				// Verify if the run has been tested yet
				do {
					resultChecker = ExcelUtils.getCellData(loopIni, 3);
					if(resultChecker.length() > 0) {
						loopIni++;
					}
				} while(resultChecker.length() > 0);

				// Initialize the test at 9:00 AM to prevent the app from being stuck in this scenario:
				// 		data has not been sync yet and device's time is around 4:00 PM  
				setTime24hFormat(9, 0, "+07");

				// Log the latest row of Excel that was just tested
				try {
					String loopRecord = Integer.toString(loopIni);
					ExcelUtils.setCellData(loopRecord, 3, 5);
				} catch(Exception e) {
					logger.error("Error at writing to Excel: ", e);
				}

				// Select the specific route by excel
				menuPage.clickMenuOpt("Select Route");
				selectRoutePage = new SelectRoutePageObject(driver);
				drivingPage = selectRoutePage.selectRouteByExcel(loopIni, site);
				drivingPage.clickAlertOK();

				// Select the specific run by excel
				drivingPage.selectRunByExcel(loopIni, site);

				// Set time according to the time of the 1st stop
				setTimeAsFirstStop();

				// Get the max time to finish the run
				String timeToFinishRunExcel = ExcelUtils.getCellData(loopIni, 4);
				int timeToFinishRun = Integer.parseInt(timeToFinishRunExcel);

				// Start Route
				drivingPage.clickStartRoute();
				drivingPage.clickAlertOK();

				// Start GPX file by Excel
				gpxFile = setFileMockByExcel(loopIni, "1000", site);

				// Each 10 min do an action till max time to finish the run (got in excel) reaches 0
				do {
					if(timeToFinishRun > 600) {
						try {
							Thread.sleep(600000);
							drivingPage.clickAlertCancel();
							Thread.sleep(500);
							timeToFinishRun -= 600;
						} catch(Exception e) {
							timeToFinishRun -= 600;
						}
					} else if(timeToFinishRun <= 600) {
						try {
							Thread.sleep(timeToFinishRun);
							drivingPage.clickAlertCancel();
							Thread.sleep(500);
							timeToFinishRun = 0;
						} catch(Exception e) {
							timeToFinishRun = 0;
						}
					}

					@SuppressWarnings("rawtypes")
					String activity = ((AndroidDriver) driver).currentActivity();
					if(activity != null) {
						if(activity.equals("com.edulog.driverportal.ui.activities.LoginActivity")) {
							result = "AUTO LOGOUT";
							break;
						}
						else if(!activity.equals("com.edulog.driverportal.ui.activities.LoginActivity")) {
							try {
								isSwapped = swapActivities("Driver Portal");
								if(isSwapped == true) {
									result = "PASSED";
									
									@SuppressWarnings("rawtypes")
									String activity1 = ((AndroidDriver) driver).currentActivity();
									if(activity1 != null) {
										if(activity1.equals("com.edulog.driverportal.ui.activities.LoginActivity")) {
											result = "AUTO LOGOUT";
											break;
										}
									}
								} else {
									logger.error("Might be stuck at switching apps");
									Runtime.getRuntime().exec("cmd.exe /c \"adb logcat>>DPLog"+loopIni+".txt\"");
									result = "FAILED";
									break;
								}

							} catch(Exception e) {
								logger.error("Error at switching apps: ", e);
								Runtime.getRuntime().exec("cmd.exe /c \"adb logcat>>DPLog"+loopIni+".txt\"");
								result = "FAILED";
								break;
							}
						}
					}
				} while(timeToFinishRun > 0);

				// Handling the alert at the end of the run
				setNewTimeOut(3);
				try {
					drivingPage.clickAlertCancel();
					Thread.sleep(500);
				} catch(Exception e) {}
				resetTimeOut();

				swapActivities("AndroidMockGpx");
				stopMock();

				isSwapped = swapActivities("Driver Portal");
				if(isSwapped == false) {
					startDP("");
					loginPage = new LoginPageObject(driver);
					try {
						loginPage.setSite(strIPServer, strIPArea);
					} catch(Exception e) {}
					loginPage.loginAs(site, loginDriverNo);
				}

				try {
					ExcelUtils.setCellData(result, loopIni, 3);
				} catch(Exception e) {
					logger.error("Error at writing test results: ", e);
				}

				loopIni++;

			} while(loopIni <= loopMax);

		} catch(Exception e) {
			logger.error("Failed at gpx: " + gpxFile, e);
		}
	}
}
