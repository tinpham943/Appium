package com.tcvn.testcase.login;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.tcvn.core.CoreTestTemplate;
import com.tcvn.driverhelp.Constant;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.PunchesPageObject;

public class LoginTest extends CoreTestTemplate {

	@BeforeMethod
	public void startApp() {
		loginPage = new LoginPageObject(driver);
		loginPage.clearAllFields();
	}

	@Test(groups = "Login_Test", priority = 10)
	public void testLoginPassed() {
		try {
		System.out.println("Start testcase login_5");
		loginPage.inputBusID("12345");
		loginPage.inputDriverID("022900");
		loginPage.inputPassword("9999");
		PunchesPageObject punches = loginPage.clickLogin();
		assertEquals("PUNCHES", punches.getTitle());
		System.out.println("Stop testcase login_5");
		} catch (NoSuchElementException ex) {
			Assert.fail();
		}
	}

	@Test(groups = "Login_Test", priority = 1)
	public void testLoginFailed_3FieldsAreEmpty() {
		System.out.println("Start testcase login_1");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_3FieldsEmpty);
		System.out.println("Stop testcase login_1");
	}

	@Test(groups = "Login_Test", priority = 2)
	public void testLoginFailed_2FieldsAreEmpty() {
		System.out.println("Start testcase login_2");
		loginPage.inputBusID("12345");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_DriverID_Password_Empty);
		loginPage.clearAllFields();
		loginPage.inputDriverID("035300");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_BusID_Password_Empty);
		loginPage.clearAllFields();
		loginPage.inputPassword("9999");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_BusID_DriverID_Empty);
		System.out.println("Stop testcase login_2");
	}

	@Test(groups = "Login_Test", priority = 3)
	public void testLoginFailed_1FieldIsEmpty() {
		System.out.println("Start testcase login_3");
		loginPage.inputDriverID("035300");
		loginPage.inputPassword("9999");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_BusID_Empty);
		loginPage.clearAllFields();
		loginPage.inputBusID("12345");
		loginPage.inputPassword("9999");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_DriverID_Empty);
		loginPage.clearAllFields();
		loginPage.inputBusID("12345");
		loginPage.inputDriverID("035300");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_Password_Empty);
		System.out.println("Stop testcase login_3");
	}

	@Test(groups = "Login_Test", priority = 4)
	public void testLoginFailed_WrongPass() {
		System.out.println("Start testcase login_4");
		loginPage.inputBusID("12345");
		loginPage.inputDriverID("035300");
		loginPage.inputPassword("abc12345");
		loginPage.clickLogin();
		assertEquals(loginPage.getError(), Constant.LoginErr_WrongPassword);
		System.out.println("Stop testcase login_4");
	}
}
