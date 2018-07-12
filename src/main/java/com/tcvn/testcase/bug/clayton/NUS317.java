package com.tcvn.testcase.bug.clayton;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;

public class NUS317 extends CoreTestTemplate {
	public String createRandomIdByDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmmss");
		return sdf.format(date);
	}
	@BeforeClass
	public void before() {
		// Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs("test", "0007", "9999");
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "BugDatabase", priority = 10)

	public void testDTB() throws InterruptedException, AWTException {
		
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		String idrun = createRandomIdByDate();
		String idroute = createRandomIdByDate();
		selectRoutePage.buildNewRun(idroute,idrun);
		assertTrue(true);
	
	}
}
