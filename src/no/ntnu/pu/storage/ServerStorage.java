package no.ntnu.pu.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

import no.ntnu.pu.model.Person;

public class ServerStorage extends Storage {
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;

	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test",
				"testUser", "test");
		con.setAutoCommit(false);
	}

	public boolean create(Object o) throws SQLException {
		if (o instanceof Person) {
			sql = "INSERT INTO person VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ((Person) o).getEmail());
			pstmt.setString(2, ((Person) o).getName());
			
		} else
			return false;
		
		pstmt.executeUpdate();
		con.commit();
		con.close();
		return true;
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ServerStorage serverStorage = new ServerStorage();
		serverStorage.connect();
		Person p = new Person();
		p.setEmail("email");
		p.setName("name");
		serverStorage.create(p);

	}

}
