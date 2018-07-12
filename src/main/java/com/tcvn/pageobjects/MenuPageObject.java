package com.tcvn.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.tcvn.driverhelp.Constant;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MenuPageObject extends BasePageObject {
	@AndroidFindBy(id = "alertOkButton")
	private MobileElement btnAlertOK;

	// Constructor 
	public MenuPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	/**
	 * Function to click on specific option on Menu bar
	 * @author datle
	 */
	public void clickMenuOpt(String opt) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("android.widget.ImageButton")))).click();
		Thread.sleep(1000);
		MobileElement select = driver.findElement(
				By.xpath("//*[@resource-id='"+Constant.DP_AppPackage+":id/menuItem' and @text='"+opt+"']"));
		select.click();
		setNewTimeOut(3);
		if (!opt.equals("Switch Session Owner")) {
			try {
				btnAlertOK.click();
			} catch(Exception e) {}
		}
		resetTimeOut();
	}
}
