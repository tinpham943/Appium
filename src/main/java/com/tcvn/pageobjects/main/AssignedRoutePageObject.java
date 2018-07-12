package com.tcvn.pageobjects.main;

import org.openqa.selenium.By;
import com.tcvn.pageobjects.MainPageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AssignedRoutePageObject extends MainPageObject {

	// Constructor
	public AssignedRoutePageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	// Check Assigned Route screen
	public boolean checkAssignedRoute(String busID) {
		boolean check = false;
		String txt = driver.findElement(By.id("idTextView")).getText();
		if(txt.equalsIgnoreCase("<"+busID+">")) {
			check = true;
		}
		return check;
	}
}
