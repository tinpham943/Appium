package com.tcvn.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MockPageObject extends BasePageObject {

	//==========Main screen of MockGPX==========
	@AndroidFindBy(id = "com.twolinessoftware.android:id/file_manager")
	private MobileElement btnFileMgr;

	@AndroidFindBy(id = "com.twolinessoftware.android:id/editTextDelay")
	private MobileElement txtTimeDelay;

	@AndroidFindBy(id = "com.twolinessoftware.android:id/start")
	private MobileElement btnStart;

	@AndroidFindBy(id = "com.twolinessoftware.android:id/stop")
	private MobileElement btnStop;

	//==========Select file screen of MockGPX==========
	@AndroidFindBy(id = "com.ghisler.android.TotalCommander:id/curDir")
	private MobileElement dir;

	@AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Go to home folder')]")
	private MobileElement iconHome;

	@AndroidFindBy(id = "com.ghisler.android.TotalCommander:id/okBtn")
	private MobileElement btnOpen;

	@AndroidFindBy(id = "com.ghisler.android.TotalCommander:id/newName")
	private MobileElement txtFileName;

	// Constructor
	public MockPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	// Function to click Open to select file GPX
	public void clickOpenMgr() {
		System.out.println("Click Open button");
		btnFileMgr.click();
	}

	//Function to check Home icon
	public String getDir() {
		return dir.getText();
	}

	//Function to click Home icon
	public void clickHomeIcon() {
		System.out.println("Click Home icon");
		iconHome.click();
	}

	//Function to open download folder
	public void clickDownload() {
		System.out.println("Click Downloaded files");
		MobileElement downloadElement = driver.findElement(
				By.xpath("//android.widget.TextView[contains(@text,'Downloaded files')]"));
		downloadElement.click();
	}

	//Function to set Delay time
	public void typeDelayTime(String time) {
		System.out.println("Set Delay time: "+time);
		txtTimeDelay.setValue(time);
		driver.hideKeyboard();
	}

	//Function to start MockGPX
	public void clickStart() {
		System.out.println("Start MockGPX");
		btnStart.click();
	}
	
	//Function to stop MockGPX
	public void clickStop() {
		System.out.println("Stop MockGPX");
		wait.until(ExpectedConditions.elementToBeClickable(btnStop)).click();
	}

	//Function to confirm selected file GPX
	public void clickOpenFile() {
		System.out.println("Selected GPX file");
		btnOpen.click();
	}

	//Function to select file GPX by name
	public void selectFile(String fileName) {
		txtFileName.clear();
		try {
			driver.hideKeyboard();
		} catch(Exception e) { }
		MobileElement file = driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.ghisler.android.TotalCommander:id/fileList\"))"
				+ ".scrollIntoView(new UiSelector().textContains(\""+fileName+"\"))");
		System.out.println("Select file: "+file.getText());
		file.click();
	}

	//Function to select file GPX by name
	public void selectFileByName(String fileName) {
		txtFileName.clear();
		try {
			driver.hideKeyboard();
		} catch(Exception e) { }
		txtFileName.setValue(fileName);
		driver.hideKeyboard();
	}
}
