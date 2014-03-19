package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Room;

public class RoomStorage extends ServerStorage {

	public Room insertRoom(Room r) {
		try {
			sql = "INSERT INTO meetingroom(capacity, roomname) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, r.getCapacity());
			pstmt.setString(2, r.getRoomname());
			pstmt.executeUpdate();
			r.setId(this.getLastId());
			con.commit();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

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
	
	public ArrayList<Room> getAll() {
		try {
			sql = "SELECT * FROM meetingroom";
			rs = stmt.executeQuery(sql);
			ArrayList<Room> list = new ArrayList<Room>();
			while (rs.next()) {
				list.add(this.setRoom(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
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
