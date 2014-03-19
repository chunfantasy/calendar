package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Person;

public class PersonStorage extends ServerStorage {

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

	public boolean updatePerson(Person p) {
		try {
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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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

	public boolean deletePersonByEmail(String email) {
		try {
			sql = "DELETE FROM person WHERE email = '" + email + "'";
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Person> getAll() {
		try {
			sql = "SELECT * FROM person";
			rs = stmt.executeQuery(sql);
			ArrayList<Person> list = new ArrayList<Person>();
			while (rs.next()) {
				list.add(this.setPerson(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

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

	public Person getPersonByEmail(String email) {
		try {
			sql = "SELECT * FROM person WHERE email = '" + email + "'";
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

	public ArrayList<Person> getPersonByName(String name) {
		try {
			sql = "SELECT * FROM person WHERE name = '" + name + "'";
			rs = stmt.executeQuery(sql);
			ArrayList<Person> list = new ArrayList<Person>();
			while (rs.next()) {
				list.add(this.setPerson(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
