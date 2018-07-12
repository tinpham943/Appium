package com.tcvn.testcase.bug.clayton;

import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.tcvn.core.CoreTestTemplate;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.DrivingPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class NUS220 extends CoreTestTemplate {

	public String createRandomIdByDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmmss");
		return sdf.format(date);
	}

	public static String getDayBefore() {
		Calendar cal = Calendar.getInstance();
		String[] time = cal.getTime().toString().substring(11, 19).split(":");
		int HH = Integer.parseInt(time[0].toString());
		int MM = Integer.parseInt(time[1].toString());
		if (HH > 11 && MM > 1) {
			cal.add(Calendar.DATE, -0);
		} else {
			cal.add(Calendar.DATE, -1);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String getDevicetimebefore30s() throws ParseException {
		String time = driver.getDeviceTime().substring(11, 19);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = sdf.parse(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, +1);
		cal.add(Calendar.SECOND, -30);
		date = cal.getTime();
		String sTime = date.toString().substring(11, 19);
		return sTime;

	}

	public static String getDevicetime() throws ParseException {
		String time = driver.getDeviceTime().substring(11, 19);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = sdf.parse(time);
		Calendar cal = Calendar.getInstance();
		cal.getTime().toString();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, +1);
		cal.add(Calendar.SECOND, -00);
		date = cal.getTime();
		String sTime = date.toString().substring(11, 19);
		return sTime;

	}

	public String GetValueFromDtbs(String eventname, String tagertvalue) throws Exception {
		String output = null;
		URL url = null;
		try {
			if (eventname.equalsIgnoreCase("getstudentevent")) {
				url = new URL("http://172.16.0.58:8282/api/mam/" + eventname + "/" + BusId + "/110/" + getDayBefore()
						+ "_" + getDevicetime());
				System.out.println("getDevicetime: " + getDevicetime());
			}
			if (eventname.equalsIgnoreCase("getstopevent")) {
				url = new URL("http://172.16.0.58:8282/api/mam/" + eventname + "/" + BusId + "/106/" + getDayBefore()
						+ "_" + getDevicetimebefore30s());
				System.out.println("getDevicetimebefore30s: " + getDevicetimebefore30s());
			}

			// System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				ObjectMapper mapper = new ObjectMapper();
				int c = output.indexOf("]");
				String json = output.substring(1, c);
				// System.out.println("Json data on server is: " + json);
				Map<String, Object> map = new HashMap<String, Object>();
				// convert JSON string to Map
				try {
					map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
					});
					if (map == null) {
						throw new Exception();
					}
				} catch (MismatchedInputException e) {
					e.printStackTrace();

					System.out.println("Failed to get value from mapper");

				}
				System.out.println("Write Successfully " + tagertvalue + " : " + (String) map.get(tagertvalue));
				return (String) map.get(tagertvalue);

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return output;

	}

	public String CountValueFromDtb(String eventnumber, String timebegin, String timeend) {
		String output = null;
		try {

			// URL url = new URL("http://172.16.0.33:8089/api/mam/" + Path);
			URL url = new URL("http://172.16.0.58:8282/api/mam/getnumberevent/" + BusId + "/" + eventnumber + "/"
					+ getDayBefore() + "_" + timebegin + "/" + getDayBefore() + "_" + timeend);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			System.out.print(url+" _ ");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				return (String) output;

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return output;

	}

	String BusId = "Bus" + createRandomIdByDate();

	@BeforeClass
	public void before() {
		// Login to DP
		loginPage = new LoginPageObject(driver);
		// loginPage.inputurl();
		punchesPage = loginPage.loginAs(BusId, "0007", "9999");
		// Setting time

	}

	@Test(groups = "BugDatabase", priority = 10)

	public void testDTB() throws InterruptedException, AWTException, JsonParseException, JsonMappingException,
			IOException, ParseException {
		setTime(8, 5, "AM");
		setAirPlane("ON");
		System.out.println("Start Testdtb");
		// Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		DrivingPageObject previewRoute = selectRoutePage.selectRouteByID("717");
		System.out.println("Show alert message: " + previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		previewRoute.clickStartRoute();
		System.out.println("Show alert message: " + previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		// Driving mode
		stopPage = new StopPageObject(driver);
		// Set file .gpx
		setFileMock("717-001.002-KV0123-Final.gpx", "1000");
		// Set Timeout to 3000
		setNewTimeOut(3000);
		stopPage.waitStopView();
		stopPage.closeStopView();
	
		stopPage.waitStopView();
		stopPage.closeStopView();
	
		stopPage.waitStopView();
		stopPage.closeStopView();
		stopPage.closeNextRunPopup();
		setAirPlane("OFF");
		Thread.sleep(15000);
		System.out.println("NO_MOTION_BEGIN: " + CountValueFromDtb("104", "09:04:00", "09:28:00"));
		System.out.println("NO_MOTION_END: " + CountValueFromDtb("105","09:04:00", "09:28:00"));
		System.out.println("EXCESSIVE_IDLE_BEGIN: " + CountValueFromDtb("108","09:04:00", "09:28:00"));
		System.out.println("EXCESSIVE_IDLE_END: " + CountValueFromDtb("109","09:04:00", "09:28:00"));
	
		assertTrue(CountValueFromDtb("104", "09:04:00", "09:28:00").equalsIgnoreCase(CountValueFromDtb("105","09:04:00", "09:28:00"))&&
				CountValueFromDtb("108", "09:04:00", "09:28:00").equalsIgnoreCase(CountValueFromDtb("109","09:04:00", "09:28:00")));
	
	}
}