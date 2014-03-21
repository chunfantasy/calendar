package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

public class AppointmentStorage extends ServerStorage {

    public AppointmentStorage() {
        super();
        System.out
                .println("Database: Database will be connected by AppointmentStorage");
    }

    public Appointment insertAppointment(Appointment a) {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            sql = "INSERT INTO appointment(title, starttime, endtime, address, description, creatorid) "
                    + "VALUES(?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, a.getTitle());
            pstmt.setTimestamp(2, new Timestamp(a.getStartTime().getTime()));
            pstmt.setTimestamp(3, new Timestamp(a.getEndTime().getTime()));
            pstmt.setString(4, a.getAddress());
            pstmt.setString(5, a.getDescription());
            pstmt.setInt(6, a.getCreator().getId());
            pstmt.executeUpdate();
            a.setId(this.getLastId());

            if (a.getMeetingRoom()!= null){
                sql = "UPDATE appointment SET meetingroomid = ? WHERE id = " + a.getId();
                pstmt.setInt(1, a.getMeetingRoom().getId());
                pstmt.executeUpdate();
            }

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
            con.close();
            System.out.println("Database: Appointment inserted done");
            return a;
        } catch (SQLException e) {
            System.out
                    .println("FAIL: Database: Appointment inserted failed!!!!!!");
            return null;
        }
    }

    public boolean updateAppointment(Appointment a) {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            sql = "UPDATE appointment SET title = ?, starttime = ?, endtime = ?, address = ?, description = ? "
                    + "WHERE id = " + a.getId();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, a.getTitle());
            pstmt.setTimestamp(2, new Timestamp(a.getStartTime().getTime()));
            pstmt.setTimestamp(3, new Timestamp(a.getEndTime().getTime()));
            pstmt.setString(4, a.getAddress());
            pstmt.setString(5, a.getDescription());
            pstmt.executeUpdate();

            if (a.getMeetingRoom() != null){
                sql = "UPDATE appointment SET meetingroomid = ? "
                        + "WHERE id = " + a.getId();
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, a.getMeetingRoom().getId());
            }
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
                System.out
                        .println("FAIL: Database: Appointment updated failed!!!!!!");
                return false;
            }
            System.out.println("Database: Appointment updated done");
            con.commit();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out
                    .println("FAIL: Database: Appointment updated failed!!!!!!");
            return false;
        }
    }

    public boolean deleteAppointmentById(int id) {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            sql = "DELETE FROM appointment WHERE id = " + id;
            stmt.execute(sql);
            con.commit();
            con.close();
            System.out.println("Database: Appointment deleted done");
            return true;
        } catch (SQLException e) {
            System.out
                    .println("FAIL: Database: Appointment deleted failed!!!!!!");
            return false;
        }
    }

    public ArrayList<Appointment> getAll() {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            sql = "SELECT * FROM appointment";
            rs = stmt.executeQuery(sql);
            ArrayList<Appointment> list = new ArrayList<Appointment>();
            while (rs.next()) {
                list.add(this.setAppointment(con, rs));
            }
            con.close();
            System.out.println("Database: Appointment gotten done");
            return list;
        } catch (SQLException e) {
            System.out
                    .println("FAIL: Database: Appointment gotten failed!!!!!!");
            return null;
        }
    }

    public Appointment getAppointmentById(int id) {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            sql = "SELECT * FROM appointment WHERE id = " + id;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Appointment appointment = this.setAppointment(con, rs);
                con.close();
                System.out.println("Database: Appointment gotten done");
                return appointment;
            } else
                con.close();
            System.out
                    .println("FAIL: Database: Appointment gotten failed!!!!!!");
            return null;
        } catch (SQLException e) {
            System.out
                    .println("FAIL: Database: Appointment gotten failed!!!!!!");
            return null;
        }
    }

    public ArrayList<Appointment> getAppointmentByTime(Date startTime,
                                                       Date endTime) {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            sql = "SELECT * FROM appointment WHERE starttime >= '"
                    + new Timestamp(startTime.getTime()) + "' AND endtime <= '"
                    + new Timestamp(endTime.getTime()) + "'";
            rs = stmt.executeQuery(sql);
            ArrayList<Appointment> list = new ArrayList<Appointment>();
            while (rs.next()) {
                list.add(this.setAppointment(con, rs));
            }
            con.close();
            System.out.println("Database: Appointment gotten done");
            return list;
        } catch (SQLException e) {
            System.out
                    .println("FAIL: Database: Appointment gotten failed!!!!!!");
            return null;
        }
    }

    public ArrayList<Appointment> getAppointmentByParticipant(Participant p) {
        try {
            Connection con = this.connect();
            stmt = con.createStatement();
            if (p instanceof Person)
                sql = "SELECT * FROM appointment_participant WHERE personid = "
                        + ((Person) p).getId();
            else if (p instanceof Group)
                sql = "SELECT * FROM appointment_participant WHERE meetinggroupid = "
                        + ((Group) p).getId();
            else {
                System.out
                        .println("FAIL: Database: Appointment gotten failed!!!!!!");
                return null;
            }
            rs = stmt.executeQuery(sql);
            ArrayList<Integer> listId = new ArrayList<Integer>();
            while (rs.next()) {
                listId.add(rs.getInt("appointmentid"));
            }
            ArrayList<Appointment> listAppointment = new ArrayList<Appointment>();
            for (int id : listId) {
                sql = "SELECT * FROM appointment WHERE id = " + id;
                rs = stmt.executeQuery(sql);
                if (rs.next())
                    listAppointment.add(this.setAppointment(con, rs));
            }
            con.close();
            System.out.println("Database: Appointment gotten done");
            return listAppointment;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("FAIL: Database: Appointment gotten failed!!!!!!");
            return null;
        }
    }

}