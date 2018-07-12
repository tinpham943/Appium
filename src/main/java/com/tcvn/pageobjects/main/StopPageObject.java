package com.tcvn.pageobjects.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.tcvn.pageobjects.MainPageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class StopPageObject extends MainPageObject {

	@AndroidFindBy(id = "sideBarImageView")
	private MobileElement btnSideBar_StopList;

	//=====Stop information=====
	@AndroidFindBy(id = "toolbarStopName")
	private MobileElement txtStopName;

	@AndroidFindBy(id = "toolbarStopNameRegular")
	private MobileElement txtStopAddress;

	@AndroidFindBy(id = "toolbarScheduledTime")
	private MobileElement txtStopSchedule;

	@AndroidFindBy(id = "toolbarActualTime")
	private MobileElement txtStopActual;

	@AndroidFindBy(id = "toolbarCloseBtn")
	private MobileElement btnCloseStopView;

	@AndroidFindBy(id = "inputManuallyEditText")
	private MobileElement txtInputStudent;

	@AndroidFindBy(id = "submitManuallyBtn")
	private MobileElement btnInputStudent;

	@AndroidFindBy(id = "student_school_spinner")
	private MobileElement cbxSchool;

	//=====Menu Board/Disembark=====
	@AndroidFindBy(id = "boardContainer")
	private MobileElement menuBoard;

	@AndroidFindBy(id = "disembarkContainer")
	private MobileElement menuDisembark;

	@AndroidFindBy(id = "boardNumber")
	private MobileElement txtBoardNumber;

	@AndroidFindBy(id = "disembarkNumber")
	private MobileElement txtDisNumber;

	@AndroidFindBy(id = "unknownTextView")
	private MobileElement txtSumOfUnknownStudent;

	//=====Student List=====
	@AndroidFindBy(id = "studentViewContainer")
	private MobileElement scrStopView;

	@AndroidFindBy(id = "studentScrollviewContainer")
	private MobileElement scrStudentView;

	//=====Student Detail=====
	@AndroidFindBy(id = "studentDetailCloseBtn")
	private MobileElement btnCloseDetail;

	@AndroidFindBy(id = "studentName")
	private MobileElement txtStudentName;

	@AndroidFindBy(id = "studentDetailID")
	private MobileElement txtStudentID;

	@AndroidFindBy(id = "studentUpperStatus")
	private MobileElement txtStudentStatus;

	@AndroidFindBy(id = "studentDetailBoardedBtn")
	private MobileElement btnBoard;

	@AndroidFindBy(id = "dummyFieldForSwipe")
	private MobileElement dummyFieldForSwipe;

	@AndroidFindBy(id = "alertCancelButton")
	private MobileElement alertCancelButton;
	
	private Robot robot;

	//Constructor
	public StopPageObject(AndroidDriver<MobileElement> driver) throws AWTException {
		super(driver);
		robot = new Robot();
	}

	/** Function to compare the amount of boarded students to the number displayed on the stop view + stops list.
	 * @author datle
	 */
	public boolean compareStudentBoarded() {
		System.out.println("Compare Boared Student Amount!");
		String checkSumBoarded_Stop1 = null;
		String txtBoard_StopView = null;
		String schoolText = null;
		boolean check = false;
		int countKnown = 0;
		int countTotal = 0;

		try {
			// Click the "School" combo box then sum all the schools on it, except the 1st row "All".
			cbxSchool.click();
			Thread.sleep(1000);
			List<MobileElement> schoolList = driver.findElements(By.xpath("//*[@class='android.widget.LinearLayout']"));
			System.out.println("SUM OF SCHOOLS = " + (schoolList.size() - 1));
			
			// [Stop View] Sort students by school then select them 1-by-1 to check.
			for(int i=1; i < schoolList.size(); i++) {
				if(!driver.findElement(By.xpath("//*[@class='android.widget.ListView']")).isDisplayed()) {
					cbxSchool.click();
					Thread.sleep(1000);
				}
				schoolText = driver.findElements(By.id("text1")).get(i).getText();
								
				MobileElement school = driver.findElementByAndroidUIAutomator(
						"new UiScrollable(new UiSelector()"
						+ ".resourceId(\"text1\"))"
						+ ".scrollIntoView(new UiSelector().textContains(\""+schoolText+"\"))");
				school.click();
				
				System.out.println("Checking school: " + schoolText);
				
				List<MobileElement> students = scrStudentView.findElements(By.xpath("//*[contains(@text,'"+schoolText+"')]"));
				System.out.println("TOTAL STUDENTS OF SCHOOL " + schoolText + " = " + students.size());
				
				// Store the sum of boarded students on Stop view
				txtBoard_StopView = txtBoardNumber.getText();
				
				// If the btn of students info is "Disembarked", which means the students had already boarded.
				for(MobileElement select : students) {
					select.click();
					if(btnBoard.isDisplayed()) {
						if(btnBoard.getText().equalsIgnoreCase("disembarked")) {
							countKnown++;
						} else {
							System.out.println("Btn of student #" + txtStudentID.getText() + ": " + btnBoard.getText());
						}
						btnCloseDetail.click();
					}
				}
			}
			
			// Get the value of Unknown students counter
			String txtTotalUnknown = txtSumOfUnknownStudent.getText();
			int totalUnknown = Integer.parseInt(txtTotalUnknown);

			// Close Stop View if it is not auto-closed.
			if(btnCloseStopView.isEnabled()) {
				btnCloseStopView.click();
			}
			
			/** [Stop List] Get data displayed then compare with gotten data. */
			// Condition to click to show the stop list
			if(!driver.findElements(By.id("nameRouteTextView")).get(0).isDisplayed()) {
				btnSideBar_StopList.click(); 
			}
			// Condition to get value of Boarded/Disembarked if they appears besides the stops.
			if(driver.findElements(By.id("getInTextView")).get(0).isDisplayed()) {
				checkSumBoarded_Stop1 = driver.findElements(By.id("getInTextView")).get(0).getText();
			}
			
			System.out.println("[Stop View] Counter of boarded students (known): " + countKnown);
			System.out.println("[Stop View] Counter of boarded students (unknown): " + totalUnknown);
			System.out.println("[Stop View] Sum number on Boarded tab: " + txtBoard_StopView);
			System.out.println("[Stop List] Sum of boared students at stop #1: " + checkSumBoarded_Stop1);
			
			countTotal = countKnown + totalUnknown;
			
			if((txtBoard_StopView.equals(Integer.toString(countTotal))
					&& checkSumBoarded_Stop1.equals(Integer.toString(countTotal)))
						|| (txtBoard_StopView.equals(Integer.toString(countTotal))
					&& checkSumBoarded_Stop1.isEmpty())) {
				check = true;
			} 
			return check;
		} 
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//Function to check Stop view
	public boolean isOpenStopView() {
		System.out.println("Check stopview "+txtStopName.getText());
		return scrStopView.isDisplayed();
	}
	
	//Function to wait for Stop view
	public void waitStopView() {
		wait.until(ExpectedConditions.visibilityOf(scrStopView));
		System.out.println("stopview "+txtStopName.getText());
	}
	
	//close pop up ask user want to change to next run
	public void closeNextRunPopup() {
		wait.until(ExpectedConditions.visibilityOf(alertCancelButton));
		alertCancelButton.click();
		System.out.println("Close popup");
	}
	
	public String getStopnumber() {
		return 	txtStopName.getText().substring(12);
	}
	
	//Function to check Students displaying
	public boolean isStudentDisplayed() {
		MobileElement check = driver.findElement(By.id("com.edulog.driverportal:id/firstLine"));
		wait.until(ExpectedConditions.visibilityOf(check));
		return check.isDisplayed();
	}

	//Function to change Board view
	public void changeToBoard() {
		System.out.println("Click Board");
		menuBoard.click();
	}

	//Function to change Disembark view
	public void changeToDisembark() {
		System.out.println("Click Disembark");
		menuDisembark.click();
	}

	//Function to input a student
	public void inputStudent(String studentID) {
	/*	CharSequence charSequence = new StringBuffer(studentID + "\\n");
		   txtSwipeStudent.sendKeys(charSequence);
		   */
		System.out.println("Input studentID: "+studentID);
		txtInputStudent.click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sendkeys(robot, studentID);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.pressKeyCode(AndroidKeyCode.BACK);
		clickElement(btnInputStudent, 200);
	}

	// Function to select a student
	public void selectStudent(String studentID) {
		System.out.println("Select student: "+studentID);
		MobileElement student = driver.findElement(By.xpath("//*[contains(@text,'"+studentID+"')]"));
		clickElement(student, 200);
	}

	// Function to swipe a student card
	public void swipeStudentCard(String stdID) {
		System.out.println(stdID + "\\n");
		dummyFieldForSwipe.setValue(stdID + "\\n");
	}

	// Function to get Student info
	public void getStudentInfo() {
		System.out.println("Student name: "+txtStudentName.getText());
		System.out.println("Student ID: "+txtStudentID.getText());
	}

	// Function to get button name
	public String getButtonName() {
		return btnBoard.getText();
	}

	// Function to click board a student on student detail
	public void boardStudent() {
		System.out.println("Click "+btnBoard.getText()+" button");
		clickElement(btnBoard, 200);
	}
	
	// Function to close a student detail
	public void closeStudentDetail() {
		System.out.println("Close student detail");
		btnCloseDetail.click();
	}

	// Function to close a stop view
	public void closeStopView() {
		System.out.println("Close stop view");
		if(btnCloseStopView.isDisplayed()) {
			btnCloseStopView.click();
		}
	}
}