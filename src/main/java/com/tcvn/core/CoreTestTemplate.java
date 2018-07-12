package com.tcvn.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.tcvn.driverhelp.Constant;
import com.tcvn.driverhelp.DriverHelp;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.MenuPageObject;
import com.tcvn.pageobjects.MockPageObject;
import com.tcvn.pageobjects.SettingPageObject;
import com.tcvn.pageobjects.main.AddEmployeePageObject;
import com.tcvn.pageobjects.main.AssignedRoutePageObject;
import com.tcvn.pageobjects.main.DrivingPageObject;
import com.tcvn.pageobjects.main.PunchesPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;
import com.tcvn.pageobjects.main.SwitchSessionPageObject;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class CoreTestTemplate {

	// Declare AndroidDriver
	protected static AndroidDriver<MobileElement> driver;

	// Declare PageObject
	protected static MenuPageObject menuPage;
	protected static MockPageObject mockPage;
	protected static StopPageObject stopPage;
	protected static LoginPageObject loginPage;
	protected static SettingPageObject settingPage;
	protected static PunchesPageObject punchesPage;
	protected static AssignedRoutePageObject assignedRoutePage;
	protected static SelectRoutePageObject selectRoutePage;
	protected static DrivingPageObject drivingPage;
	protected static SwitchSessionPageObject switchSessionPage;
	protected static AddEmployeePageObject addEmployeePage;

	// Declare Activity
	protected static Activity mockAct;
	protected static Activity settingAct;
	protected static Activity driverPortal;

	// Declare setting
	protected static String fileExcel 		= null;
	protected static String sheetExcel 		= null;
	protected static String strIPServer 	= null;
	protected static String strIPArea 		= null;
	protected static String strSite 		= null;
	protected static InputStream input;
	protected static Properties prop = new Properties();
	String site = null;

	@BeforeSuite
	protected void setUpBeforeSuite() {
	}

	@Parameters("OS")
	@BeforeClass
	protected void setUpBeforeTestClass(String OS) throws IOException {
		// Create Driver
		driver = DriverHelp.getDriver(OS);
		// Create PageObject
		menuPage 	= new MenuPageObject(driver);
		mockPage 	= new MockPageObject(driver);
		settingPage = new SettingPageObject(driver);
		// Create Activity
		settingAct 	= new Activity(Constant.Setting_AppPackage, Constant.Setting_AppActivity);
		mockAct 	= new Activity(Constant.Mock_AppPackage, Constant.Mock_AppActivity);
		driverPortal = new Activity(Constant.DP_AppPackage, Constant.DP_AppActivity);

		try {
			input = new FileInputStream("config.properties");
			//Load a properties file
			prop.load(input);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			input.close();
		}
		strIPServer = prop.getProperty("IPServer");
		strIPArea = prop.getProperty("IPArea");
		fileExcel = prop.getProperty("excel");
		sheetExcel = prop.getProperty("sheet");
		strSite = strIPArea.substring(3);
		strSite = strSite.substring(0, strSite.length()-1);
	}

	@BeforeMethod
	protected String setSiteBeforeTest() {
		// Get the site to test from UI
		if(strIPArea.contains("apieastallen")) {
			site = "EastAllen";
		} else if(strIPArea.contains("apiclayton")) {
			site = "Clayton";
		}
		return site;
	}

	@AfterClass
	protected static void tearDownAfterTestClass() {
		// Close application, quit driver
		driver.closeApp();
		driver.quit();
	}

	// Setting new timeout
	protected void setNewTimeOut(long timeOut) {
		System.out.println("Set new Timeout: " + timeOut);
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	// Reset timeout to 80
	protected void resetTimeOut() {
		System.out.println("Set time out: 80");
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}

	/**
	 * Export the message console to txt file.
	 * @author: DatLe
	 */
	public void printConsole(String outputFileName) throws IOException {
		try {
			File directoryLogs = new File("D:/DPAutoLog");
			directoryLogs.mkdir();	  
			File fileLog = new File(directoryLogs, outputFileName);
			fileLog.createNewFile();
			FileOutputStream f = new FileOutputStream(fileLog);
			System.setOut(new PrintStream(f));
			f.close();
		} catch(Exception e) {
			System.out.println("Cannot print console!");
		}
	}

	/**
	 * Function to stop Mock app
	 * @author DatLe
	 */
	public void stopMock() throws InterruptedException {
		mockPage.clickStop();
		try {
			driver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(300);
			driver.pressKeyCode(AndroidKeyCode.BACK);
		} catch(Exception e) {}
	}

	/**
	 * Function to handle Home screen selector
	 * @author DatLe
	 */
	public void handleHomeScreenSelector() {
		try {
			// Reduce waiting time to check unless we waste 3000s waiting.
			setNewTimeOut(3);
			// Locate the 2nd option of Home screen pop-up.
			MobileElement check = driver.findElement(By.id("android:id/text1"));

			// If DP is at the 2nd position -> click "Always" to set default screen as Home,
			// If DP is at the 1st position -> click the 2nd choice. 
			if(check.getText().contains("Driver Portal")) {
				driver.findElement(By.id("android:id/button_always")).click();
			} else {
				check.click();
			}
		} catch(Exception e) {
			System.out.println("No home screen pop-up!");
		}
		resetTimeOut();
	}

	/** Function to close all app
	 * @author datle
	 */
	public void closeAllApp() throws InterruptedException {

		// KEYCODE_APP_SWITCH
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
		Thread.sleep(3000);

		// Handling home screen selector
		this.handleHomeScreenSelector();

		// Select Recent app btn after handling Home screen selector
		try {
			if(!(driver.findElements(By.id("com.android.systemui:id/activity_description")).size() > 0)) {
				driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
				Thread.sleep(3000);
			}
		} catch(Exception e) {}

		// click X btn on all recent app
		List<MobileElement> btnCloseApp = driver.findElements(By.id("com.android.systemui:id/dismiss_task"));
		for(MobileElement focus : btnCloseApp) {
			focus.click();
		}
	}

	/** Function to switch between activities
	 * @author datle
	 */
	public boolean swapActivities(String app) throws InterruptedException {
		setNewTimeOut(3);
		boolean isSwapped = false;

		// Press HOME button
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_HOME);
		Thread.sleep(500);

		// Handling home screen selector
		this.handleHomeScreenSelector();
		
		// KEYCODE_APP_SWITCH
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
		Thread.sleep(1000);

		// Select Recent app btn after handling Home screen selector
		try {
			if(!(driver.findElements(By.id("com.android.systemui:id/activity_description")).size() > 0)) {
				driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
				Thread.sleep(1000);
			}
		} catch(Exception e) {}

		// Tap our first app
		List<MobileElement> appList = driver.findElements(By.id("com.android.systemui:id/activity_description"));
		for (int i = 0; i < appList.size(); i++) {
			System.out.println("Moving at app: " + appList.get(i).getText()) ;
			if (appList.get(i).getText().contains(app)) {
				try {
					appList.get(i).click();
					appList.get(i).click();
				} catch(Exception e) {}
				isSwapped = true;
				break;
			}
		}
		resetTimeOut();
		return isSwapped;
	}

	// Function to start a new DP
	protected void startDP(String site) {
		switch(site) {
			case "EastAllen":
				driver.startActivity(driverPortal);
			default:
				driver.startActivity(driverPortal);
		}
	}

	/**
	 * Function to set .GPX file by Excel
	 * @author owner
	 */
	protected String setFileMockByExcel(int fileName, String timeDelay, String site) throws Exception {
		// Get gpx name from Excel file
		ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_RouteToTest, "AllRun_"+site);
		String gpx = ExcelUtils.getCellData(fileName, 2);

		driver.startActivity(mockAct);
		mockPage.clickOpenMgr();
		if(!mockPage.getDir().equals("*")) {
			mockPage.clickHomeIcon();
		}
		mockPage.clickDownload();
		mockPage.selectFile(gpx);
		mockPage.clickOpenFile();
		mockPage.typeDelayTime(timeDelay);
		mockPage.clickStart();
		mockPage.clickStart();
		driver.pressKeyCode(AndroidKeyCode.BACK);
		return gpx;
	}

	//Function to set .GPX file
	protected void setFileMock(String fileName, String timeDelay) {
		System.out.println("Switch to MockGPX app");
		//Open MockGPX application
		driver.startActivity(mockAct);
		mockPage.clickOpenMgr();
		if(!mockPage.getDir().equals("*")) {
			mockPage.clickHomeIcon();
		}
		mockPage.clickDownload();
		//Select file .GPX
		mockPage.selectFileByName(fileName);
		mockPage.clickOpenFile();
		//Set timeDelay
		mockPage.typeDelayTime(timeDelay);
		//Start MockGPX application
		mockPage.clickStart();
		mockPage.clickStart();
		//Back to DP
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	/**Setting to next date function
	 * @author datle
	 */
	protected void setNextDate() throws ParseException, InterruptedException {
		driver.startActivity(settingAct);
		settingPage.clickDateAndTime();
		settingPage.clickSetDate();
		settingPage.setToNextDate();
		clickBackBtn();
	}
	
	/**
	 * Function to set time with 24-hour format,
	 * also disable auto date & time + time zone, then select GMT.
	 * @author DatLe
	 */
	protected void setTime24hFormat(int hour, int minute, String gmt) throws InterruptedException {
		driver.startActivity(settingAct);
		settingPage.clickDateAndTime();
		settingPage.setGMT(gmt);
		settingPage.switch24HourFormat("ON");
		settingPage.clickSetTime();
		settingPage.setTime(hour, minute);
		clickBackBtn();
	}
	
	/** Function to set System time
	 * Updated condition: always set time format to 12-hour.
	 * @author quingo
	 * @updater datle
	 */
	protected void setTimeAsFirstStop() {
		// Get the value of the 1st stop time
		String timeGet = driver.findElement(By.id("timeTextView")).getText();
		System.out.println("Starting time of the 1st stop: " + timeGet);
		String[] timeSplit = timeGet.split(":");
		int hourToSet = Integer.parseInt(timeSplit[0].trim());
		int minToSet = Integer.parseInt(timeSplit[1].trim());
		
		System.out.println("Open setting");
		
		//Open Device's setting
		driver.startActivity(settingAct);
		
		//Set Date & Time (Hour, Minute)
		settingPage.clickDateAndTime();
		// Always turn 24-hour format OFF
		settingPage.switch24HourFormat("ON");
		settingPage.clickSetTime();
		settingPage.setTimeByData(hourToSet, minToSet);
		clickBackBtn();
	}

	/** Function to set System time
	 * Updated condition: always set time format to 12-hour.
	 * @author quingo
	 * @updater datle
	 */
	protected void setTime(int hour, int minute, String period) {
		System.out.println("Open setting");
		
		//Open Device's setting
		driver.startActivity(settingAct);
		
		//Set Date & Time (Hour, Minute)
		settingPage.clickDateAndTime();
		// Always turn 24-hour format OFF
		settingPage.switch24HourFormat("OFF");
		settingPage.clickSetTime();
		settingPage.setTime(hour, minute, period);
		clickBackBtn();
	}

	protected void setTimeByCmd(String date,String hours, String minutes) {
		try {
			Process rc = Runtime.getRuntime().exec("adb shell \"toolbox date -s "+date+"."+hours+minutes+"00\"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void setTimeByCmd1(String date,String time) {
		try {
			Process rc = Runtime.getRuntime().exec("adb shell \"toolbox date -s "+date+"."+time+"\"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Function to turn on/off Wifi
	protected void setWifi(String status) {
		System.out.println("Open setting");
		//Open Device's setting
		driver.startActivity(settingAct);
		settingPage.clickWifi();
		//Verify Wifi status
		if(!status.equals(settingPage.getWifi())) {
			//Setting on/off Wifi
			System.out.println("Turn " +status+" Wifi");
			settingPage.switchWifi();
		}
		settingPage.switchWifi();
		clickBackBtn();
	}

	/**
	 * Updated the condition: checking if the airplane mode is ON/OFF
	 * Updated condition: clicking Retry btn if app was launched when airplane = ON
	 * @author quingo
	 * @updater datle
	 */
	protected void setAirPlane(String status) {
		System.out.println("Set airplane mode to " + status);
		System.out.println("Open setting");
		//Open Device's setting
		driver.startActivity(settingAct);
		settingPage.clickMore();
		// Verify Airplane status
		if(!status.equals(settingPage.getAirplane())) {
			//Setting on/off AirPlane mode
			System.out.println("Turn " +status+" Airplane mode");
			settingPage.switchAirPlane();
		}
		clickBackBtn();
	}

	//Back to DP screen
	private void clickBackBtn() {
		driver.pressKeyCode(AndroidKeyCode.BACK);
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}
}
