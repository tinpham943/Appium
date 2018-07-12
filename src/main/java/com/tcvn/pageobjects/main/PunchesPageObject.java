package com.tcvn.pageobjects.main;

import org.openqa.selenium.By;
import com.tcvn.pageobjects.MainPageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PunchesPageObject extends MainPageObject {

	@AndroidFindBy(id = "toolbarCenterTitle")
	private MobileElement txt_Title;

	@AndroidFindBy(id = "btnPunchIn")
	private MobileElement btn_PunchIn;

	@AndroidFindBy(id = "btnPunchOut")
	private MobileElement btn_PunchOut;

	// Constructor
	public PunchesPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	// Function to get title of PunchesPage
	public String getTitle() {
		return txt_Title.getText().toString();
	}

	// Function to click Punch in
	public void clickPunchIn() {
		btn_PunchIn.click();
	}

	// Function to click Punch out
	public void clickPunchOut() {
		btn_PunchOut.click();
	}

	/**
	 * Get value displayed on combo box or grid
	 * @author datle
	 */
	public String getValueDisplayed(String name, int position) {
		String check = driver.findElements(By.id(name)).get(position).getText();
		check.trim();
		return check;
	}

	/**
	 * Selecting options of the combo box
	 * @author datle
	 */
	public void switchPunchInAsComboBox() throws InterruptedException {
		driver.findElements(By.id("title")).get(0).click();
		Thread.sleep(500);
		driver.findElements(By.id("title")).get(2).click();
	}

	/**Selecting options of Performing Task combo box
	 * @author datle
	 */
	public void switchPerTaskComboBox() throws InterruptedException {
		driver.findElements(By.id("title")).get(1).click();
		Thread.sleep(500);
		driver.findElements(By.id("title")).get(2).click();		
	}
}
