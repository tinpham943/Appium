package com.tcvn.testcase.bug;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.PunchesPageObject;

/**
 * @author datle
 */
public class DP01 extends CoreTestTemplate {

	private static LoginPageObject loginPage;

	@BeforeMethod
	public void startApp() {
		loginPage = new LoginPageObject(driver);
	}

	@Test
	public void Login_Pass() {
		try {
			loginPage.setSite(strIPServer, strIPArea);
			PunchesPageObject punches = loginPage.loginAs(strSite, 1);

			assertEquals("PUNCHES", punches.getTitle());
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}