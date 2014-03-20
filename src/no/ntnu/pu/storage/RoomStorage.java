package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Room;

public class RoomStorage extends ServerStorage {

	public RoomStorage() {
		super();
		System.out.println("Database: Database connected by RoomStorage");
	}

	public Room insertRoom(Room r) {
		try {
			sql = "INSERT INTO meetingroom(capacity, roomname) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, r.getCapacity());
			pstmt.setString(2, r.getRoomname());
			pstmt.executeUpdate();
			r.setId(this.getLastId());
			con.commit();
			System.out.println("Database: Room inserted done");
			return r;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateRoom(Room r) {
		try {
			sql = "UPDATE meetingroom SET capacity = ?, roomname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, r.getCapacity());
			pstmt.setString(2, r.getRoomname());
			pstmt.executeUpdate();
			con.commit();
			System.out.println("Database: Room updated done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Invitation updated failed!!!!!!");
			return true;
		}
	}

	public boolean deleteRoomById(int id) {
		try {
			sql = "DELETE FROM meetinggroup WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			System.out.println("Database: Room deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room deleted failed!!!!!!");
			return false;
		}
	}

	public ArrayList<Room> getAll() {
		try {
			sql = "SELECT * FROM meetingroom";
			rs = stmt.executeQuery(sql);
			ArrayList<Room> list = new ArrayList<Room>();
			while (rs.next()) {
				list.add(this.setRoom(rs));
			}
			System.out.println("Database: Room gotten done");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room deleted failed!!!!!!");
			return null;
		}
	}

	public Room getRoomById(int id) {
		try {
			sql = "SELECT * FROM meetingroom WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("Database: Room gotten done");
				return this.setRoom(rs);
			} else {
				System.out.println("FAIL: Database: Room deleted failed!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Room deleted failed!!!!!!");
			return null;
		}
	}

}
