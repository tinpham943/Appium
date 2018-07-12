package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;

import java.text.ParseException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.DrivingPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;

/**
 * @author datle
 */
public class DP04 extends CoreTestTemplate {

	@BeforeClass
	public void setUp() throws Exception {
		//Login to DP
		loginPage = new LoginPageObject(driver);
		punchesPage = loginPage.loginAs(strSite, 1);
	}

	@Test
	public void test1RecentRoute_Reinstall() throws InterruptedException {
		boolean check = false;
		//Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		check = selectRoutePage.checkRecentRouteEmpty();
		try {
			assertTrue(check);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(check);
		}
	}

	@Test
	public void test2RecentRoute_Started() throws InterruptedException {
		boolean check = false;
		String inputRoute1 = "717";
		String inputRoute2 = "180L";
		//start the 1st route
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		DrivingPageObject previewRoute = selectRoutePage.selectRouteByID(inputRoute1);
		previewRoute.clickAlertOK();
		previewRoute.clickStartRoute();
		previewRoute.clickAlertOK();
		//start the 2nd route 
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		DrivingPageObject previewRoute2 = selectRoutePage.selectRouteByID(inputRoute2);
		previewRoute2.clickAlertOK();
		previewRoute2.clickStartRoute();
		previewRoute2.clickAlertOK();
		//switch back to check the displayed data
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		check = selectRoutePage.checkHistoryRoute(inputRoute1, inputRoute2);
		try {
			assertTrue(check);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(check);
		}
	}

	@Test
	public void test3RecentRoute_NextDay() throws ParseException, InterruptedException {
		setNextDate();
		boolean check = false;
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		check = selectRoutePage.checkRecentRouteEmpty();
		try {
			assertTrue(check);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(check);
		}
	}
}