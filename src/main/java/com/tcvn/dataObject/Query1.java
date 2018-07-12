package com.tcvn.dataObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Query1 {
	private static String modifyDateLayout(String inputDate) throws ParseException{
	    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
	    return new SimpleDateFormat("HHmmss").format(date);
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:sqlserver://172.16.0.133:1433;databaseName=ETDBlive04022018",
					"etm", "etm");
			Statement stmt = connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT DISTINCT\r\n" + " Runs.RouteNumber\r\n" + " ,Runs.Section\r\n"
					+ " ,Runs.ToFrom\r\n" + " ,Runs.RunID\r\n" + " ,Runs.RunDescription\r\n" + " ,Stops.StopSeq\r\n"
					+ " ,Stops.StopRunID\r\n" + " ,Stops.StopID\r\n" + " ,Stops.Description\r\n"
					+ " ,Stops.ScheduledArrival\r\n" + " ,Stops.SchoolStop\r\n" + " ,Student.StudentID\r\n"
					+ " ,Student.FirstName\r\n" + " ,Student.LastName\r\n" + "FROM Runs\r\n"
					+ "    JOIN RunStop ON Runs.RunRoute=RunStop.RunRoute\r\n"
					+ "    JOIN Stops ON RunStop.StopRunID=Stops.StopRunID\r\n"
					+ " JOIN StudentStop ON Stops.StopRunID=StudentStop.StopRunID\r\n"
					+ " JOIN Student ON StudentStop.StudentID=Student.StudentID\r\n" + "WHERE \r\n"
					+ " Runs.RouteNumber IN ('"
					+ "180L"
					+ "')\r\n" + "ORDER BY Runs.RouteNumber\r\n" + " , Runs.Section\r\n"
					+ " , Runs.RunID\r\n" + " , Stops.StopSeq");
			List<SqlRouteObject> lstRoute = new ArrayList<SqlRouteObject>();
			List<String> tempRoute = new ArrayList<String>();
			List<String> tempStop = new ArrayList<String>();
			Gson gson = new Gson();
			int currentIndex = 0;
			while (rs.next()) {
				SqlStudentObject objStudent = new SqlStudentObject(rs.getString("StudentID").trim(), rs.getString("FirstName").trim(),
						rs.getString("LastName").trim());

				if (lstRoute.size() == 0) {
					SqlRouteObject objRoute = new SqlRouteObject();
					objRoute.setRouteNumber(rs.getString("RouteNumber"));
					objRoute.setSection(rs.getString("Section"));
					objRoute.setToFrom(rs.getString("ToFrom"));
					objRoute.setRunID(rs.getString("RunID"));
					objRoute.setRunDescription(rs.getString("RunDescription").trim());
					List<SqlStopObject> lstStop = new ArrayList<SqlStopObject>();
					objRoute.setStops(lstStop);
					lstRoute.add(objRoute);
					tempRoute.add(rs.getString("RunID"));
				} else {
					if (!tempRoute.contains(rs.getString("RunID"))) {
						SqlRouteObject objRoute = new SqlRouteObject();
						objRoute.setRouteNumber(rs.getString("RouteNumber"));
						objRoute.setSection(rs.getString("Section"));
						objRoute.setToFrom(rs.getString("ToFrom"));
						objRoute.setRunID(rs.getString("RunID"));
						objRoute.setRunDescription(rs.getString("RunDescription").trim());
						List<SqlStopObject> lstStop = new ArrayList<SqlStopObject>();
						objRoute.setStops(lstStop);
						lstRoute.add(objRoute);
						tempRoute.add(rs.getString("RunID"));
					}
				}
				int routeIndex = tempRoute.indexOf(rs.getString("RunID"));
				if (currentIndex != routeIndex) {
					tempStop.clear();
					currentIndex = routeIndex;
				}
				SqlRouteObject routeSelected = lstRoute.get(routeIndex);
				if (routeSelected.getStops().size() == 0) {
					
					SqlStopObject objStop = new SqlStopObject();
					objStop.setStopSeq(rs.getString("StopSeq"));
					objStop.setStopRunID(rs.getString("StopRunID"));
					objStop.setStopID(rs.getString("StopID"));
					objStop.setDescription(rs.getString("Description"));
					objStop.setScheduledArrival(modifyDateLayout(rs.getString("ScheduledArrival")));
					objStop.setSchoolStop(rs.getString("SchoolStop"));
					List<SqlStudentObject> lstStudent = new ArrayList<SqlStudentObject>();
					objStop.setStudent(lstStudent);
					routeSelected.getStops().add(objStop);
					tempStop.add(rs.getString("StopSeq"));
				} else {
					if (!tempStop.contains(rs.getString("StopSeq"))) {
						SqlStopObject objStop = new SqlStopObject();
						objStop.setStopSeq(rs.getString("StopSeq"));
						objStop.setStopRunID(rs.getString("StopRunID"));
						objStop.setStopID(rs.getString("StopID"));
						objStop.setDescription(rs.getString("Description"));
						objStop.setScheduledArrival(modifyDateLayout(rs.getString("ScheduledArrival")));
						objStop.setSchoolStop(rs.getString("SchoolStop"));
						List<SqlStudentObject> lstStudent = new ArrayList<SqlStudentObject>();
						objStop.setStudent(lstStudent);
						routeSelected.getStops().add(objStop);
						tempStop.add(rs.getString("StopSeq"));
					}
				}
				int stopIndex = tempStop.indexOf(rs.getString("StopSeq"));
				routeSelected.getStops().get(stopIndex).getStudent().add(objStudent);

			}
			try (Writer writer = new FileWriter("D:\\file.json")) {
				gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
				gson.toJson(lstRoute, writer);
			}
			for (int i=0;i<lstRoute.size();i++) {
				System.out.println("--------------");
				System.out.println(lstRoute.get(i).getRouteNumber());
				System.out.println(lstRoute.get(i).getSection());
				System.out.println(lstRoute.get(i).getToFrom());
				System.out.println(lstRoute.get(i).getRunID());
				System.out.println(lstRoute.get(i).getRunDescription());
				for (int j = 0; j < lstRoute.get(i).getStops().size(); j++) {
					System.out.println("Stop seq : "+lstRoute.get(i).getStops().get(j).getStopSeq());
					for (int j2 = 0; j2 < lstRoute.get(i).getStops().get(j).getStudent().size(); j2++) {
						System.out.println("Student of stop : "+lstRoute.get(i).getStops().get(j).getStudent().get(j2).getStudentID());
					}}}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}
}