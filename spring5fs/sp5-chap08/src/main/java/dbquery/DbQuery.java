package dbquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DbQuery {
	
	private DataSource dataSource;	// DB 연결을 관리하는 인터페이스
	
	public DbQuery(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public int count() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();	// 커넥션 풀에서 커넥션을 구함
			try (Statement stmt = conn.createStatement();
				 ResultSet rs = stmt.executeQuery("select count(*) from MEMBER")) {
				rs.next();	// 결과에서 현재 커서 위치를 다음 행으로 이동
				return rs.getInt(1);	// 현재 커서 위치에서 첫 번째 열의 값을 Int 형으로 가져옴
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();	// 커넥션 풀에 커넥션 반환
				} catch (SQLException e) {
				}
		}
	}
}
