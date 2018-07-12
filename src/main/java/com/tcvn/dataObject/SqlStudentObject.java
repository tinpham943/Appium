package com.tcvn.dataObject;


public class SqlStudentObject {
	public SqlStudentObject (String StudentID,
			String FirstName,
			String LastName) {
		this.StudentID = StudentID;
		this.FirstName = FirstName;
		this.LastName = LastName;
		
	}
	public SqlStudentObject() {
		
	}
	private String StudentID;
	private String FirstName;
	private String LastName;
	public String getStudentID() {
		return StudentID;
	}
	public void setStudentID(String studentID) {
		this.StudentID = studentID;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		this.LastName = lastName;
	}
}
