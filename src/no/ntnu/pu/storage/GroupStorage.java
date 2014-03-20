package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Person;

public class GroupStorage extends ServerStorage {

	public GroupStorage() {
		super();
		System.out
				.println("Database: Database will be connected by GroupStorage");
	}

	public Group insertGroup(Group g) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
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
			con.close();
			System.out.println("Database: Group inserted done");
			return g;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateGroup(Group g) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
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
				ArrayList<Integer> listOld = new ArrayList<Integer>();
				while (rs.next()) {
					listOld.add(rs.getInt("personid"));
				}
				ArrayList<Integer> listNew = new ArrayList<Integer>();
				for (Person p : g.getPersons()) {
					listNew.add(p.getId());
				}

				ArrayList<Integer> list = new ArrayList<Integer>();
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
				System.out
						.println("FAIL: Database: Group updated failed!!!!!!");
				return false;
			}
			con.close();
			System.out.println("Database: Group updated done");
			con.commit();
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group updated failed!!!!!!");
			return false;
		}
	}

	public boolean deleteGroupById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM meetinggroup WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: Group deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group deleted failed!!!!!!");
			return false;
		}
	}

	public boolean deleteGroupByEmail(String email) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM meetinggroup WHERE email = '" + email + "'";
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: Group deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group deleted failed!!!!!!");
			return false;
		}
	}

	public ArrayList<Group> getAll() {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM meetinggroup";
			rs = stmt.executeQuery(sql);
			ArrayList<Group> list = new ArrayList<Group>();
			while (rs.next()) {
                System.out.println(11111);
				list.add(this.setGroup(rs));
			}
			con.close();
			System.out.println("Database: Group gotten done");
			return list;
		} catch (SQLException e) {
            e.printStackTrace();
			System.out.println("FAIL: Database: Group gotten failed!!!!!!");
			return null;
		}
	}

	public Group getGroupById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM meetinggroup WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Group group = this.setGroup(rs);
				con.close();
				System.out.println("Database: Group gotten done");
				return group;
			} else {
				con.close();
				System.out.println("FAIL: Database: Group gotten failed!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group gotten failed!!!!!!");
			return null;
		}
	}

	public Group getGroupByEmail(String email) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM meetinggroup WHERE email = " + email;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Group group = this.setGroup(rs);
				con.close();
				System.out.println("Database: Group gotten done");
				return group;
			} else {
				con.close();
				System.out.println("FAIL: Database: Group gotten failed!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group gotten failed!!!!!!");
			return null;
		}
	}

	public ArrayList<Group> getGroupByName(String name) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM meetinggroup WHERE name = " + name;
			rs = stmt.executeQuery(sql);
			ArrayList<Group> list = new ArrayList<Group>();
			while (rs.next()) {
				list.add(this.setGroup(rs));
			}
			con.close();
			System.out.println("Database: Group gotten done");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Group gotten failed!!!!!!");
			return null;
		}
	}

}
