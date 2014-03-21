package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.ChangeNotification;
import no.ntnu.pu.model.Person;

public class ChangeNotificationStorage extends ServerStorage {

	public ChangeNotificationStorage() {
		super();
		System.out
				.println("Database: Database will be connected by ChangeNotificationStorage");
	}

	public ChangeNotification insertChangeNotification(ChangeNotification c) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "INSERT INTO changenotification(appointmentid, recipientid, changedproperties) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c.getAppointment().getId());
			pstmt.setInt(2, c.getRecipient().getId());
			pstmt.setString(3, c.getChangedProperties().toString());
			pstmt.executeUpdate();
			c.setId(this.getLastId());
			con.commit();
			con.close();
			System.out.println("Database: ChangeNotification inserted done");
			return c;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: ChangeNotification inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateChangeNotification(ChangeNotification c) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "UPDATE changenotification SET appointmentid = ?, recipientid =?, changedproperties = ? WEHRE id = "
					+ c.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c.getAppointment().getId());
			pstmt.setInt(2, c.getRecipient().getId());
			pstmt.setString(3, c.getChangedProperties().toString());
			pstmt.executeUpdate();
			con.commit();
			con.close();
			System.out.println("Database: ChangeNotification updated done");
			return true;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: ChangeNotification updated failed!!!!!!");
			return true;
		}
	}

	public boolean deleteChangeNotificationById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM changenotification WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: ChangeNotification deleted done");
			return true;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: ChangeNotification deleted failed!!!!!!");
			return false;
		}
	}

	public ArrayList<ChangeNotification> getAll() {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM changenotification";
			rs = stmt.executeQuery(sql);
			ArrayList<ChangeNotification> list = new ArrayList<ChangeNotification>();
			while (rs.next()) {
				list.add(this.setChangeNotification(con, rs));
			}
			con.close();
			System.out.println("Database: ChangeNotification gotten done");
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ChangeNotification getChangeNotificationById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM changenotification WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ChangeNotification changeNotification = this
						.setChangeNotification(con, rs);
				con.close();
				System.out.println("Database: ChangeNotification gotten done");
				return changeNotification;
			} else {
				con.close();
				System.out
						.println("FAIL: Database: ChangeNotification gotten fail!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: ChangeNotification gotten fail!!!!!!");
			return null;
		}
	}

	public ArrayList<ChangeNotification> getChangeNotificationByRecipient(
			Person p) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM changenotification WHERE recipientid = "
					+ p.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<ChangeNotification> list = new ArrayList<ChangeNotification>();
			while (rs.next()) {
				list.add(this.setChangeNotification(con, rs));
			}
			con.close();
			System.out.println("Database: ChangeNotification gotten");
			return list;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: ChangeNotification gotten fail!!!!!!");
			return null;
		}
	}
}
