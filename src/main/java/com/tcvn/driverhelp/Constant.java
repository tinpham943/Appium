package com.tcvn.driverhelp;

public interface Constant {

	public static final String AndroidOS						= "Android";
	public static final String AndroidOS_NoInstall				= "Android_NoInstall";
	public static final String AndroidVer_51					= "5.1";
	public static final String AndroidVer_50					= "5.0";
	public static final String DP_AppPackage					= "com.edulog.driverportal";
	public static final String DP_AppActivity					= "com.edulog.driverportal.ui.activities.LaunchActivity";
	public static final String Mock_AppPackage					= "com.twolinessoftware.android";
	public static final String Mock_AppActivity					= "com.twolinessoftware.android.MainActivity";
	public static final String Setting_AppPackage				= "com.android.settings";
	public static final String Setting_AppActivity				= "com.android.settings.Settings";

	//==========Excel Paths==========
	public static final String Path_TestData			= System.getProperty("user.dir")+"/src/main/resources/ExcelData/";
	public static final String File_UserLogin			= "UserLogin.xlsx";
	public static final String File_RouteToTest			= "RouteToRun.xlsx";
	public static final String File_RouteToTest_Qui		= "EastAllenSG_Qui.xlsx";

	//==========Login Message Error==========
	public static final String LoginErr_3FieldsEmpty				= "Your Bus ID, Driver ID and Password are empty";
	public static final String LoginErr_BusID_DriverID_Empty		= "Your Bus ID and Driver ID are empty";
	public static final String LoginErr_BusID_Password_Empty		= "Your Bus ID and Password are empty";
	public static final String LoginErr_DriverID_Password_Empty		= "Your Driver ID and Password are empty";
	public static final String LoginErr_BusID_Empty 				= "Your Bus ID is empty";
	public static final String LoginErr_DriverID_Empty 				= "Your Driver ID is empty";
	public static final String LoginErr_Password_Empty 				= "Your Password is empty";
	public static final String LoginErr_WrongPassword 				= "Oops, your Driver ID or Password is incorrect";

	//==========SSO Alerts==========
	public static final String SSOalert_Selecting = "Are you sure you want to switch the session owner ?";
	public static final String SSOalert_Relogin = "Do you want to load the last states of all the features of the previous session owner?";
}
