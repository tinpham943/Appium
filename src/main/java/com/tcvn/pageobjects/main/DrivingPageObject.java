package com.tcvn.pageobjects.main;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.tcvn.core.ExcelUtils;
import com.tcvn.driverhelp.Constant;
import com.tcvn.pageobjects.MainPageObject;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class DrivingPageObject extends MainPageObject {

	//=====Alert=====
	@AndroidFindBy(id = "alertContent")
	private MobileElement txtAlertMsg;

	@AndroidFindBy(id = "alertOkButton")
	private MobileElement btnAlertOK;

	@AndroidFindBy(id = "alertCancelButton")
	private MobileElement btnAlertCancel;

	//=====Preview Mode=====
	@AndroidFindBy(id = "startRouteButton")
	private MobileElement btnStartRoute;
	
	@AndroidFindBy(id = "nameRouteTextView")
	private MobileElement txtStopNameOnList;

	//=====Driving Mode=====
	@AndroidFindBy(id = "addStopBtn")
	private MobileElement btnAddStop;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Stops:']")
	private MobileElement txtSelectStop;

	@AndroidFindBy(id = "spinnerDropArrow")
	private MobileElement btnRunList;

	//Constructor
	public DrivingPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	/**
	 * Function to get the maximum amount of stops in the selected run 
	 * 	- Scroll to the end of stops list
	 * 	- Then get the number of the last stop
	 *  - Minus the last and the first stop No. to get the max amount of stops
	 * @author DatLe
	 */
	public int getMaxStop() {
		int maxStop = 0;

		// Get the 1st stop No.
		String txt_firstStopNo = txtStopNameOnList.getText();
		String[] txt_firstStopNoSplit = txt_firstStopNo.split("\\.");
		int firstStopNoToGet = Integer.parseInt(txt_firstStopNoSplit[1].trim());
		System.out.println("The 1st stop No. = " + firstStopNoToGet);

		/** Scroll to the element that appears when reaching the last stop
		// then get the latest stop info by this method:
		// - count the max size of stop displayed on the list view
		// - focus on the element that have get(max) */
		// Work around to scroll to the end of the stops list
		for(int i=1; i<=5; i++) {
			try {
				driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector().resourceId(\"com.edulog.driverportal:id/pointRouteRV\"))"
					+ ".scrollForward())");
			} catch(Exception e) {}
		}

		// Get the last stop No.
		List<MobileElement> stopDisplayed = driver.findElements(By.id("nameRouteTextView"));
		int lastStopDisplayed = stopDisplayed.size()-1;
		String txt_lastStopNo = driver.findElements(By.id("nameRouteTextView")).get(lastStopDisplayed).getText();
		String[] txt_lastStopNoSplit = txt_lastStopNo.split("\\.");
		int lastStopNoToGet = Integer.parseInt(txt_lastStopNoSplit[1].trim());
		System.out.println("The last stop No. = " + lastStopNoToGet);

		maxStop = lastStopNoToGet - firstStopNoToGet + 1;
		System.out.println("Max stops = " + maxStop);
		return maxStop;
	}

	/**
	 * Function to select the run (From/To)
	 * @author DatLe
	 */
	public void selectRun(String inputRun) throws InterruptedException {
		// Click the Run list
		btnRunList.click();
		Thread.sleep(1000);
		// Click the specific run
		 MobileElement run = driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()"
				+ ".resourceId(\"com.edulog.driverportal:id/secondRouteList\"))"
				+ ".scrollIntoView(new UiSelector().textContains(\""+inputRun+"\"))");
		run.click();
		Thread.sleep(400);
	}

	/** Function to select the run (From/To) by Excel
	 * @author datle
	 */
	public void selectRunByExcel(int inputRun, String site) throws Exception {
		ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_RouteToTest, "AllRun_"+site);
		String data = ExcelUtils.getCellData(inputRun, 1);
		// Click the Run list
		btnRunList.click();
		Thread.sleep(1000);
		// Click the specific run
		MobileElement run = driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()"
				+ ".resourceId(\"com.edulog.driverportal"+site+":id/secondRouteList\"))"
				+ ".scrollIntoView(new UiSelector().textContains(\""+data+"\"))");
		run.click();
		Thread.sleep(400);
	}

	//Function to get Message Error
	public String getAlertMsg() {
		return txtAlertMsg.getText().toString();
	}

	//Function to check if alert pop-up is shown
	public boolean isDisplayAlertPopUp() {
		return btnAlertCancel.isDisplayed();
	}

	//Function to check Add stop btn is clickable
	public boolean isAddStopClickable() {
		wait.until(ExpectedConditions.elementToBeClickable(btnAddStop));
		return btnAddStop.isEnabled();
	}

	//Function to click OK/Yes button on Alert
	public void clickAlertOK() {
		System.out.println("Click "+btnAlertOK.getText());
		btnAlertOK.click();
	}

	//Function to click Cancel/No button on Alert
	public void clickAlertCancel() {
		setNewTimeOut(3);
		System.out.println("Click "+btnAlertCancel.getText());
		btnAlertCancel.click();
	}

	//Function to check Select Stop view
	public boolean isShowSelectStop() {
		System.out.println("Show Select Stop view");
		return txtSelectStop.isDisplayed();
	}

	//Function to select a stop on Select Stop view
	public StopPageObject selectStop(String stopName) throws AWTException {
		System.out.println("Click "+stopName);
		driver.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='"+stopName+"']")).click();
		return new StopPageObject(driver);
	}

	//Function to click Start Route button
	public void clickStartRoute() {
		System.out.println("Click Start Route button");
		btnStartRoute.click();
	}

	//Function to click Add Stop button
	public void clickAddStop() {
		System.out.println("Click Add Stop button");
		wait.until(ExpectedConditions.elementToBeClickable(btnAddStop));
		btnAddStop.click();
	}
}
