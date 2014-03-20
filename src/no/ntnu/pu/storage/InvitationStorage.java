package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Invitation;

public class InvitationStorage extends ServerStorage{
	
	public InvitationStorage() {
		super();
		System.out
				.println("Database: Database connected by IvitationStorage");
	}
	
	public Invitation insertInvitation(Invitation i) {
		try {
			sql = "INSERT INTO invitation(appointmentid, recipientid, senderid) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, i.getAppointment().getId());
			pstmt.setInt(2, i.getRecipient().getId());
			pstmt.setInt(3, i.getSender().getId());
			pstmt.executeUpdate();
			i.setId(this.getLastId());
			con.commit();
			System.out.println("Database: Invitation inserted done");
			return i;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Invitation inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateInvitation(Invitation i) {
		try {
			sql = "UPDATE invitation SET appointmentid = ?, recipientid =?, senderid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, i.getAppointment().getId());
			pstmt.setInt(2, i.getRecipient().getId());
			pstmt.setInt(3, i.getSender().getId());
			pstmt.executeUpdate();
			con.commit();
			System.out.println("Database: Invitation updated");
			return true;
		} catch (SQLException e) {
			System.out.println("Database: Invitation updated failed!!!!!!");
			return true;
		}
	}

	public boolean deleteInvitationById(int id) {
		try {
			sql = "DELETE FROM invitation WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			System.out.println("Database: Invitation deleted by id");
			return true;
		} catch (SQLException e) {
			System.out.println("Database: Invitation deleted by id failed!!!!!!");
			return false;
		}
	}

	public ArrayList<Invitation> getAll() {
		try {
			sql = "SELECT * FROM invitation";
			rs = stmt.executeQuery(sql);
			ArrayList<Invitation> list = new ArrayList<Invitation>();
			while (rs.next()) {
				list.add(this.setInvitation(rs));
			}
			System.out.println("Database: Invitation all gotten");
			return list;
		} catch (SQLException e) {
			System.out.println("Database: Invitation all gotten falied!!!!!!");
			return null;
		}
	}

	public Invitation getInvitationById(int id) {
		try {
			sql = "SELECT * FROM Invitation WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("Database: Invitation gotten by id");
				return this.setInvitation(rs);
			} else
				System.out.println("Database: Invitation gotten by id failed!!!!!!");
				return null;
		} catch (SQLException e) {
			System.out.println("Database: Invitation gotten by id failed!!!!!!");
			return null;
		}
	}
}
