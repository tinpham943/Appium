package com.tcvn.pageobjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MainPageObject extends BasePageObject {

	@AndroidFindBy(id = "dummyFieldForSwipe")
	private MobileElement btn_Menu;

	// Constructor
	public MainPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	// Function to click Menu icon
	public void clickMenu() {
		System.out.println("Click Menu icon");
		wait.until(ExpectedConditions.elementToBeClickable(btn_Menu));
		clickElement(btn_Menu, 500);
	}

	// Function to type String by Robot
	public void sendkeys(Robot robot, String keys) {
		for (char c : keys.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (KeyEvent.CHAR_UNDEFINED == keyCode) {
				throw new RuntimeException(
					"Key code not found for character '" + c + "'");
			}
			robot.keyPress(keyCode);
			robot.delay(100);
		}
	}
}
