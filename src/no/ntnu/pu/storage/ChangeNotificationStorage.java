package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.ChangeNotification;

public class ChangeNotificationStorage extends ServerStorage {

	public ChangeNotificationStorage() {
		super();
		System.out
				.println("Database: Database connected by ChangeNotificationStorage");
	}

	public ChangeNotification insertChangeNotification(ChangeNotification c) {
		try {
			sql = "INSERT INTO changenotification(appointmentid, recipientid, changedproperties) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c.getAppointment().getId());
			pstmt.setInt(2, c.getRecipient().getId());
			pstmt.setString(3, c.getChangedProperties().toString());
			pstmt.executeUpdate();
			c.setId(this.getLastId());
			con.commit();
			System.out.println("Database: ChangeNotification inserted done");
			return c;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: ChangeNotification inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateChangeNotification(ChangeNotification c) {
		try {
			sql = "UPDATE changenotification SET appointmentid = ?, recipientid =?, changedproperties = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c.getAppointment().getId());
			pstmt.setInt(2, c.getRecipient().getId());
			pstmt.setString(3, c.getChangedProperties().toString());
			pstmt.executeUpdate();
			con.commit();
			System.out.println("Database: ChangeNotification updated done");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public boolean deleteChangeNotificationById(int id) {
		try {
			sql = "DELETE FROM changenotification WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<ChangeNotification> getAll() {
		try {
			sql = "SELECT * FROM changenotification";
			rs = stmt.executeQuery(sql);
			ArrayList<ChangeNotification> list = new ArrayList<ChangeNotification>();
			while (rs.next()) {
				list.add(this.setChangeNotification(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ChangeNotification getChangeNotificationById(int id) {
		try {
			sql = "SELECT * FROM changenotification WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setChangeNotification(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
