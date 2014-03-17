package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

public class ServerStorage implements Storage {
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
		sql = "DROP TABLE IF EXISTS appointment_participant";
		stmt.execute(sql);
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

		// create table meetinggroup
		sql = "CREATE TABLE meetinggroup (" 
				+ "id int auto_increment primary key, "
				+ "name varchar(15)," 
				+ "email varchar(20))";
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
				+ "adress varchar(30), " 
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
	}

	// Save an object in the database
	public Object insert(Object o) throws SQLException {
		// table person
		if (o instanceof Person) {
			Person p = (Person) o;
			sql = "INSERT INTO person(email, name, title) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p.getEmail());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, p.getTitle());
			pstmt.executeUpdate();
			p.setId(this.getLastId());
			con.commit();
			return p;
		}

		// table meetingroom
		if (o instanceof Room) {
			Room r = (Room) o;
			sql = "INSERT INTO meetingroom(roomname) VALUES(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, r.getRoomname());
			pstmt.executeUpdate();
			r.setId(this.getLastId());
			con.commit();
			return r;
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
			pstmt.setString(4, a.getAddress());
			pstmt.setString(5, a.getDescription());
			pstmt.setInt(6, a.getMeetingRoom().getId());
			pstmt.executeUpdate();
			a.setId(this.getLastId());

			if (!a.getParticipants().isEmpty()) {
				for (Participant participant : a.getParticipants()) {
					if (participant instanceof Person) {
						sql = "INSERT INTO appointment_participant (appointmentid, personid) "
								+ "VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, ((Person) participant).getId());
						pstmt.executeUpdate();
					}

					else if (participant instanceof Group) {
						sql = "INSERT INTO appointment_participant (appointmentid, meetinggroupid) "
								+ "VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, ((Group) participant).getId());
						pstmt.executeUpdate();
					}

					else
						continue;
				}
			}
			con.commit();
			return a;
		}

		// table group
		else if (o instanceof Group) {
			Group g = (Group) o;
			sql = "INSERT INTO meetinggroup(email, name) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, g.getEmail());
			pstmt.setString(2, g.getName());
			pstmt.executeUpdate();
			g.setId(this.getLastId());
			con.commit();
			return g;
		} else
			return null;
	}

	public boolean delete(Object o) throws SQLException {
		if (o instanceof Person) {
			sql = "DELETE FROM person WHERE id = " + ((Person) o).getId();
			stmt.executeUpdate(sql);
			con.commit();
			return true;
		}

		else if (o instanceof Room) {
			sql = "DELETE FROM meetingroom WHERE id = " + ((Room) o).getId();
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
			sql = "DELETE FROM meetinggroup WHERE id = " + ((Group) o).getId();
			stmt.executeUpdate(sql);
			con.commit();
			return true;
		}

		else
			return false;
	}

	public void close() throws SQLException {
		this.con.close();
	}

	public int getLastId() throws SQLException {
		this.rs = this.pstmt.executeQuery("select last_insert_id()");
		if (this.rs.next()) {
			int id = rs.getInt(1);
			return id;
		} else
			return -1;

	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ServerStorage serverStorage = new ServerStorage();
		serverStorage.connect();
		serverStorage.initiate();

		Person p = new Person("a");
		p.setEmail("email");
		p.setTitle("title");
		p = (Person) serverStorage.insert(p);

		Group g = new Group("super group 12");
		g = (Group) serverStorage.insert(g);

		Room r = new Room("P15");
		r.setId(1);
		serverStorage.insert(r);

		Appointment a = new Appointment("");
		a.setTitle("gogogo");
		a.setStartTime(new Date());
		a.setEndTime(new Date());
		a.setMeetingRoom(r);
		ArrayList<Participant> participants = new ArrayList<>();
		participants.add(p);
		participants.add(g);
		a.setParticipants(participants);
		serverStorage.insert(a);

		System.out.println(serverStorage.deletePersonByEmail("email"));

	}

	@Override
	public Person getPersonByEmail(String email) throws SQLException {
		sql = "SELECT * FROM person WHERE email = " + email;
		rs = stmt.executeQuery(sql);
		Person p = new Person("");
		if (rs.next()) {
			p.setId(rs.getInt("id"));
			p.setEmail(rs.getString("email"));
			p.setName(rs.getString("name"));
			p.setTitle(rs.getString("title"));
		}
		return p;
	}

	@Override
	public ArrayList<Person> getPersonByName(String email) throws SQLException {
		sql = "SELECT * FROM PERSON WHERE email = " + email;
		rs = stmt.executeQuery(sql);
		Person p = new Person("");
		ArrayList<Person> list = new ArrayList<>();
		while (rs.next()) {
			p.setId(rs.getInt("id"));
			p.setEmail(rs.getString("email"));
			p.setName(rs.getString("name"));
			p.setTitle(rs.getString("title"));
			list.add(p);
		}
		return list;
	}

	@Override
	public Person insertPerson(Person p) throws SQLException {
		sql = "INSERT INTO person(email, name, title) VALUES(?, ?, ?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p.getEmail());
		pstmt.setString(2, p.getName());
		pstmt.setString(3, p.getTitle());
		pstmt.executeUpdate();
		p.setId(this.getLastId());
		con.commit();
		return p;
	}

	@Override
	public boolean deletePersonByEmail(String email) throws SQLException {
		try {
			sql = "DELETE FROM person WHERE email = " + email;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Group getGroupByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group insertGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteGroupByEmail(Group g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Appointment> getAppointmentByParticipant(Participant p) {
		// TODO Auto-generated method stub
		return null;
	}

}
