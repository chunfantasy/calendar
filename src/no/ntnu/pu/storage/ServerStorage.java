package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ntnu.pu.model.Alarm;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.ChangeNotification;
import no.ntnu.pu.model.DeclineNotification;
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
		try {
			this.connect();
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			sql = "DROP TABLE IF EXISTS alarm";
			stmt.execute(sql);
			System.out.println("Database: Table alarm dropped");
			sql = "DROP TABLE IF EXISTS changenotification";
			stmt.execute(sql);
			System.out.println("Database: Table changenotification dropped");
			sql = "DROP TABLE IF EXISTS declinenotification";
			stmt.execute(sql);
			System.out.println("Database: Table declinenotification dropped");
			sql = "DROP TABLE IF EXISTS appointment_participant";
			stmt.execute(sql);
			System.out.println("Database: Table appointment_participant dropped");
			sql = "DROP TABLE IF EXISTS meetinggroup_person";
			stmt.execute(sql);
			System.out.println("Database: Table meetinggroup_person dropped");
			sql = "DROP TABLE IF EXISTS appointment";
			stmt.execute(sql);
			System.out.println("Database: Table appointment dropped");
			sql = "DROP TABLE IF EXISTS person";
			stmt.execute(sql);
			System.out.println("Database: Table person dropped");
			sql = "DROP TABLE IF EXISTS meetingroom";
			stmt.execute(sql);
			System.out.println("Database: Table meetingroom dropped");
			sql = "DROP TABLE IF EXISTS meetinggroup";
			stmt.execute(sql);
			System.out.println("Database: Table meetinggroup dropped");
	
	
			// create table person
			sql = "CREATE TABLE person (" 
					+ "id int auto_increment primary key, "
					+ "email varchar(30), "
					+ "name varchar(20), "
					+ "title varchar(20), "
					+ "password varchar(20), "
					+ "phonenumbers varchar(30))";
			stmt.execute(sql);
			System.out.println("Database: Table person created");
			
			// create table meetinggroup
			sql = "CREATE TABLE meetinggroup (" 
					+ "id int auto_increment primary key, "
					+ "name varchar(15)," 
					+ "email varchar(20))";
			stmt.execute(sql);
			System.out.println("Database: Table meetinggroup created");
			
			// create table meetinggroup_person
			sql = "CREATE TABLE meetinggroup_person (" 
					+ "id int auto_increment primary key, "
					+ "meetinggroupid int, " 
					+ "personid int, "
					+ "foreign key (meetinggroupid) references meetinggroup(id) on delete set null on update cascade, "
					+ "foreign key (personid) references person(id) on delete set null on update cascade)";
			stmt.execute(sql);		
			System.out.println("Database: Table meetinggroup_person created");
					
			// create table meetingroom
			sql = "CREATE TABLE meetingroom ("
					+ "id int auto_increment primary key, "
					+ "capacity int, "
					+ "roomname varchar(15))";
			stmt.execute(sql);
			System.out.println("Database: Table meetingroom created");
	
			// create table appointment
			sql = "CREATE TABLE appointment ("
					+ "id int auto_increment primary key, " 
					+ "title varchar(20), "
					+ "starttime datetime, " 
					+ "endtime datetime, "
					+ "address varchar(30), " 
					+ "meetingroomid int, " 
					+ "creatorid int, "
					+ "foreign key(meetingroomid) references meetingroom(id) on delete set null on update cascade, "
					+ "foreign key(creatorid) references person(id) on delete set null on update cascade, "
					+ "description varchar(50))";
			stmt.execute(sql);
			System.out.println("Database: Table appointment created");
	
			// create table appointment_participant
			sql = "CREATE TABLE appointment_participant ("
					+ "id int auto_increment primary key, " 
					+ "appointmentid int, "
					+ "personid int null, "
					+ "meetinggroupid int null, "
					+ "foreign key(personid) references person(id) on delete set null on update cascade, "
					+ "foreign key(meetinggroupid) references meetinggroup(id) on delete set null on update cascade) ";
			stmt.execute(sql);
			System.out.println("Database: Table appointment_participant created");
			
			// create table alarm
			sql = "CREATE TABLE alarm ("
					+ "id int auto_increment primary key, " 
					+ "appointmentid int, "
					+ "recipientid int, "
					+ "date datetime, "
					+ "foreign key(appointmentid) references appointment(id) on delete cascade on update cascade, "
					+ "foreign key(recipientid) references person(id) on delete cascade on update cascade) ";
			stmt.execute(sql);
			System.out.println("Database: Table alarm created");
			
			// create table declinenotification
			sql = "CREATE TABLE declinenotification ("
					+ "id int auto_increment primary key, " 
					+ "appointmentid int, "
					+ "recipientid int, "
					+ "declinerid int, "
					+ "foreign key(appointmentid) references appointment(id) on delete cascade on update cascade, "
					+ "foreign key(recipientid) references person(id) on delete cascade on update cascade, "
					+ "foreign key(declinerid) references person(id) on delete cascade on update cascade) ";
			stmt.execute(sql);
			System.out.println("Database: Table declinenotification created");
			
			// create table appointment_participant
			sql = "CREATE TABLE changenotification ("
					+ "id int auto_increment primary key, " 
					+ "appointmentid int, "
					+ "recipientid int, "
					+ "changedproperties varchar(200), "
					+ "foreign key(appointmentid) references appointment(id) on delete cascade on update cascade, "
					+ "foreign key(recipientid) references person(id) on delete cascade on update cascade) ";
			stmt.execute(sql);
			System.out.println("Database: Table changenotification created");
			//@formatter:on

			con.commit();
			System.out.println("Database: All tables reset");
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
			String phoneNumbers = rs.getString("phonenumbers");
			phoneNumbers = phoneNumbers.substring(1, phoneNumbers.length() - 1);
			String[] s = phoneNumbers.split(", ");
			for (String phoneNumber : s) {
				p.addPhoneNumber(phoneNumber);
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Group setGroup(ResultSet rs) {
		try {
			stmt = con.createStatement();
			Group g = new Group("");
			g.setId(rs.getInt("id"));
			g.setEmail(rs.getString("email"));
			g.setName(rs.getString("name"));

			sql = "SELECT * FROM meetinggroup_person WHERE meetinggroupid = "
					+ g.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<Integer> listId = new ArrayList<Integer>();
			while (rs.next()) {
				listId.add(rs.getInt("personid"));
			}
			ArrayList<Person> list = new ArrayList<Person>();
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
			r.setCapacity(rs.getInt("capacity"));
			r.setRoomname(rs.getString("roomname"));
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Appointment setAppointment(ResultSet rs) {
		try {
			stmt = con.createStatement();
			Person p = new Person("");
			Appointment a = new Appointment();
			a.setId(rs.getInt("id"));
			a.setAddress(rs.getString("address"));
			a.setDescription(rs.getString("description"));
			a.setStartTime(new Date(rs.getTimestamp("starttime").getTime()));
			a.setEndTime(new Date(rs.getTimestamp("endtime").getTime()));
			a.setTitle(rs.getString("title"));

			int meetingroomId = rs.getInt("meetingroomid");
			int creatorId = rs.getInt("creatorid");

			sql = "SELECT * FROM meetingroom WHERE id = " + meetingroomId;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				a.setMeetingRoom(this.setRoom(rs));
			}

			sql = "SELECT * FROM person WHERE id = " + creatorId;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				a.setCreator(this.setPerson(rs));
				;
			}

			sql = "SELECT * FROM appointment_participant WHERE appointmentid = "
					+ a.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<Integer> listPersonId = new ArrayList<Integer>();
			ArrayList<Integer> listGroupId = new ArrayList<Integer>();
			while (rs.next()) {
				if (rs.getInt("personid") != 0)
					listPersonId.add(rs.getInt("personid"));
				else if (rs.getInt("meetinggroupid") != 0)
					listGroupId.add(rs.getInt("meetinggroupid"));
				else
					continue;
			}

			for (int id : listPersonId) {
				sql = "SELECT * FROM person WHERE id = " + id;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					a.addParticipant(this.setPerson(rs));
				}
			}
			for (int id : listGroupId) {
				sql = "SELECT * FROM meetinggroup WHERE id = " + id;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					a.addParticipant(this.setGroup(rs));
				}
			}
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Alarm setAlarm(ResultSet rs) {
		try {
			stmt = con.createStatement();
			Person person = new Person("");
			Appointment appointment = new Appointment();
			Alarm a = new Alarm(new Date(), person, appointment);
			a.setId(rs.getInt("id"));
			a.setTime(new Date(rs.getTimestamp("date").getTime()));
			int appointmentId = rs.getInt("appointmentid");
			int recipientId = rs.getInt("recipientid");

			sql = "SELECT * FROM appointment WHERE id = " + appointmentId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				a.setAppointment(this.setAppointment(rs));

			sql = "SELECT * FROM person WHERE id = " + recipientId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				a.setRecipient(this.setPerson(rs));

			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected ChangeNotification setChangeNotification(ResultSet rs) {
		try {
			stmt = con.createStatement();
			Person person = new Person("");
			Appointment appointment = new Appointment();
			List<String> list = new ArrayList<String>();
			ChangeNotification c = new ChangeNotification(list,	person, appointment);
			c.setId(rs.getInt("id"));
			
			String changedProperties = rs.getString("changedproperties");
			changedProperties = changedProperties.substring(1, changedProperties.length() - 1);
			String[] s = changedProperties.split(", ");
			for (String changedProperty : s) {
				list.add(changedProperty);
			}
			c.setChangedProperties(list);
			
			int appointmentId = rs.getInt("appointmentid");
			int recipientId = rs.getInt("recipientid");

			sql = "SELECT * FROM appointment WHERE id = " + appointmentId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				c.setAppointment(this.setAppointment(rs));

			sql = "SELECT * FROM person WHERE id = " + recipientId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				c.setRecipient(this.setPerson(rs));

			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected DeclineNotification setDeclineNotification(ResultSet rs) {
		try {
			stmt = con.createStatement();
			Person person = new Person("");
			Appointment appointment = new Appointment();
			DeclineNotification d = new DeclineNotification(person, person,
					appointment);
			d.setId(rs.getInt("id"));
			int appointmentId = rs.getInt("appointmentid");
			int recipientId = rs.getInt("recipientid");
			int declinerId = rs.getInt("declinerid");

			sql = "SELECT * FROM appointment WHERE id = " + appointmentId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				d.setAppointment(this.setAppointment(rs));

			sql = "SELECT * FROM person WHERE id = " + recipientId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				d.setRecipient(this.setPerson(rs));

			sql = "SELECT * FROM person WHERE id = " + declinerId;
			rs = stmt.executeQuery(sql);
			if (rs.next())
				d.setDecliner(this.setPerson(rs));

			return d;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
