package com.tcvn.dataObject;

import java.util.List;

public class SqlStopObject {
	public SqlStopObject(String StopSeq,
			String StopRunID,
			String StopID,String Description,
			String ScheduledArrival,
			String SchoolStop,
			List<SqlStudentObject> Student) {
		this.StopSeq = StopSeq;
		this.StopRunID = StopRunID;
		this.StopID = StopID;
		this.Description = Description;
		this.ScheduledArrival = ScheduledArrival;
		this.SchoolStop = SchoolStop;
		this.Student = Student;
	}
	public SqlStopObject() {
		// TODO Auto-generated constructor stub
	}
	private String StopSeq;
	private String StopRunID;
	private String StopID;
	private String Description;
	private String ScheduledArrival;
	private String SchoolStop;
	private List<SqlStudentObject> Student;
	public String getStopSeq() {
		return StopSeq;
	}
	public void setStopSeq(String stopSeq) {
		this.StopSeq = stopSeq;
	}
	public String getStopRunID() {
		return StopRunID;
	}
	public void setStopRunID(String stopRunID) {
		this.StopRunID = stopRunID;
	}
	public String getStopID() {
		return StopID;
	}
	public void setStopID(String stopID) {
		this.StopID = stopID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		this.Description = description;
	}
	public String getScheduledArrival() {
		return ScheduledArrival;
	}
	public void setScheduledArrival(String scheduledArrival) {
		this.ScheduledArrival = scheduledArrival;
	}
	public String getSchoolStop() {
		return SchoolStop;
	}
	public void setSchoolStop(String schoolStop) {
		this.SchoolStop = schoolStop;
	}
	public List<SqlStudentObject> getStudent() {
		return Student;
	}
	public void setStudent(List<SqlStudentObject> student) {
		this.Student = student;
	}
	
}
