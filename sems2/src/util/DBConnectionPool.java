package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

// JDBC URL, Driver, ID, PWD를 외부에서 주입
public class DBConnectionPool {
	
	String url;
	String driver;
	String username;
	String password;
	
	ArrayList<Connection> list = new ArrayList<Connection>();
	
	public void setUrl(String url) {
		this.url = url;
	}


	public void setDriver(String driver) {
		this.driver = driver;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Connection getConnection() throws Exception {
		// 커넥션 있으면 있는거 주고, 없으면 새로 만들어 준다.
		if (list.size() > 0) {
			return list.remove(0);
		} else {
			Class.forName("com.mysql.jdbc.Driver");

			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/studydb", username, password);
			
		}
	}
	
	
	public void returnConnection(Connection con) {
		// 이 커넥션이 서버와 연결이 유효하다면 list에 추가
		try {
	    if (!con.isClosed()) {
	    	list.add(con);
	    }
    } catch (SQLException e) {}
	}
	
	public void closeAll() {
		for (Connection con : list) {
			try { con.close(); } catch(Exception e) {}
		}
	}
	
}
