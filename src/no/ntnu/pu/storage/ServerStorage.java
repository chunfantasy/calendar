package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

public class ServerStorage extends Storage {
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;

	// Connect to the database
	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/calendar", "root", "123");
		con.setAutoCommit(false);
	}

	// initiate database
	public void initiate() throws SQLException {

		stmt = con.createStatement();

		//@formatter:off
		// create table person
		sql = "DROP TABLE IF EXISTS person";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS appointment";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS meetingroom";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS meetinggroup";
		stmt.execute(sql);

		sql = "CREATE TABLE person (" 
				+ "id int auto_increment primary key, "
				+ "email varchar(20), " 
				+ "name varchar(10), "
				+ "title varchar(10))";
		stmt.execute(sql);

		// create talbe meetingroom
		sql = "CREATE TABLE meetingroom ("
				+ "id int auto_increment primary key, "
				+ "roomname varchar(15))";
		stmt.execute(sql);

		sql = "CREATE TABLE appointment ("
				+ "id int auto_increment primary key, " 
				+ "title varchar(20), "
				+ "starttime datetime, " 
				+ "endtime datetime, "
				+ "adress varchar(30), " 
				+ "meetingroomid int, "
				+ "foreign key(meetingroomid) references meetingroom(id), "
				+ "description varchar(50))";
		stmt.execute(sql);

		sql = "CREATE TABLE meetinggroup (" 
				+ "id int auto_increment primary key, "
				+ "name varchar(15)," 
				+ "email varchar(20))";
		stmt.execute(sql);
		
		//@formatter:on
		con.commit();
	}

	// Save an object in the database
	public boolean insert(Object o) throws SQLException {
		// table person
		if (o instanceof Person) {
			Person p = (Person) o;
			sql = "INSERT INTO person(email, name, title) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p.getEmail());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, p.getTitle());
			pstmt.executeUpdate();
		}

		// talbe meetingroom
		if (o instanceof Room) {
			Room r = (Room) o;
			sql = "INSERT INTO meetingroom(roomname) VALUES(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, r.getRoomname());
			pstmt.executeUpdate();
		}

		// table appointment appointment_participant
		else if (o instanceof Appointment) {
			Appointment a = (Appointment) o;
			sql = "INSERT INTO appointment(title, starttime, endtime, adress, description, meetingroomid) "
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a.getTitle());
			pstmt.setTimestamp(2, new Timestamp(a.getStartTime().getTime()));
			pstmt.setTimestamp(3, new Timestamp(a.getEndTime().getTime()));
			pstmt.setString(4, a.getAdress());
			pstmt.setString(5, a.getDescription());
			pstmt.setInt(6, a.getMeetingRoom().getId());
			pstmt.executeUpdate();

		}

		else if (o instanceof Group) {
			Group g = (Group) o;
			sql = "INSERT INTO meetinggroup(email, name) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, g.getEmail());
			pstmt.setString(2, g.getName());
			pstmt.executeUpdate();

		} else
			return false;
		
		con.commit();
		return true;
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ServerStorage serverStorage = new ServerStorage();
		serverStorage.connect();
		serverStorage.initiate();

		Person p = new Person("a");
		p.setEmail("email");
		p.setTitle("title");

		serverStorage.insert(p);

		Room r = new Room("P15");
		r.setId(1);
		serverStorage.insert(r);

		Appointment a = new Appointment();
		a.setTitle("gogogo");
		a.setStartTime(new Date());
		a.setEndTime(new Date());
		a.setMeetingRoom(r);
		serverStorage.insert(a);

		Group g = new Group("super group 12");
		serverStorage.insert(g);
	}

}
