package com.tcvn.dataObject;

import java.util.List;

public class SqlRouteObject {
	public SqlRouteObject(String RouteNumber,
			String Section,
			String ToFrom,String RunID,
			String RunDescription,
			List<SqlStopObject> Stops) {
		this.RouteNumber = RouteNumber;
		this.Section = Section;
		this.ToFrom = ToFrom;
		this.RunID = RunID;
		this.RunDescription = RunDescription;
		this.Stops = Stops;
		
	}
	public SqlRouteObject() {
		// TODO Auto-generated constructor stub
	}
	private String RouteNumber;
	private String Section;
	private String ToFrom;
	private String RunID;
	private String RunDescription;
	private List<SqlStopObject> Stops;

	public String getRouteNumber() {
		return RouteNumber;
	}

	public void setRouteNumber(String routeNumber) {
		this.RouteNumber = routeNumber;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		this.Section = section;
	}

	public String getToFrom() {
		return ToFrom;
	}

	public void setToFrom(String toFrom) {
		this.ToFrom = toFrom;
	}

	public String getRunID() {
		return RunID;
	}

	public void setRunID(String runID) {
		this.RunID = runID;
	}

	public String getRunDescription() {
		return RunDescription;
	}

	public void setRunDescription(String runDescription) {
		this.RunDescription = runDescription;
	}

	public List<SqlStopObject> getStops() {
		return Stops;
	}

	public void setStops(List<SqlStopObject> stops) {
		this.Stops = stops;
	}

}
