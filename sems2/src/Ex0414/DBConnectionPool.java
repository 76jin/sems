package basic.exam06.jdbc.up7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Pooling 기법
 * 	- 적은 자원을 효율적으로 사용
 * 	- 미리 자원을 준비한 후 필요한 곳에 빌려줌
 * 	- 자원을 사용하고 난 다음에는 반납함.
 * 
 * DB Connection 풀링
 * 	- Connection이 필요할 때 생성 후 대여
 * 	- 사용 후 닫지 않고 반납함.
 * 	- 기존에 커넥션이 있으면 기존 커넥션을 대여해 줌.
 * 		=> DBMS와 연결하는 시간을 줄일 수 있다.
 */

public class DBConnectionPool {
	
	ArrayList<Connection> list = new ArrayList<Connection>();
	
	public Connection getConnection() throws Exception {
		// 커넥션 있으면 있는거 주고, 없으면 새로 만들어 준다.
		if (list.size() > 0) {
			return list.remove(0);
		} else {
			Class.forName("com.mysql.jdbc.Driver");

			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/studydb", "study", "study");
			
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
