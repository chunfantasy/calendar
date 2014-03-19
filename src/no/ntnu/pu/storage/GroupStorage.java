package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Person;

public class GroupStorage extends ServerStorage {

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

	public boolean deleteGroupByEmail(String email) {
		try {
			sql = "DELETE FROM meetinggroup WHERE email = '" + email + "'";
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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

	public ArrayList<Group> getGroupByName(String name) {
		try {
			sql = "SELECT * FROM meetinggroup WHERE name = " + name;
			rs = stmt.executeQuery(sql);
			ArrayList<Group> list = new ArrayList<Group>();
			while (rs.next()) {
				list.add(this.setGroup(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
