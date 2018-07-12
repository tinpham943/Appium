package com.tcvn.pageobjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;

import com.tcvn.driverhelp.Constant;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class SettingPageObject extends BasePageObject {
	String version = driver.getCapabilities().getVersion();

	// Constructor
	public SettingPageObject(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	/**
	 * Turn 24-hour option ON/OFF
	 * 
	 * @author datle
	 */
	public void switch24HourFormat(String status) {
		MobileElement hourFormatOpt;
		MobileElement hourFormatOptStatus;
		String check;
		String convertStt = "false";
		if(status.equals("ON"))
			convertStt = "true";
		if (version.equals(Constant.AndroidVer_51)) {
			hourFormatOpt = driver.findElement(By.xpath("//*[@class='android.widget.LinearLayout' and @index='5']"));
			hourFormatOptStatus = hourFormatOpt.findElement(By.id("switchWidget"));
			check = hourFormatOptStatus.getText();
			if(!check.equals(status)) {
				hourFormatOptStatus.click();
			}
		} else {
			hourFormatOpt = driver.findElement(By.xpath("//*[@class='android.widget.LinearLayout' and @index='4']"));
			hourFormatOptStatus = hourFormatOpt.findElement(By.id("checkbox"));
			check = hourFormatOptStatus.getAttribute("checked");
			if(!check.equals(convertStt)) {
				hourFormatOptStatus.click();
			}
		}
	}
	public void switch24HourFormatcalampOFF(String status) {

		MobileElement hourFormatOpt = driver
				.findElement(By.xpath("//*[@class='android.widget.LinearLayout' and @index='4']"));
		MobileElement hourFormatOptStatus = hourFormatOpt.findElement(By.id("checkbox"));
		String check = hourFormatOptStatus.getAttribute("checked");
		System.out.println(check);
		if (check.equals(status)) {
			hourFormatOptStatus.click();
		}
	}

	/**
	 * Function to set GMT
	 * 
	 * @author DatLe
	 */
	public void setGMT(String gmt) {
		MobileElement gmtOpt = driver
				.findElement(By.xpath("//android.widget.TextView[contains(@text,'Select time zone')]"));
		if (!gmtOpt.isEnabled()) {
			driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Automatic time zone')]")).click();
		}
		gmtOpt.click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/list\"))"
				+ ".scrollIntoView(new UiSelector().textContains(\"" + gmt + "\"))").click();
	}

	// Function to click Wi-Fi
	public void clickWifi() {
		System.out.println("Click Wifi");
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.android.settings:id/main_content\"))"
						+ ".scrollIntoView(new UiSelector().textContains(\"Fi\"))")
				.click();
	}

	// Function to switch on/off wifi
	public void switchWifi() {
		driver.findElement(By.id("com.android.settings:id/switch_widget")).click();
	}

	// Function to get Wifi status
	public String getWifi() {
		return driver.findElement(By.id("com.android.settings:id/switch_widget")).getText();
	}

	// Function to click More
	public void clickMore() {
		System.out.println("Click More");
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.android.settings:id/main_content\"))"
						+ ".scrollIntoView(new UiSelector().textContains(\"More\"))")
				.click();
	}

	// Function to switch on/off AirPlane mode
	public void switchAirPlane() {
		driver.findElement(By.id("android:id/switchWidget")).click();
	}

	// Function to get Airplane status
	public String getAirplane() {
		return driver.findElement(By.id("android:id/switchWidget")).getText();
	}

	// Function to click Date & Time
	public void clickDateAndTime() {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.android.settings:id/main_content\"))"
						+ ".scrollIntoView(new UiSelector().textContains(\"Date & time\"))")
				.click();
	}

	// Function to click Set time
	public void clickSetTime() {
		MobileElement setTimeOpt = driver
				.findElement(By.xpath("//android.widget.TextView[contains(@text,'Set time')]"));
		if (!setTimeOpt.isEnabled()) {
			driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Automatic date & time')]")).click();
		}
		setTimeOpt.click();
	}

	/**
	 * Clicking 'Set date' option
	 * 
	 * @author datle
	 */
	public void clickSetDate() {
		System.out.println("Click Set date");
		driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Set date')]")).click();
	}

	/**
	 * Setting to the next date
	 * 
	 * @author datle
	 * @throws ParseException
	 */
	public void setToNextDate() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		String setDate = dateFormat.format(date);
		if (setDate.startsWith("0")) {
			setDate = setDate.substring(setDate.length() - 1);
		}
		System.out.println("Current date: " + setDate);
		driver.findElement(By.xpath("//*[@class='android.view.View' and @index='" + setDate + "']")).click();
		driver.findElement(By.id("android:id/button1")).click();
	}

	// Function to set new system time
	public void setTimeByData(int strHour, int strMinute) {
		/**
		 * Because displayed element of min is 0, 5, 10, 15, ... so we reset the value
		 * around 30 min (+/- 30 min as Driver Portal rule)
		 */
		if (strMinute < 30) {
			strMinute = 0;
		} else {
			strMinute = 30;
		}
		MobileElement hour = driver
				.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/hours')]"));
		hour.click();
		System.out.println("Set hour: " + strHour);
		driver.findElement(
				By.xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
						+ strHour + "']"))
				.click();
		System.out.println("Set minute: " + strMinute);
		driver.findElement(
				By.xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
						+ strMinute + "']"))
				.click();
		System.out.println("Click OK");
		driver.findElement(By.id("android:id/button1")).click();
	}

	// Function to set new system time
	public void setTime(int strHour, int strMinute) {
		MobileElement hour = driver
				.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/hours')]"));
		hour.click();
		driver.findElement(
				By.xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
						+ strHour + "']"))
				.click();
		driver.findElement(
				By.xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
						+ strMinute + "']"))
				.click();
		driver.findElement(By.id("android:id/button1")).click();
	}

	// Function to set new system time
	public void setTime(int strHour, int strMinute, String period) {
		MobileElement hour = driver
				.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/hours')]"));
		hour.click();
		System.out.println("Set hour: " + strHour);
		driver.findElement(
				By.xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
						+ strHour + "']"))
				.click();
		System.out.println("Set minute: " + strMinute);
		driver.findElement(
				By.xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
						+ strMinute + "']"))
				.click();
		System.out.println("Set AM/PM: " + period);
		driver.findElement(By.xpath("//*[@class='android.widget.CheckedTextView' and @text='" + period + "']")).click();
		System.out.println("Click OK");
		driver.findElement(By.id("android:id/button1")).click();
	}

	public void setTimecalamp(String strHour, String strMinute, String period) {
		driver.findElementByAccessibilityId(strHour).click();
		driver.findElementByAccessibilityId(strMinute).click();
		/*
		 * MobileElement hour = driver.findElement(By.xpath(
		 * "//android.widget.TextView[contains(@resource-id,'android:id/hours')]"));
		 * hour.click(); System.out.println("Set hour: "+strHour);
		 * driver.findElement(By.
		 * xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
		 * +strHour+"']")).click(); System.out.println("Set minute: "+strMinute);
		 * driver.findElement(By.
		 * xpath("//*[@class='android.widget.RadialTimePickerView$RadialPickerTouchHelper' and @content-desc='"
		 * +strMinute+"']")).click();
		 */
		System.out.println("Set AM/PM: " + period);

		while (driver.findElement(By.id("ampm_label")).getText().equalsIgnoreCase(period)) {

			driver.findElement(By.id("ampm_label")).click();
		}

		driver.findElement(By.id("ampm_label")).click();
		System.out.println("Click OK");
		driver.findElement(By.id("android:id/button1")).click();
	}
}