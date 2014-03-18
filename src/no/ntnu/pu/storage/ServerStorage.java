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

	public ServerStorage() throws SQLException{
		this.connect();
	}
	
	// Connect to the database
	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
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

	private Person setPerson(ResultSet rs) throws SQLException {
		Person p = new Person("");
		p.setId(rs.getInt("id"));
		p.setEmail(rs.getString("email"));
		p.setName(rs.getString("name"));
		p.setTitle(rs.getString("title"));
		p.setPassword(rs.getString("password"));
		return p;
	}

	private Group setGroup(ResultSet rs) throws SQLException {
		Group g = new Group("");
		g.setId(rs.getInt("id"));
		g.setEmail(rs.getString("email"));
		g.setName(rs.getString("name"));

		try {
			sql = "SELECT * FROM meetinggroup_person WHERE meetinggroupid = "
					+ g.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<Integer> listId = new ArrayList();
			while (rs.next()) {
				listId.add(rs.getInt("personid"));
			}
			ArrayList<Person> list = new ArrayList();
			for (int id : listId) {
				sql = "SELECT * FROM person WHERE id = " + id;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					list.add(setPerson(rs));
				}
			}
			g.setPersons(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return g;
	}

	private Room setRoom(ResultSet rs) throws SQLException {
		Room r = new Room("");
		r.setId(rs.getInt("id"));
		r.setRoomname(rs.getString("roomname"));
		return r;
	}

	private Appointment setAppointment(ResultSet rs) throws SQLException {
		Person p = new Person("");
		Appointment a = new Appointment(p);
		a.setId(rs.getInt("id"));
		return a;
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ServerStorage serverStorage = new ServerStorage();
		serverStorage.connect();
		serverStorage.initiate();

		Person p1 = new Person("a");
		p1.setEmail("email1");
		p1.setTitle("title1");
		p1 = serverStorage.insertPerson(p1);

		Person p2 = new Person("b");
		p2.setEmail("email2");
		p2.setTitle("title2");
		p2 = serverStorage.insertPerson(p2);

		Person p3 = new Person("c");
		p3.setEmail("email3");
		p3.setTitle("title3");
		p3 = serverStorage.insertPerson(p3);

		Group g = new Group("super group 12");
		g.addPerson(p1);
		g = serverStorage.insertGroup(g);
		g.addPerson(p3);
		serverStorage.updateGroup(g);
		serverStorage.updateGroup(g);
		serverStorage.updateGroup(g);

		g.removePerson(p1);
		g.removePerson(p1);
		g.removePerson(p1);
		serverStorage.updateGroup(g);
		g.addPerson(p2);
		serverStorage.updateGroup(g);

		g = serverStorage.getGroupById(1);

		p1.setEmail("emaillllllllllllllll");
		serverStorage.updatePerson(p1);

		Room r = new Room("P15");
		r.setId(1);
		serverStorage.insertRoom(r);

		Appointment a = new Appointment(p1);
		a.setTitle("gogogo");
		a.setStartTime(new Date());
		a.setEndTime(new Date());
		a.setMeetingRoom(r);
		ArrayList<Participant> participants = new ArrayList();
		a.addParticipant(p2);
		a.addParticipant(p3);
		a.addParticipant(participant);
		serverStorage.insertAppointment(a);

		a.setTitle("comecomecome");
		a.addParticipant(g);
		a.addParticipant(g);
		a.addParticipant(g);
		a.addParticipant(p2);
		serverStorage.updateAppointment(a);

		serverStorage.updateAppointment(a);

		ArrayList<Appointment> appointmentByTime = serverStorage
				.getAppointmentByParticipant(p3);
		System.out.println(appointmentByTime);

	}

	@Override
	public Person insertPerson(Person p) {
		try {
			sql = "INSERT INTO person(email, name, title, password, phonenumbers) VALUES(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p.getEmail());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, p.getTitle());
			pstmt.setString(4, p.getPassword());
			pstmt.setString(5, p.getPhoneNumbers().toString());
			pstmt.executeUpdate();
			p.setId(this.getLastId());
			con.commit();
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updatePerson(Person p) {
		try {
			sql = "UPDATE  person SET email = ?, name= ?, title = ?, password = ? WHERE id = "
					+ p.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p.getEmail());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, p.getTitle());
			pstmt.setString(4, p.getPassword());
			pstmt.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePersonById(int id) {
		try {
			sql = "DELETE FROM person WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePersonByEmail(String email) {
		try {
			sql = "DELETE FROM person WHERE email = " + email;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Person getPersonById(int id) {
		try {
			sql = "SELECT * FROM person WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setPerson(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Person getPersonByEmail(String email) {
		try {
			sql = "SELECT * FROM person WHERE email = " + email;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setPerson(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Person> getPersonByName(String name) {
		try {
			sql = "SELECT * FROM person WHERE name = " + name;
			rs = stmt.executeQuery(sql);
			ArrayList<Person> list = new ArrayList();
			while (rs.next()) {
				list.add(this.setPerson(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Group insertGroup(Group g) {
		try {
			sql = "INSERT INTO meetinggroup(email, name) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, g.getEmail());
			pstmt.setString(2, g.getName());
			pstmt.executeUpdate();
			g.setId(this.getLastId());

			if (!g.getPersons().isEmpty()) {
				for (Person person : g.getPersons()) {
					sql = "INSERT INTO meetinggroup_person (meetinggroupid, personid) "
							+ "VALUES(?, ?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, g.getId());
					pstmt.setInt(2, person.getId());
					pstmt.executeUpdate();
				}
			}
			con.commit();
			return g;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateGroup(Group g) {
		try {
			sql = "UPDATE meetinggroup SET email = ?, name = ? WHERE id = "
					+ g.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, g.getEmail());
			pstmt.setString(2, g.getName());
			pstmt.executeUpdate();

			try {
				sql = "SELECT * FROM meetinggroup_person WHERE meetinggroupid = "
						+ g.getId();
				rs = stmt.executeQuery(sql);
				ArrayList<Integer> listOld = new ArrayList();
				while (rs.next()) {
					listOld.add(rs.getInt("personid"));
				}
				ArrayList<Integer> listNew = new ArrayList();
				for (Person p : g.getPersons()) {
					listNew.add(p.getId());
				}

				ArrayList<Integer> list = new ArrayList();
				for (int id : listOld) {
					if (!listNew.contains(id)) {
						sql = "DELETE FROM meetinggroup_person WHERE meetinggroupid = ? AND personid = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, g.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
						list.add(id);
					}
				}

				for (int id : list) {
					listOld.remove((Integer.valueOf(id)));
				}

				for (int id : listNew) {
					if (!listOld.contains(id)) {
						sql = "INSERT INTO meetinggroup_person(meetinggroupid, personid) VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, g.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteGroupById(int id) {
		try {
			sql = "DELETE FROM meetinggroup WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteGroupByEmail(String email) {
		try {
			sql = "DELETE FROM meetinggroup WHERE email = " + email;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Group getGroupById(int id) {
		try {
			sql = "SELECT * FROM meetinggroup WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setGroup(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Group getGroupByEmail(String email) {
		try {
			sql = "SELECT * FROM meetinggroup WHERE email = " + email;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setGroup(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Group> getGroupByName(String name) {
		try {
			sql = "SELECT * FROM meetinggroup WHERE name = " + name;
			rs = stmt.executeQuery(sql);
			ArrayList<Group> list = new ArrayList();
			while (rs.next()) {
				list.add(this.setGroup(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Appointment insertAppointment(Appointment a) {
		try {
			sql = "INSERT INTO appointment(title, starttime, endtime, address, description, meetingroomid) "
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
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateAppointment(Appointment a) {
		try {
			sql = "UPDATE appointment SET title = ?, starttime = ?, endtime = ?, address = ?, description = ?, meetingroomid = ? "
					+ "WHERE id = " + a.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a.getTitle());
			pstmt.setTimestamp(2, new Timestamp(a.getStartTime().getTime()));
			pstmt.setTimestamp(3, new Timestamp(a.getEndTime().getTime()));
			pstmt.setString(4, a.getAddress());
			pstmt.setString(5, a.getDescription());
			pstmt.setInt(6, a.getMeetingRoom().getId());
			pstmt.executeUpdate();

			try {
				sql = "SELECT * FROM appointment_participant WHERE appointmentid = "
						+ a.getId();
				rs = stmt.executeQuery(sql);
				ArrayList<Integer> listOldPerson = new ArrayList();
				ArrayList<Integer> listOldGroup = new ArrayList();
				while (rs.next()) {
					if (rs.getInt("personid") != 0)
						listOldPerson.add(rs.getInt("personid"));
					if (rs.getInt("meetinggroupid") != 0)
						listOldGroup.add(rs.getInt("meetinggroupid"));
				}

				ArrayList<Integer> listNewPerson = new ArrayList();
				ArrayList<Integer> listNewGroup = new ArrayList();
				for (Participant p : a.getParticipants()) {
					if (p instanceof Person)
						listNewPerson.add(((Person) p).getId());
					if (p instanceof Group)
						listNewGroup.add(((Group) p).getId());
				}

				ArrayList<Integer> list = new ArrayList();
				for (int id : listOldPerson) {
					if (!listNewPerson.contains(id)) {
						sql = "DELETE FROM appointment_participant WHERE appointmentid = ? AND personid = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
						list.add(id);
					}
				}

				for (int id : list) {
					listOldPerson.remove((Integer.valueOf(id)));
				}

				for (int id : listNewPerson) {
					if (!listOldPerson.contains(id)) {
						sql = "INSERT INTO appointment_participant (appointmentid, personid) VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
					}
				}

				list.clear();
				for (int id : listOldGroup) {
					if (!listNewGroup.contains(id)) {
						sql = "DELETE FROM appointment_participant WHERE appointmentid = ? AND meetinggroupid = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
						list.add(id);
					}
				}

				for (int id : list) {
					listOldPerson.remove((Integer.valueOf(id)));
				}

				for (int id : listNewGroup) {
					if (!listOldGroup.contains(id)) {
						sql = "INSERT INTO appointment_participant (appointmentid, meetinggroupid) VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAppointmentById(int id) {
		try {
			sql = "DELETE FROM appointment WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime) {
		try {
			sql = "SELECT * FROM appointment WHERE starttime >= '"
					+ new Timestamp(startTime.getTime()) + "' AND endtime <= '"
					+ new Timestamp(endTime.getTime()) + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			ArrayList<Appointment> list = new ArrayList();
			while (rs.next()) {
				list.add(this.setAppointment(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Appointment> getAppointmentByParticipant(Participant p) {
		try {
			if (p instanceof Person)
				sql = "SELECT * FROM appointment_participant WHERE personid = "
						+ ((Person) p).getId();
			else if (p instanceof Group)
				sql = "SELECT * FROM appointment_participant WHERE meetinggroupid = "
						+ ((Group) p).getId();
			else
				return null;
			rs = stmt.executeQuery(sql);
			ArrayList<Appointment> list = new ArrayList();
			while (rs.next()) {
				list.add(this.setAppointment(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Room insertRoom(Room r) {
		try {
			sql = "INSERT INTO meetingroom(roomname) VALUES(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, r.getRoomname());
			pstmt.executeUpdate();
			r.setId(this.getLastId());
			con.commit();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateRoom(Room r) {
		try {
			sql = "UPDATE meetingroom SET roomname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, r.getRoomname());
			pstmt.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	@Override
	public boolean deleteRoomById(int id) {
		try {
			sql = "DELETE FROM meetinggroup WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Room getRoomById(int id) {
		try {
			sql = "SELECT * FROM meetingroom WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setRoom(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
