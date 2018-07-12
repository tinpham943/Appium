package com.tcvn.pageobjects.main;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.tcvn.driverhelp.Constant;
import com.tcvn.pageobjects.MainPageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SwitchSessionPageObject extends MainPageObject {
	//=====Alert=====
	@AndroidFindBy(id = "alertContent")
	private MobileElement txtAlertMsg;

	@AndroidFindBy(id = "alertOkButton")
	private MobileElement btnAlertOK;

	@AndroidFindBy(id = "alertCancelButton")
	private MobileElement btnAlertCancel;

	//=====Login Screen=====
	@AndroidFindBy(id = "busIDText")
	private MobileElement txtBusID;

	@AndroidFindBy(id = "driverIDText")
	private MobileElement txtDriverID;

	@AndroidFindBy(id = "passwordText")
	private MobileElement txtPassword;

	@AndroidFindBy(id = "loginButton")
	private MobileElement btnLogin;

	//=====Preview Route=====
	@AndroidFindBy(id = "startRouteButton")
	private MobileElement btnStartRoute;

	//=====Menu Bar=====
	@AndroidFindBy(id = "dummyFieldForSwipe")
	private MobileElement btn_Menu;

	//=====Punching Screen=====
	@AndroidFindBy(id = "btnPunchIn")
	private MobileElement btnPunchIn;

	@AndroidFindBy(id = "userValue")
	private MobileElement txtDriverName;

	//=====Select Route=====
	@AndroidFindBy(id = "searchRouteIdField")
	private MobileElement fld_search;

	@AndroidFindBy(id = "searchIcon")
	private MobileElement btn_search;

	// Constructor
	public SwitchSessionPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	// Function to get Message
	public String getAlertMsg() {
		return txtAlertMsg.getText().toString();
	}

	// Function to click OK/Yes button on Alert
	public void clickAlertOK() {
		System.out.println("Click "+btnAlertOK.getText());
		btnAlertOK.click();
	}

	// Function to click Cancel/No button on Alert
	public void clickAlertNo() {
		System.out.println("Click "+btnAlertCancel.getText());
		btnAlertCancel.click();
	}

	/**
	 * Function to click on specific option on Menu bar
	 * @author datle
	 */
	public void clickMenuOpt(String opt) throws InterruptedException {
		System.out.println("Choose " + opt);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("dummyFieldForSwipe")))).click();
		Thread.sleep(500);
		MobileElement optToClick = driver.findElement(
				By.xpath("//*[@resource-id='com.edulog.driverportal:id/menuItem' and @text='"+opt+"']"));
		optToClick.click();
	}

	// Function to select Route by ID
	public void selectRouteByID(String ID) {
		wait.until(ExpectedConditions.visibilityOf(fld_search));
		fld_search.setValue(ID);
		btn_search.click();
		MobileElement route = driver.findElement(By.xpath(
				"//android.widget.TextView[@resource-id=\"com.edulog.driverportal:id/idTextView\""
				+ "and contains(@text,'"+ID+"')]"));
		route.click();
	}

	// Function to set values in DriverID Field
	public void inputDriverID(String driverID) {
		System.out.println("Input DriverID: " + driverID);
		txtDriverID.setValue(driverID);
		driver.hideKeyboard();
	}

	// Function to set values in Password Field
	public void inputPassword(String password) {
		System.out.println("Input Password: " + password);
		txtPassword.setValue(password);
		driver.hideKeyboard();
	}

	//Function to click Login button
	public void clickLogin() {
		System.out.println("Click Login button");
		btnLogin.click();
	}

	/** 
	 * @author datle
	 */
	public boolean SSO_DiffUserRelogin_LoadLastSession() {
		boolean check = false;
		
		try {
			String txtCurrentDriver = txtDriverName.getText().trim();
			
			this.clickMenuOpt("Select Route");
			this.selectRouteByID("03");
			this.clickAlertOK();
			this.clickMenuOpt("Switch Session Owner");
			this.clickAlertOK();
			txtDriverID.clear();
			this.inputDriverID("037506");
			this.inputPassword("9999");
			this.clickLogin();
			this.clickAlertOK();
			
			if(btnStartRoute.isDisplayed()) {
				check = true;
			}
			
			this.clickMenuOpt("Time & Attendance");
			String txtReloginDriver = txtDriverName.getText().trim();
			
			System.out.println("Current driver name: " + txtCurrentDriver);
			System.out.println("Re-login driver name: " + txtReloginDriver);
			
			if(check == true && (!txtCurrentDriver.equals(txtReloginDriver))) {
				check = true;
			} else {
				check = false;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	/** 
	 * @author datle
	 */
	public boolean SSO_DiffUserRelogin_NotLoadLastSession() {
		boolean check = false;
		
		try {
			this.clickAlertNo();
			btnPunchIn.click();
			List<MobileElement> grid = driver.findElements(By.id("punchInAs"));
			
			// If the grid shows 1 row of punching in record -> true
			// condition == 2 due to the lable ID has the same ID="punchInAs" as records ID
			if(grid.size() == 2) {
				check = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	/** 
	 * @author datle
	 */
	public boolean SSO_SameUserRelogin_LoadLastSession() {
		boolean check = false;
		
		try {
			btnAlertOK.click();
			txtPassword.sendKeys("9999");
			btnLogin.click();
			btnAlertOK.click();
			
			if(fld_search.isEnabled()) {
				check = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}

	/** Checking the behaviors after selecting SSO opt. then re-login by the same driver
	 * Behaviors after selecting SSO: 
	 * 		(1) Alert displaying -> to login screen + busID field is not editable
	 * 		(2) Re-login by the same driver: alert displaying -> click No 
	 * @author datle
	 */
	public boolean SSO_SameUserRelogin_NotLoadLastSession() {
		boolean check = false;
		String alertSSO = this.getAlertMsg();
		
		try {
			if(alertSSO.equals(Constant.SSOalert_Selecting)) {
				check = true;
			} else {
				System.out.println("Actual SSO msg: " + alertSSO);
			}
			btnAlertOK.click();
			
			if(check == true && (!txtBusID.isEnabled())) {
				check = true;
			} else if(txtBusID.isEnabled()) {
				System.out.println("BusID field can be edited!");
				check = false;
			}
			
			txtPassword.sendKeys("9999");
			btnLogin.click();
			
			String alertLoadSession = this.getAlertMsg();
			if(check == true && alertLoadSession.equals(Constant.SSOalert_Relogin)) {
				check = true;
			} else if(!alertLoadSession.equals(Constant.SSOalert_Relogin)) {
				System.out.println("Actual SSO loading last session msg: " + alertLoadSession);
				check = false;
			}

			// Choose "No" to not load the last session -> eDTA must be loaded as default
			btnAlertCancel.click();

			if(check == true && btnPunchIn.isEnabled()) {
				check = true;
			} else {
				check = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
}