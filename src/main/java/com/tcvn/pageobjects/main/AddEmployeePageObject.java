package com.tcvn.pageobjects.main;

import java.awt.AWTException;
import java.awt.Robot;
import com.tcvn.pageobjects.MainPageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class AddEmployeePageObject extends MainPageObject {

	@AndroidFindBy(id = "idEditText")
	private MobileElement txtEmployeeID;

	@AndroidFindBy(id = "passwordEditText")
	private MobileElement txtPassword;

	@AndroidFindBy(id = "btnGo")
	private MobileElement btnGo;

	@AndroidFindBy(id = "btnPunchIn")
	private MobileElement btn_PunchIn;

	@AndroidFindBy(id = "btnPunchOut")
	private MobileElement btn_PunchOut;

	@AndroidFindBy(id = "userValue")
	private MobileElement userValue;

	@AndroidFindBy(id = "jobCodeValue")
	private MobileElement jobCodeValue;

	@AndroidFindBy(id = "btnCancel")
	private MobileElement btnCancel;

	@AndroidFindBy(id = "punchInAs")
	private MobileElement punchInAs;

	@AndroidFindBy(id = "perTask")
	private MobileElement perTask;

	@AndroidFindBy(id = "timeIn")
	private MobileElement timeIn;
	
	@AndroidFindBy(id = "timeOut")
	private MobileElement timeOut;
	
	private Robot robot;

	// Constructor
	public AddEmployeePageObject(AndroidDriver<MobileElement> driver) throws AWTException {
		super(driver);
		robot = new Robot();
	}

	// Function to set values in DriverID Field
	public void inputEmployeeID(String driverID) throws AWTException {
		System.out.println("Input DriverID: " + driverID);
		txtEmployeeID.click();
		sendkeys(robot, driverID);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	// Function to set values in Password Field
	public void inputPassword(String password) throws AWTException {
		System.out.println("Input Password: " + password);
		txtPassword.click();
		sendkeys(robot, password);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	// Function to click Go button
	public PunchesPageObject clickGo() {
		System.out.println("Click Go button");
		btnGo.click();
		return new PunchesPageObject(driver);
	}

	public PunchesPageObject clickPunchin() {
		System.out.println("Click Punchin button");
		btn_PunchIn.click();
		return new PunchesPageObject(driver);
	}

	public PunchesPageObject clickPunchout() {
		System.out.println("Click Punchout button");
		btn_PunchOut.click();
		return new PunchesPageObject(driver);
	}
	
	public String getDriverName() {
		System.out.println("get Driver Name");
		return userValue.getText();
	}
}
