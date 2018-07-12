package com.tcvn.pageobjects;

import org.openqa.selenium.support.ui.ExpectedConditions;

import com.tcvn.core.ExcelUtils;
import com.tcvn.driverhelp.Constant;
import com.tcvn.pageobjects.main.PunchesPageObject;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPageObject extends BasePageObject {

	//========== Select Sites ==========
	@AndroidFindBy(id = "com.edulog.driverportal:id/ipServer")
	private MobileElement txtIPServer;

	@AndroidFindBy(id = "com.edulog.driverportal:id/ipArea")
	private MobileElement txtIPArea;

	@AndroidFindBy(id = "com.edulog.driverportal:id/changeAPIButton")
	private MobileElement btnChangeAPI;

	//========== Login Screen ==========
	@AndroidFindBy(id = "busIDText")
	private MobileElement txtBusID;

	@AndroidFindBy(id = "driverIDText")
	private MobileElement txtDriverID;

	@AndroidFindBy(id = "passwordText")
	private MobileElement txtPassword;

	@AndroidFindBy(id = "loginButton")
	private MobileElement btnLogin;

	@AndroidFindBy(id = "checkBoxRemember")
	private MobileElement ckbRemember;

	@AndroidFindBy(id = "errorEditTextLogin")
	private MobileElement txtError;

	@AndroidFindBy(id = "retry")
	private MobileElement btnRetry;
	
	@AndroidFindBy(id = "ipServer")
	private MobileElement txtipServer;

	@AndroidFindBy(id = "ipArea")
	private MobileElement txtipArea;
	
	@AndroidFindBy(id = "changeAPIButton")
	private MobileElement btnChangeAPIButton;

	// Constructor
	public LoginPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	/**
	 * Function to select sites
	 * @author DatLe
	 */
	public void setSite(String ipServer, String ipArea) {
		setNewTimeOut(10);
		try {
		clearField(txtIPServer);
		txtIPServer.setValue(ipServer);
		driver.hideKeyboard();
		clearField(txtIPArea);
		txtIPArea.setValue(ipArea);
		driver.hideKeyboard();
		btnChangeAPI.click();
		} catch (Exception e) {}
		resetTimeOut();
	} 
	
	//
	public void inputurl() {
		System.out.println("Input URL");
		txtipServer.setValue("http://113.20.115.84:9898/");
		driver.hideKeyboard();
		txtipArea.setValue("apiclayton/");
		driver.hideKeyboard();
		btnChangeAPIButton.click();
		
	}

	// Function to check if Driver ID field is blank
	public boolean checkDriverID() {
		return fieldIsBlank(txtDriverID);
	}

	// Function to set values in BusID Field
	public void inputBusID(String busID) {
		System.out.println("Input BusID: " + busID);
		txtBusID.setValue(busID);
		driver.hideKeyboard();
	}

	// Function to set values in DriverID Field
	public void inputDriverID(String driverID) {
		System.out.println("Input DriverID: " + driverID);
		txtDriverID.setValue(driverID);
		driver.hideKeyboard();
	}

	//Function to set values in Password Field
	public void inputPassword(String password) {
		System.out.println("Input Password: " + password);
		txtPassword.setValue(password);
		driver.hideKeyboard();
	}

	//Function to click Login button
	public PunchesPageObject clickLogin() {
		System.out.println("Click Login button");
		btnLogin.click();
		return new PunchesPageObject(driver);
	}

	/**
	 * Function to interact with "Remember me" check box
	 * @author datle
	 * @param status = true/false comparing with 'checked' value
	 */
	public void setRemember(String status) {
		String check = ckbRemember.getAttribute("checked");
		if(!check.equals(status)) {
			System.out.println("Uncheck Remember me");
			ckbRemember.click();
		} else {
			System.out.println("Check box Remember me already = " + check);
		}
	}

	// Function to log in by sites
	public PunchesPageObject loginAs(String site, int intDriver) throws Exception {
		String busID = null;
		String driverID = null;
		String passWord = null;

		// Click "Retry" if that btn displayed.
		setNewTimeOut(3);
		try {
			btnRetry.click();
		} catch(Exception e) {
			System.out.println("No 'Retry' Button!");
		}
		resetTimeOut();

		wait.until(ExpectedConditions.visibilityOf(txtBusID));
		clearAllFields();

		// Working with Excel data
		ExcelUtils.setExcelFile(System.getProperty("user.dir") + "/" + Constant.File_UserLogin, site);
		busID = ExcelUtils.getCellData(intDriver, 0);
		driverID = ExcelUtils.getCellData(intDriver, 1);
		passWord = ExcelUtils.getCellData(intDriver, 2);

		if(txtBusID.isEnabled()) {
			inputBusID(busID);
		}
		inputDriverID(driverID);
		inputPassword(passWord);

		return clickLogin();
	}

	// Function to set values and click login button
	public PunchesPageObject loginAs(String busID, String driverID, String pwd) {
		// Click "Retry" if that button displayed.
		setNewTimeOut(3);
		try {
			btnRetry.click();
		} catch(Exception e) {
			System.out.println("No 'Retry' Button!");
		}
		resetTimeOut();

		wait.until(ExpectedConditions.visibilityOf(txtBusID));
		clearAllFields();
		inputBusID(busID);
		inputDriverID(driverID);
		inputPassword(pwd);
		return clickLogin();
	}

	// Function to get Message Error
	public String getError() {
		wait.until(ExpectedConditions.visibilityOf(txtError));
		return txtError.getText().toString();
	}

	// Function to clear BusID, DriverID, Password Fields
	public void clearAllFields() {
		clearBusID();
		clearDriverID();
		clearPW();
	}

	// Function to clear BusID Field
	public void clearBusID() {
		clearField(txtBusID);
	}

	// Function to clear DriverID Field
	public void clearDriverID() {
		clearField(txtDriverID);
	}

	// Function to clear Password Field
	public void clearPW() {
		txtPassword.clear();
		driver.hideKeyboard();
	}
}
