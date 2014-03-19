package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.DeclineNotification;

public class DeclineNotificationStorage extends ServerStorage{
	
	public DeclineNotificationStorage() {
		super();
		System.out
				.println("Database: Database connected by DeclineNotificationStorage");
	}
	
	public DeclineNotification insertDeclineNotification(DeclineNotification d) {
		try {
			sql = "INSERT INTO declinenotification(appointmentid, recipientid, declinerid) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, d.getAppointment().getId());
			pstmt.setInt(2, d.getRecipient().getId());
			pstmt.setInt(3, d.getDecliner().getId());
			pstmt.executeUpdate();
			d.setId(this.getLastId());
			con.commit();
			System.out.println("Database: DeclineNotification inserted");
			return d;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateDeclineNotification(DeclineNotification d) {
		try {
			sql = "UPDATE declinenotification SET appointmentid = ?, recipientid =?, declinerid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, d.getAppointment().getId());
			pstmt.setInt(2, d.getRecipient().getId());
			pstmt.setInt(3, d.getDecliner().getId());
			pstmt.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public boolean deleteDeclineNotificationById(int id) {
		try {
			sql = "DELETE FROM declinenotification WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<DeclineNotification> getAll() {
		try {
			sql = "SELECT * FROM declinenotification";
			rs = stmt.executeQuery(sql);
			ArrayList<DeclineNotification> list = new ArrayList<DeclineNotification>();
			while (rs.next()) {
				list.add(this.setDeclineNotification(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public DeclineNotification getDeclineNotificationById(int id) {
		try {
			sql = "SELECT * FROM declinenotification WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return this.setDeclineNotification(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
