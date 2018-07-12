package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.AddEmployeePageObject;


public class Bug_35 extends CoreTestTemplate {

	@BeforeClass
	public void before() {
		// Login to DP
		loginPage = new LoginPageObject(driver);
		// loginPage.inputurl();
		punchesPage = loginPage.loginAs("123", "0007", "9999");
		// Setting time	
	}

	@Test(groups = "BUG_35", priority = 10)
	public void testDTB() throws InterruptedException, AWTException {
		System.out.println("Start Testdtb");
		// Change to Select Route screen
		menuPage.clickMenuOpt("Additional Employee");
		AddEmployeePageObject AEPage = new AddEmployeePageObject(driver);
		AEPage.inputEmployeeID("037505");
		AEPage.inputPassword("9999");
		AEPage.clickGo();
		String name1  = AEPage.getDriverName();
		AEPage.clickPunchin();
		AEPage.inputEmployeeID("037505");
		AEPage.inputPassword("9999");
		AEPage.clickGo();
		String name2  = AEPage.getDriverName();
		AEPage.clickPunchout();
		assertTrue(name1.equalsIgnoreCase(name2));
	}
}