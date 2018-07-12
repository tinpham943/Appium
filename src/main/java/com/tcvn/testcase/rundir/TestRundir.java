package com.tcvn.testcase.rundir;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.core.ExcelUtils;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class TestRundir extends CoreTestTemplate {
	Logger logger = Logger.getLogger("MyLog");
	FileHandler fh = null;
	static final String logPath = System.getProperty("user.dir")+"/log/";

	@DataProvider(name = "dataInput")
	public static Object[][] credentials() throws Exception {
		//Setting up the Test Data Excel file
		Object[][] testObjArray = ExcelUtils.getTableArray(System.getProperty("user.dir")+"/"+fileExcel, sheetExcel);
		return testObjArray;
	}

	@Test(dataProvider = "dataInput")
	public void testRunDir(String iNumber, String sRoute, String sRuns, String sGPX, String sGPXNote) {

		File dirLog = new File(logPath);
		if(!dirLog.exists())
			dirLog.mkdir();
		String sFileName = sRuns.trim();
		System.out.println("Runs: "+sFileName);
		if (sFileName.contains("/") || sFileName.contains(":")) {
			sFileName = sFileName.replaceAll("[:|/]", "");
			System.out.println("Runs after replace: " +sFileName);
		}

		try {
			try {
				// This block configure the logger with handler and formatter
				fh = new FileHandler(logPath+iNumber+"_"+sRoute+"_"+sFileName+".log");
				logger.addHandler(fh);
				logger.setLevel(Level.ALL);
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (ExcelUtils.getCellData(Integer.parseInt(iNumber), 5) == null) {
				//Login to Driver Portal
				loginPage = new LoginPageObject(driver);
				loginPage.setSite(strIPServer, strIPArea);
				punchesPage = loginPage.loginAs("Automation", "8382", "9999");

				//Change to Select Route screen
				menuPage.clickMenuOpt("Select Route");
				selectRoutePage = new SelectRoutePageObject(driver);

				//Select Route
				drivingPage = selectRoutePage.selectRouteByID(sRoute);
				System.out.println("Show alert message: "+drivingPage.getAlertMsg());
				drivingPage.clickAlertOK();

				//Select Runs
				drivingPage.selectRun(sRuns);

				//Set time according to the time of the 1st stop
				setTimeAsFirstStop();

				//Start Route
				drivingPage.clickStartRoute();
				System.out.println("Show alert message: " + drivingPage.getAlertMsg());
				drivingPage.clickAlertOK();

				logger.info("Start Route_Run: "+sRoute+"_"+sRuns.trim());

				//Setting new time out
				stopPage = new StopPageObject(driver);
	
				//Start GPX file
				setFileMock(sGPX, "1000");
	
				//Get total time of GPX file
				int GPXnote = Integer.parseInt(sGPXNote);
	
				int i = 0 ;
				boolean blnCrash = false;
				//Get current time and set end time
				Calendar currentTime = Calendar.getInstance();
				Calendar endTime = Calendar.getInstance();
				endTime.add(Calendar.SECOND, GPXnote);
				System.out.println("Current Time :" + currentTime.getTime());
				System.out.println("End Time :" + endTime.getTime());
	
				setNewTimeOut(100);
				do {
					//Get current time
					currentTime = Calendar.getInstance();
					//Check Driving mode every 5 minutes
					if (currentTime.get(Calendar.MINUTE)%5 == 0
							&& currentTime.get(Calendar.SECOND) == 00
							&& i == 0) {
						String currentAct = driver.currentActivity();
						logger.info("[Check app] Current activity is: "+currentAct);
						System.out.println("Show time :"+currentTime.getTime());
						i = 1;
						//Check crash app
						if (currentAct == null) {
							System.out.println("Crash app");
							blnCrash = true;
							break;
						}
						try {
							drivingPage.clickAlertCancel();
						} catch (Exception ex) {}
					}
					if (currentTime.get(Calendar.SECOND) != 00) {
						i = 0;
					}
				} while (currentTime.before(endTime));

				resetTimeOut();
				swapActivities("AndroidMockGpx");
				stopMock();

	//			ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_RouteToTest_Qui, "Sheet1");
				if (blnCrash == false) {
					System.out.println("This run is success.");
					logger.info("Route_Run: "+sRoute+"_"+sRuns.trim()+" is finished.");
	//				ExcelUtils.setCellData("PASS", Integer.parseInt(iNumber), 5);
					driver.resetApp();
				} else {
					logger.info("Route_Run: "+sRoute+"_"+sRuns.trim()+" is failed.");
					fh.close();
	//				ExcelUtils.setCellData("FAIL", Integer.parseInt(iNumber), 5);
					driver.launchApp();
					fail("App crash.");
				}
			} else {
				logger.info("Route_Run: "+sRoute+"_"+sRuns.trim()+" is tested.");
			}
			fh.close();
		} catch(Exception e) {
			logger.info(e.getMessage());
			logger.info("Route_Run: "+sRoute+"_"+sRuns+" is failed.");
			fh.close();
			driver.resetApp();
			e.printStackTrace();
			fail();
		}
	}
}
