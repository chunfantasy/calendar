package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.pu.model.Invitation;
import no.ntnu.pu.model.Person;

public class InvitationStorage extends ServerStorage {

	public InvitationStorage() {
		super();
		System.out
				.println("Database: Database will be connected by IvitationStorage");
	}

	public Invitation insertInvitation(Invitation i) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "INSERT INTO invitation(appointmentid, recipientid, senderid) VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, i.getAppointment().getId());
			pstmt.setInt(2, i.getRecipient().getId());
			pstmt.setInt(3, i.getSender().getId());
			pstmt.executeUpdate();
			i.setId(this.getLastId());
			con.commit();
			con.close();
			System.out.println("Database: Invitation inserted done");
			return i;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: Invitation inserted failed!!!!!!");
			return null;
		}
	}

	public boolean updateInvitation(Invitation i) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "UPDATE invitation SET appointmentid = ?, recipientid =?, senderid = ? WEHRE id = "
					+ i.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, i.getAppointment().getId());
			pstmt.setInt(2, i.getRecipient().getId());
			pstmt.setInt(3, i.getSender().getId());
			pstmt.executeUpdate();
			con.commit();
			con.close();
			System.out.println("Database: Invitation updated");
			return true;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: Invitation updated failed!!!!!!");
			return true;
		}
	}

	public boolean deleteInvitationById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "DELETE FROM invitation WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			con.close();
			System.out.println("Database: Invitation deleted");
			return true;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: Invitation deleted by id failed!!!!!!");
			return false;
		}
	}

	public ArrayList<Invitation> getAll() {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM invitation";
			rs = stmt.executeQuery(sql);
			ArrayList<Invitation> list = new ArrayList<Invitation>();
			while (rs.next()) {
				list.add(this.setInvitation(rs));
			}
			con.close();
			System.out.println("Database: Invitation gotten done");
			return list;
		} catch (SQLException e) {
			System.out
					.println("FAIL: Database: Invitation gotten falied!!!!!!");
			return null;
		}
	}

	public Invitation getInvitationById(int id) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM Invitation WHERE id = " + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Invitation invitation = this.setInvitation(rs);
				con.close();
				System.out.println("Database: Invitation gotten done");
				return invitation;
			} else {
				con.close();
				System.out
						.println("FAIL: Database: Invitation gotten falied!!!!!!");
				return null;
			}
		} catch (SQLException e) {
			System.out
					.println("Database: Invitation gotten by id failed!!!!!!");
			return null;
		}
	}

	public ArrayList<Invitation> getInvitationByRecipient(Person p) {
		try {
			Connection con = this.connect();
			stmt = con.createStatement();
			sql = "SELECT * FROM invitation WHERE recipientid = " + p.getId();
			rs = stmt.executeQuery(sql);
			ArrayList<Invitation> list = new ArrayList<Invitation>();
			while (rs.next()) {
				list.add(this.setInvitation(rs));
			}
			con.close();
			System.out.println("Database: Invitation gotten done");
			return list;
		} catch (SQLException e) {
			System.out.println("FAIL: Database: Invitation gotten fail!!!!!!");
			return null;
		}
	}
}
