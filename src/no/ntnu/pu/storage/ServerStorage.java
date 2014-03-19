package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

public class ServerStorage {
	protected Connection con;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	protected String sql;

	public ServerStorage() {
		this.connect();
	}

	// Connect to the database
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// mysql -h mysql.stud.ntnu.no/chunf_calendar
			// -u chunf_calender -pgroup12
			con = DriverManager.getConnection(
					"jdbc:mysql://mysql.stud.ntnu.no/chunf_calendar",
					"chunf_calendar", "group12");
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// initiate database
	public void initiate() {
		try {
			stmt = con.createStatement();

		//@formatter:off
		// create table person
		sql = "DROP TABLE IF EXISTS appointment_participant";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS meetinggroup_person";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS person";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS appointment";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS meetingroom";
		stmt.execute(sql);
		sql = "DROP TABLE IF EXISTS meetinggroup";
		stmt.execute(sql);


		// create table person
		sql = "CREATE TABLE person (" 
				+ "id int auto_increment primary key, "
				+ "email varchar(20), " 
				+ "name varchar(10), "
				+ "title varchar(10), "
				+ "password varchar(20), "
				+ "phonenumbers varchar(30))";
		stmt.execute(sql);

		// create table meetinggroup
		sql = "CREATE TABLE meetinggroup (" 
				+ "id int auto_increment primary key, "
				+ "name varchar(15)," 
				+ "email varchar(20))";
		stmt.execute(sql);
		
		// create table meetinggroup_person
		sql = "CREATE TABLE meetinggroup_person (" 
				+ "id int auto_increment primary key, "
				+ "meetinggroupid int, " 
				+ "personid int, "
				+ "foreign key (meetinggroupid) references meetinggroup(id) on delete set null on update cascade, "
				+ "foreign key (personid) references person(id) on delete set null on update cascade)";
		stmt.execute(sql);		
				
		// create table meetingroom
		sql = "CREATE TABLE meetingroom ("
				+ "id int auto_increment primary key, "
				+ "roomname varchar(15))";
		stmt.execute(sql);

		// create table appointment
		sql = "CREATE TABLE appointment ("
				+ "id int auto_increment primary key, " 
				+ "title varchar(20), "
				+ "starttime datetime, " 
				+ "endtime datetime, "
				+ "address varchar(30), " 
				+ "meetingroomid int, "
				+ "foreign key(meetingroomid) references meetingroom(id) on delete set null on update cascade, "
				+ "description varchar(50))";
		stmt.execute(sql);

		// create table appointment_participant
		sql = "CREATE TABLE appointment_participant ("
				+ "id int auto_increment primary key, " 
				+ "appointmentid int, "
				+ "personid int null, "
				+ "meetinggroupid int null, "
				+ "foreign key(personid) references person(id) on delete set null on update cascade, "
				+ "foreign key(meetinggroupid) references meetinggroup(id) on delete set null on update cascade) ";
		stmt.execute(sql);
		//@formatter:on
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean delete(Object o) {
		try {
			if (o instanceof Person) {
				sql = "DELETE FROM person WHERE id = " + ((Person) o).getId();
				stmt.executeUpdate(sql);
				con.commit();
				return true;
			}

			else if (o instanceof Room) {
				sql = "DELETE FROM meetingroom WHERE id = "
						+ ((Room) o).getId();
				stmt.executeUpdate(sql);
				con.commit();
				return true;
			}

			else if (o instanceof Appointment) {
				sql = "DELETE FROM appointment WHERE id = "
						+ ((Appointment) o).getId();
				stmt.executeUpdate(sql);
				con.commit();
				return true;
			}

			else if (o instanceof Group) {
				sql = "DELETE FROM meetinggroup WHERE id = "
						+ ((Group) o).getId();
				stmt.executeUpdate(sql);
				con.commit();
				return true;
			}

			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getLastId() {
		try {
			this.rs = this.pstmt.executeQuery("select last_insert_id()");
			if (this.rs.next()) {
				int id = rs.getInt(1);
				return id;
			} else
				return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	protected Person setPerson(ResultSet rs) {
		try {
			Person p = new Person("");
			p.setId(rs.getInt("id"));
			p.setEmail(rs.getString("email"));
			p.setName(rs.getString("name"));
			p.setTitle(rs.getString("title"));
			p.setPassword(rs.getString("password"));
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Group setGroup(ResultSet rs) {
		try {
			Group g = new Group("");
			g.setId(rs.getInt("id"));
			g.setEmail(rs.getString("email"));
			g.setName(rs.getString("name"));

			sql = "SELECT * FROM meetinggroup_person WHERE meetinggroupid = "
					+ g.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<Integer> listId = new ArrayList<>();
			while (rs.next()) {
				listId.add(rs.getInt("personid"));
			}
			ArrayList<Person> list = new ArrayList<>();
			for (int id : listId) {
				sql = "SELECT * FROM person WHERE id = " + id;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					list.add(setPerson(rs));
				}
			}
			g.setPersons(list);
			return g;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	protected Room setRoom(ResultSet rs) {
		try {
			Room r = new Room("");
			r.setId(rs.getInt("id"));
			r.setRoomname(rs.getString("roomname"));
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Appointment setAppointment(ResultSet rs) {
		try {
			Person p = new Person("");
			Appointment a = new Appointment(p);
			a.setId(rs.getInt("id"));
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
