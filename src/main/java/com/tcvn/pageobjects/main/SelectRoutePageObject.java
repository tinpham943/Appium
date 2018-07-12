package com.tcvn.pageobjects.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.tcvn.core.ExcelUtils;
import com.tcvn.driverhelp.Constant;
import com.tcvn.pageobjects.BasePageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SelectRoutePageObject extends BasePageObject {

	@AndroidFindBy(id = "toolbarCenterTitle")
	private MobileElement title_selectRoute;

	@AndroidFindBy(id = "searchRouteIdField")
	private MobileElement fld_search;

	@AndroidFindBy(id = "searchIcon")
	private MobileElement btn_search;
	
	@AndroidFindBy(id = "buildRouteBtn")
	private MobileElement btn_buildRoute;
	
	@AndroidFindBy(id = "secondInputField")
	private MobileElement fld_routeID;
	
	@AndroidFindBy(id = "thirdCommentInputField")
	private MobileElement fld_cmtBuildRoute;
	
	@AndroidFindBy(id = "startBuildingRouteBtn")
	private MobileElement btn_startBuildRoute;
	
	@AndroidFindBy(id = "inputNextToSpinner")
	private MobileElement fld_runID;
	
	@AndroidFindBy(id = "thirdDatePickerInputField")
	private MobileElement fld_PlannedEndTime;
	
	@AndroidFindBy(id = "button1")
	private MobileElement btn_clockOK;
	
	@AndroidFindBy(id = "startBuildingRouteBtn")
	private MobileElement btn_CreateRun;
	
	@AndroidFindBy(id = "setAsNewStopBtn")
	private MobileElement btn_setNewStop_PreviewRoute;
	
	@AndroidFindBy(id = "inputNextToSpinner")
	private MobileElement fld_stopID;
	
	@AndroidFindBy(id = "noOfStudentInputField")
	private MobileElement fld_noOfStd;
	
	@AndroidFindBy(id = "thirdCommentByStudentInputField")
	private MobileElement fld_cmtNewStop;
	
	@AndroidFindBy(id = "startBuildingRouteBtn")
	private MobileElement btn_setNewStop_CreateScreen;
	
	@AndroidFindBy(id = "finishRouteButton")
	private MobileElement btn_finishRoute;
	
	@AndroidFindBy(id = "endRunButton")
	private MobileElement btn_endRun;
	
	@AndroidFindBy(id = "alertOkButton")
	private MobileElement btn_alertOK;
	
	@AndroidFindBy(id = "secondRouteList")
	private MobileElement grd_firstResult;

	//Constructor
	public SelectRoutePageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	public String getTitle() {
		return title_selectRoute.getText();
	}

	public void sendkeys(Robot robot, String keys) {
		for (char c : keys.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (KeyEvent.CHAR_UNDEFINED == keyCode) {
				throw new RuntimeException(
					"Key code not found for character '" + c + "'");
			}
			robot.keyPress(keyCode);
			robot.delay(100);
		}
	}
	public String createRandomIdByDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmmss");
		return sdf.format(date);
	}
	/** Function to select routes by Excel
	 * @author datle
	 */
	public DrivingPageObject selectRouteByExcel(int routeNo, String site) throws Exception {
		ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_RouteToTest, "AllRun_"+site);
		String ID = ExcelUtils.getCellData(routeNo, 0);
		
		wait.until(ExpectedConditions.visibilityOf(fld_search));
		System.out.println("Search route " + ID);
		fld_search.setValue(ID);
		Thread.sleep(300);
		btn_search.click();
		MobileElement route = driver.findElement(By.xpath(
				"//android.widget.TextView[@resource-id=\"com.edulog.driverportal"+site+":id/idTextView\""
				+ "and contains(@text,'"+ID+"')]"));
		route.click();
		return new DrivingPageObject(driver);
	}

	//Function to select Route by ID
	public DrivingPageObject selectRouteByID(String ID, String site) {
		wait.until(ExpectedConditions.visibilityOf(fld_search));
		fld_search.setValue(ID);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
		btn_search.click();
		MobileElement route = driver.findElement(By.xpath(
				"//android.widget.TextView[@resource-id=\"com.edulog.driverportal"+site+":id/idTextView\""
				+ "and contains(@text,'"+ID+"')]"));
		route.click();
		return new DrivingPageObject(driver);
	}

	//Function to select Route by ID
	public DrivingPageObject selectRouteByID(String ID) {
		wait.until(ExpectedConditions.visibilityOf(fld_search));
		fld_search.setValue(ID);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
		btn_search.click();
		MobileElement route = driver.findElement(By.xpath(
				"//android.widget.TextView[@resource-id=\"com.edulog.driverportal:id/idTextView\""
				+ "and contains(@text,'"+ID+"')]"));
		route.click();
		return new DrivingPageObject(driver);
	}

	//Function to select Route by scroll and touch
	public DrivingPageObject selectRouteByScroll(String ID) {
		System.out.println("Scroll and select Route: "+ID);
		MobileElement route = driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()"
				+ ".resourceId(\"com.edulog.driverportal:id/secondRouteList\"))"
				+ ".scrollIntoView(new UiSelector().textContains(\""+ID+"\"))");
		route.click();
		return new DrivingPageObject(driver);
	}
	
	/** Verify if Previous Driven Route & Recent Route list displaying correctly.
	 * @author datle
	 */
	public boolean checkHistoryRoute(String txtRecentRoute1, String txtRecentRoute2) {
		boolean check = false;
		String chkPreviousRoute = driver.findElements(By.id("idTextView")).get(0).getText();
		String chkRecentRoute1 = driver.findElements(By.id("idTextView")).get(1).getText();
		String chkRecentRoute2 = driver.findElements(By.id("idTextView")).get(2).getText();
		// print to check
		System.out.println(chkPreviousRoute);
		System.out.println(chkRecentRoute1);
		System.out.println(chkRecentRoute2);
		if(chkPreviousRoute.contains(txtRecentRoute2)
				&& chkRecentRoute1.contains(txtRecentRoute1)
				&& chkRecentRoute2.contains(txtRecentRoute2)) {
			check = true;
		}
		return check;
	}
	public void buildNewRun(String idRoute,String idRun) throws InterruptedException, AWTException {
		
		btn_buildRoute.click();
		Thread.sleep(500);
		fld_routeID.setValue(idRoute);
		driver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(500);
		fld_cmtBuildRoute.click();	
		//should only use this for emulator
		Robot robot = new Robot(); 
		Thread.sleep(500);
		sendkeys(robot, "2");
	    driver.pressKeyCode(AndroidKeyCode.BACK);  
		btn_startBuildRoute.click();
		Thread.sleep(500);
		fld_runID.click();
		Thread.sleep(500);
		sendkeys(robot, idRun);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_PlannedEndTime.click();
	    Thread.sleep(500);
	    btn_clockOK.click();
	    btn_CreateRun.click();
	    Thread.sleep(500);
	    //create the 1st stop
	    btn_setNewStop_PreviewRoute.click();
	    Thread.sleep(500);
	    fld_stopID.click();
		sendkeys(robot, "1");
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_noOfStd.click();
		sendkeys(robot, "2");
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_cmtNewStop.click();
		sendkeys(robot, "1");
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    btn_setNewStop_CreateScreen.click();
	    Thread.sleep(500);
	    //create the 2nd stop
	    btn_setNewStop_PreviewRoute.click();
	    Thread.sleep(500);
	    fld_stopID.click();
		sendkeys(robot, "2");
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_noOfStd.click();
		sendkeys(robot, "3");
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_cmtNewStop.click();
		sendkeys(robot, "2");
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    btn_setNewStop_CreateScreen.click();
	    Thread.sleep(500);
	    
	    btn_endRun.click();
	    Thread.sleep(500);
	    btn_alertOK.click();
	    Thread.sleep(500);
		fld_runID.click();
		Thread.sleep(500);
		sendkeys(robot, idRun);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    btn_CreateRun.click();
	    System.out.println("Alert text Is -&gt; " +"Time cannot be empty");
	}
	
	/** THIS CASE IS PENDING DUE TO THE FEATURE HAS NOT IMPLEMENTED YET.
	 * @author datle
	 */
	public void buildNewRoute() throws InterruptedException, AWTException {
		btn_buildRoute.click();
		Thread.sleep(500);
		fld_routeID.setValue("1");
		driver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(500);
		fld_cmtBuildRoute.click();
		
		//should only use this for emulator
		Robot robot = new Robot();      
//	    robot.delay(2000);
	    robot.keyPress(KeyEvent.VK_2);
	    driver.pressKeyCode(AndroidKeyCode.BACK);

		btn_startBuildRoute.click();
		Thread.sleep(500);
		fld_runID.click();
	    robot.keyPress(KeyEvent.VK_2);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_PlannedEndTime.click();
	    Thread.sleep(500);
	    btn_clockOK.click();
	    btn_CreateRun.click();
	    Thread.sleep(500);
	    //create the 1st stop
	    btn_setNewStop_PreviewRoute.click();
	    Thread.sleep(500);
	    fld_stopID.click();
	    robot.keyPress(KeyEvent.VK_1);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_noOfStd.click();
	    robot.keyPress(KeyEvent.VK_2);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_cmtNewStop.click();
	    robot.keyPress(KeyEvent.VK_1);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    btn_setNewStop_CreateScreen.click();
	    Thread.sleep(500);
	    //create the 2nd stop
	    btn_setNewStop_PreviewRoute.click();
	    Thread.sleep(500);
	    fld_stopID.click();
	    robot.keyPress(KeyEvent.VK_2);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_noOfStd.click();
	    robot.keyPress(KeyEvent.VK_3);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    fld_cmtNewStop.click();
	    robot.keyPress(KeyEvent.VK_2);
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    btn_setNewStop_CreateScreen.click();
	    Thread.sleep(500);
	    
	    btn_finishRoute.click();
	    Thread.sleep(500);
	    btn_alertOK.click();
	    Thread.sleep(500);
	    
	    driver.findElement(By.id("dummyFieldForSwipe")).click();
	    List<MobileElement> lstMenu = driver.findElementsById("com.edulog.driverportal:id/menuItem");
		for (int i=0; i<= lstMenu.size(); i++) {
			if (lstMenu.get(i).getText().equals("Select Route")) {
				lstMenu.get(i).click();
				break;
			}
		}
		btn_alertOK.click();
	    Thread.sleep(500);
	    fld_search.click();
	    driver.pressKeyCode(AndroidKeyCode.BACK);
	    
	    // UNLOADED ROUTE WILL BE SHOWN BUT THE FEATURE HAS NOT IMPLEMENTED YET.
	}
	
	/**
	 * @author datle
	 */
	public boolean checkRecentRouteEmpty() {
		boolean check = false;
		String txt = driver.findElements(By.id("idTextView")).get(1).getText();
		if(txt.length() == 0) {
			check = true;
		} else {
			System.out.println("Recent Route currently has the value: " + txt);
		}
		return check;
	}
}
