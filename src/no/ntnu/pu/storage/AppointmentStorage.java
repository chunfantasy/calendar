package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

public class AppointmentStorage extends ServerStorage {

	public Appointment insertAppointment(Appointment a) {
		try {
			sql = "INSERT INTO appointment(title, starttime, endtime, address, description, meetingroomid) "
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a.getTitle());
			pstmt.setTimestamp(2, new Timestamp(a.getStartTime().getTime()));
			pstmt.setTimestamp(3, new Timestamp(a.getEndTime().getTime()));
			pstmt.setString(4, a.getAddress());
			pstmt.setString(5, a.getDescription());
			pstmt.setInt(6, a.getMeetingRoom().getId());
			pstmt.executeUpdate();
			a.setId(this.getLastId());

			if (!a.getParticipants().isEmpty()) {
				for (Participant participant : a.getParticipants()) {
					if (participant instanceof Person) {
						sql = "INSERT INTO appointment_participant (appointmentid, personid) "
								+ "VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, ((Person) participant).getId());
						pstmt.executeUpdate();
					}

					else if (participant instanceof Group) {
						sql = "INSERT INTO appointment_participant (appointmentid, meetinggroupid) "
								+ "VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, ((Group) participant).getId());
						pstmt.executeUpdate();
					}

					else
						continue;
				}
			}
			con.commit();
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteAppointmentById(int id) {
		try {
			sql = "DELETE FROM appointment WHERE id = " + id;
			stmt.execute(sql);
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateAppointment(Appointment a) {
		try {
			sql = "UPDATE appointment SET title = ?, starttime = ?, endtime = ?, address = ?, description = ?, meetingroomid = ? "
					+ "WHERE id = " + a.getId();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a.getTitle());
			pstmt.setTimestamp(2, new Timestamp(a.getStartTime().getTime()));
			pstmt.setTimestamp(3, new Timestamp(a.getEndTime().getTime()));
			pstmt.setString(4, a.getAddress());
			pstmt.setString(5, a.getDescription());
			pstmt.setInt(6, a.getMeetingRoom().getId());
			pstmt.executeUpdate();

			try {
				sql = "SELECT * FROM appointment_participant WHERE appointmentid = "
						+ a.getId();
				rs = stmt.executeQuery(sql);
				ArrayList<Integer> listOldPerson = new ArrayList<Integer>();
				ArrayList<Integer> listOldGroup = new ArrayList<Integer>();
				while (rs.next()) {
					if (rs.getInt("personid") != 0)
						listOldPerson.add(rs.getInt("personid"));
					if (rs.getInt("meetinggroupid") != 0)
						listOldGroup.add(rs.getInt("meetinggroupid"));
				}

				ArrayList<Integer> listNewPerson = new ArrayList<Integer>();
				ArrayList<Integer> listNewGroup = new ArrayList<Integer>();
				for (Participant p : a.getParticipants()) {
					if (p instanceof Person)
						listNewPerson.add(((Person) p).getId());
					if (p instanceof Group)
						listNewGroup.add(((Group) p).getId());
				}

				ArrayList<Integer> list = new ArrayList<Integer>();
				for (int id : listOldPerson) {
					if (!listNewPerson.contains(id)) {
						sql = "DELETE FROM appointment_participant WHERE appointmentid = ? AND personid = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
						list.add(id);
					}
				}

				for (int id : list) {
					listOldPerson.remove((Integer.valueOf(id)));
				}

				for (int id : listNewPerson) {
					if (!listOldPerson.contains(id)) {
						sql = "INSERT INTO appointment_participant (appointmentid, personid) VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
					}
				}

				list.clear();
				for (int id : listOldGroup) {
					if (!listNewGroup.contains(id)) {
						sql = "DELETE FROM appointment_participant WHERE appointmentid = ? AND meetinggroupid = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
						list.add(id);
					}
				}

				for (int id : list) {
					listOldPerson.remove((Integer.valueOf(id)));
				}

				for (int id : listNewGroup) {
					if (!listOldGroup.contains(id)) {
						sql = "INSERT INTO appointment_participant (appointmentid, meetinggroupid) VALUES(?, ?)";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, a.getId());
						pstmt.setInt(2, id);
						pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime) {
		try {
			sql = "SELECT * FROM appointment WHERE starttime >= '"
					+ new Timestamp(startTime.getTime()) + "' AND endtime <= '"
					+ new Timestamp(endTime.getTime()) + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			ArrayList<Appointment> list = new ArrayList<Appointment>();
			while (rs.next()) {
				list.add(this.setAppointment(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Appointment> getAppointmentByParticipant(Participant p) {
		try {
			if (p instanceof Person)
				sql = "SELECT * FROM appointment_participant WHERE personid = "
						+ ((Person) p).getId();
			else if (p instanceof Group)
				sql = "SELECT * FROM appointment_participant WHERE meetinggroupid = "
						+ ((Group) p).getId();
			else
				return null;
			rs = stmt.executeQuery(sql);
			ArrayList<Appointment> list = new ArrayList<Appointment>();
			while (rs.next()) {
				list.add(this.setAppointment(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
