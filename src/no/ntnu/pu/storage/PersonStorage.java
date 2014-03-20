package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Person;

public class PersonStorage extends ServerStorage {

	public PersonStorage() {
		super();
		System.out.println("Database: Database will be connected by PersonStorage");
	}

	public Person insertPerson(Person p) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
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
			con.close();
			this.con = null;
			System.out.println("Database: Person inserted done");
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAIL: Database: Person inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updatePerson(Person p) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "UPDATE  person SET email = ?, name= ?, title = ?, password = ?, phonenumbers = ? WHERE id = "
					+ p.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p.getEmail());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, p.getTitle());
			pstmt.setString(4, p.getPassword());
			pstmt.setString(5, p.getPhoneNumbers().toString());
			pstmt.executeUpdate();
			con.commit();
			con.close();
			System.out.println("Database: Person updated done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person updated failed!!!!!!");
			return false;
		}
	}

	public boolean deletePersonById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM person WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: Person deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person deleted failed!!!!!!");
			return false;
		}
	}

	public boolean deletePersonByEmail(String email) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM person WHERE email = '" + email + "'";
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: Person deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person deleted failed!!!!!!");
			return false;
		}
	}

	public ArrayList<Person> getAll() {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM person";
			rs = stmt.executeQuery(sql);
			ArrayList<Person> list = new ArrayList<Person>();
			while (rs.next()) {
				list.add(this.setPerson(rs));
			}
			con.close();
			System.out.println("Database: Person gotten done");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person gotten failed!!!!!!");
			return null;
		}
	}

	public Person getPersonById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM person WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Person person = this.setPerson(rs);
				con.close();
				System.out.println("Database: Person gotten done");
				return person;
			} else {
				con.close();
				System.out
						.println("FAIL: Database: Person gotten failed!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person gotten failed!!!!!!");
			return null;
		}
	}

	public Person getPersonByEmail(String email) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM person WHERE email = '" + email + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Person person = this.setPerson(rs);
				con.close();
				System.out.println("Database: Person gotten done");
				return person;
			} else {
				con.close();
				System.out
						.println("FAIL: Database: Person gotten failed!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person gotten failed!!!!!!");
			return null;
		}
	}

	public ArrayList<Person> getPersonByName(String name) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM person WHERE name = '" + name + "'";
			rs = stmt.executeQuery(sql);
			ArrayList<Person> list = new ArrayList<Person>();
			while (rs.next()) {
				list.add(this.setPerson(rs));
			}
			con.close();
			System.out.println("Database: Person gotten done");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Person gotten failed!!!!!!");
			return null;
		}
	}
}
