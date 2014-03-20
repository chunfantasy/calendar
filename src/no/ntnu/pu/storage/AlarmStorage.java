package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import no.ntnu.pu.model.Alarm;

public class AlarmStorage extends ServerStorage {

	public AlarmStorage(){
		super();
		System.out.println("Database: Database connected by AlarmStorage");
	}
	
	public Alarm insertAlarm(Alarm a) {
		try {
			sql = "INSERT INTO alarm(appointmentid, recipientid, date) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a.getAppointment().getId());
			pstmt.setInt(2, a.getRecipient().getId());
			pstmt.setTimestamp(3, new Timestamp(a.getTime().getTime()));
			pstmt.executeUpdate();
			a.setId(this.getLastId());
			con.commit();
			System.out.println("Database: Alarm inserted done");
			return a;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Alarm inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateAlarm(Alarm a) {
		try {
			sql = "UPDATE alarm SET appointmentid = ?, recipientid =?, date = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a.getAppointment().getId());
			pstmt.setInt(2, a.getRecipient().getId());
			pstmt.setTimestamp(3, new Timestamp(a.getTime().getTime()));
			pstmt.executeUpdate();
			con.commit();
			System.out.println("Database: Alarm updated done");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public boolean deleteAlarmById(int id) {
		try {
			sql = "DELETE FROM alarm WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Alarm> getAll() {
		try {
			sql = "SELECT * FROM alarm";
			rs = stmt.executeQuery(sql);
			ArrayList<Alarm> list = new ArrayList<Alarm>();
			while (rs.next()) {
				list.add(this.setAlarm(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Alarm getAlarmById(int id) {
		try {
			sql = "SELECT * FROM alarm WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setAlarm(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
