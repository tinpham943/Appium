package com.tcvn.testcase.bug;

import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
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
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.tcvn.core.CoreTestTemplate;
import com.tcvn.dataObject.SqlRouteObject;
import com.tcvn.pageobjects.LoginPageObject;
import com.tcvn.pageobjects.main.DrivingPageObject;
import com.tcvn.pageobjects.main.SelectRoutePageObject;
import com.tcvn.pageobjects.main.StopPageObject;

public class TestDatabase2 extends CoreTestTemplate {

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

	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
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

			URL url = new URL("http://172.16.0.58:8282/api/mam/getnumberevent/" + BusId + "/" + eventnumber + "/"
					+ getDayBefore() + "_" + timebegin + "/" + getDayBefore() + "_" + timeend);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

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
	}

	@SuppressWarnings("deprecation")
	@Test(groups = "BugDatabase", priority = 10)

	public void testDTB()

			throws InterruptedException, AWTException, JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(
				VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		List<SqlRouteObject> stopjson = mapper.readValue(new File("D:\\file.json"),
				new TypeReference<List<SqlRouteObject>>() {
				});

		setTimeByCmd1(getDate(), stopjson.get(0).getStops().get(0).getScheduledArrival().toString());
		System.out.println(stopjson.get(0).getStops().get(0).getScheduledArrival().toString());
		// setTime(stopjson.getStartHour(), stopjson.getStartMinute(),
		// stopjson.getPeriod());
		System.out.println("Start Testdtb");
		// Change to Select Route screen
		menuPage.clickMenuOpt("Select Route");
		selectRoutePage = new SelectRoutePageObject(driver);
		DrivingPageObject previewRoute = selectRoutePage.selectRouteByID(stopjson.get(0).getRouteNumber().toString());
		System.out.println("Show alert message: " + previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		previewRoute.clickStartRoute();
		System.out.println("Show alert message: " + previewRoute.getAlertMsg());
		previewRoute.clickAlertOK();
		// Driving mode
		stopPage = new StopPageObject(driver);
		// Set file .gpx
		setFileMock("New 180L.gpx", "5000");
		// Set Timeout to 3000
		setNewTimeOut(3000);
		int j;
		for (j = 0; j < stopjson.get(0).getStops().size(); j++) {
			// System.out.println("Stop seq :
			// "+stopjson.get(0).getStops().get(j).getStopSeq());
			stopPage.waitStopView();
			try {
				 assertTrue(GetValueFromDtbs("getstopevent",
				 "eventName").equalsIgnoreCase("STOP_BEGIN"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (stopPage.getStopnumber().toString().equalsIgnoreCase(stopjson.get(0).getStops().get(j).getStopSeq())) {
				for (int j2 = 0; j2 < stopjson.get(0).getStops().get(j).getStudent().size(); j2++) {
					 //System.out.println("Student of stop : "+stopjson.get(0).getStops().get(j).getStudent().get(j2).getStudentID());
					
					do {
						
						stopPage.selectStudent(stopjson.get(0).getStops().get(j).getStudent().get(j2).getFirstName());
						stopPage.getStudentInfo();
						stopPage.boardStudent();

						try {
							 assertTrue(GetValueFromDtbs("getstudentevent",
							 "eventName").equalsIgnoreCase("STUDENT_BOARD"));

						} catch (Exception e) {

							e.printStackTrace();
						}

					} while (j2 == stopjson.get(0).getStops().get(j).getStudent().size()
							&& j2 < stopjson.get(0).getStops().get(j).getStudent().size());
				
				}
				
			} else {
				j = j - 1;
			}
			stopPage.closeStopView();
		}
		
		System.out.println("number of stopBegin: " + CountValueFromDtb("106", "08:46:14", "09:05:55").toString());
		System.out.println("number of stopEnd: " + CountValueFromDtb("107", "08:46:14", "09:05:55").toString());

	}
}