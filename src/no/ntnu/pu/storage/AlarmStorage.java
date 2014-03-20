package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import no.ntnu.pu.model.Alarm;
import no.ntnu.pu.model.Person;

public class AlarmStorage extends ServerStorage {

	public AlarmStorage() {
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
			System.out.println("FAIL: Database: Alarm updated failed!!!!!!");
			return true;
		}
	}

	public boolean deleteAlarmById(int id) {
		try {
			sql = "DELETE FROM alarm WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			System.out.println("Database: Alarm deleted done");
			return true;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Alarm deleted failed!!!!!!");
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
			System.out.println("Database: Alarm gotten");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Alarm gotten fail!!!!!!");
			return null;
		}
	}

	public Alarm getAlarmById(int id) {
		try {
			sql = "SELECT * FROM alarm WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("Database: Alarm gotten");
				return this.setAlarm(rs);
			} else
				System.out.println("FAIL: Database: Alarm gotten fail!!!!!!");
			return null;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Alarm gotten fail!!!!!!");
			return null;
		}
	}

	public ArrayList<Alarm> getAlarmByRecipient(Person p) {
		try {
			sql = "SELECT * FROM alarm WHERE recipientid = " + p.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<Alarm> list = new ArrayList<Alarm>();
			while (rs.next()) {
				list.add(this.setAlarm(rs));
			}
			System.out.println("Database: Alarm gotten");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Alarm gotten fail!!!!!!");
			return null;
		}
	}
}
