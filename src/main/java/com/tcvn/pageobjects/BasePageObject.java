package com.tcvn.pageobjects;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class BasePageObject {

	//Declare AndroidDriver
	protected final AndroidDriver<MobileElement> driver;
	protected TouchAction touchAct;
	protected WebDriverWait wait;

	//Constructor
	public BasePageObject (AndroidDriver<MobileElement> driver) {
		this.driver = driver;
		this.touchAct = new TouchAction(driver);
		this.wait = new WebDriverWait(driver, 300);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	//Function to clear text field
	public void clearField(MobileElement element) {
		if (!element.getText().isEmpty()) {
			element.clear();
			try {
				driver.hideKeyboard();
			} catch(Exception e) {}
		}
	}

	//Click and wait element
	public void clickElement(MobileElement element, long time) {
		element.click();
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Setting new timeout
	protected void setNewTimeOut(long timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	//Reset timeout to 80
	protected void resetTimeOut() {
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}

	//Check empty or blank field
	protected boolean fieldIsBlank(MobileElement element) {
		if (element.getText().length() == 0) {
			return true;
		} else {
			return false;
		}
	}
}