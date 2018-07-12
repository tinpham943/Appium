package com.tcvn.driverhelp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverHelp {
	static InputStream input;
	static Properties prop = new Properties();

	//Init Android Driver, set Capabilities
	private static AndroidDriver<MobileElement> initAndroidDriver() throws IOException {
		try {
			input = new FileInputStream("config.properties");
			//Load a properties file
			prop.load(input);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			input.close();
		}

			File app = new File(prop.getProperty("filePath"));
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Constant.AndroidOS);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "TSEA74G00095");
			caps.setCapability(MobileCapabilityType.BROWSER_NAME, "");
			caps.setCapability(MobileCapabilityType.VERSION, Constant.AndroidVer_50);
			caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

			caps.setCapability(MobileCapabilityType.FULL_RESET, true);
			caps.setCapability("clearSystemFiles", true);
			caps.setCapability("locationServicesAuthorized", "true");
			caps.setCapability("autoGrantPermissions", "true");
			caps.setCapability("autoAcceptAlerts", "true");
			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 700);
	
			AndroidDriver<MobileElement> driver = new AndroidDriver<MobileElement>(
					new URL("http://"+prop.getProperty("ip")+":"+prop.getProperty("port")+"/wd/hub"), caps);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		return driver;
	}

	/** Init Android driver, No Installation
	 * @author datle
	 */
	private static AndroidDriver<MobileElement> initAndroidDriverNoInstall() throws IOException {
		try {
			input = new FileInputStream("config.properties");
			//Load a properties file
			prop.load(input);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			input.close();
		}
		
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Constant.AndroidOS);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "TSEA74G00095");
			caps.setCapability(MobileCapabilityType.UDID, prop.getProperty("udid"));
			caps.setCapability(MobileCapabilityType.VERSION, prop.getProperty("ver"));
			
			caps.setCapability("appPackage", Constant.DP_AppPackage);
			caps.setCapability("appActivity", Constant.DP_AppActivity);
			caps.setCapability(MobileCapabilityType.NO_RESET, true);

			caps.setCapability("locationServicesAuthorized", "true");
			caps.setCapability("autoGrantPermissions", "true");
			caps.setCapability("autoAcceptAlerts", "true");
			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 70000);
	
			AndroidDriver<MobileElement> driver = new AndroidDriver<MobileElement>(
					new URL("http://"+prop.getProperty("ip")+":"+prop.getProperty("port")+"/wd/hub"), caps);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		return driver;
	}

	//get WebDriver, set default is Android
	public static AndroidDriver<MobileElement> getDriver(String name) throws IOException {
		switch(name) {
			case Constant.AndroidOS_NoInstall:
				return DriverHelp.initAndroidDriverNoInstall();
			default:
				return DriverHelp.initAndroidDriver(); // Default is site Clayton
		}
	}
}