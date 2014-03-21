package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Room;

public class RoomStorage extends ServerStorage {

	public RoomStorage() {
		super();
		System.out
				.println("Database: Database will be connected by RoomStorage");
	}

	public Room insertRoom(Room r) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "INSERT INTO meetingroom(capacity, roomname) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, r.getCapacity());
			pstmt.setString(2, r.getRoomname());
			pstmt.executeUpdate();
			r.setId(this.getLastId());
			con.commit();
			con.close();
			System.out.println("Database: Room inserted done");
			return r;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateRoom(Room r) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "UPDATE meetingroom SET capacity = ?, roomname = ? WHERE id = "
					+ r.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, r.getCapacity());
			pstmt.setString(2, r.getRoomname());
			pstmt.executeUpdate();
			con.commit();
			con.close();
			System.out.println("Database: Room updated done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Invitation updated failed!!!!!!");
			return true;
		}
	}

	public boolean deleteRoomById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM meetinggroup WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: Room deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room deleted failed!!!!!!");
			return false;
		}
	}

	public ArrayList<Room> getAll() {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM meetingroom";
			rs = stmt.executeQuery(sql);
			ArrayList<Room> list = new ArrayList<Room>();
			while (rs.next()) {
				list.add(this.setRoom(con, rs));
			}
			con.close();
			System.out.println("Database: Room gotten done");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room gotten failed!!!!!!");
			return null;
		}
	}

	public Room getRoomById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM meetingroom WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Room room = this.setRoom(con, rs);
				con.close();
				System.out.println("Database: Room gotten done");
				return room;
			} else {
				con.close();
				System.out.println("FAIL: Database: Room gotten failed!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room gotten failed!!!!!!");
			return null;
		}
	}

}
