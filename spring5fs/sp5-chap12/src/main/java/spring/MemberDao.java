package spring;

import spring.MemberRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;	// DB에 쿼리를 요청하고 결과를 수신하는 코드를 간결하게 해줌
	
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);	// JdbcTemplate 객체 생성을 위해 생성자에 DateSource 객체를 전달해야 함
	}
	
	public Member selectByEmail(String email) {
		List<Member> results =
				jdbcTemplate.query(
								  "select * from MEMBER where EMAIL = ?",
								  new MemberRowMapper(),
								  email		// 인덱스 파라미터 ('?')에 들어갈 값, 가변 인자로 '?'의 개수만큼 존재
								  );
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	public void insert(final Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		// 자동 생성된 키 값을 구해주는 KeyHolder 구현 클래스
		
		jdbcTemplate.update(	// update() 메소드는 PreparedStatementCreator 객체와 KeyHolder 객체를 파라미터로 가짐
						   new PreparedStatementCreator() {		// PreparedStatementCreator 임의 클래스를 이용
							   @Override
							   public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								   PreparedStatement pstmt =	// 직접 인덱스 파라미터의 값을 설정해야 할 경우 PreparedStatement 객체 사용, 임의 클래스를 이용하여 객체 직접 생성
										   con.prepareStatement(
												   			   "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " + "values (?, ?, ?, ?)",
												   			   new String[] {"ID"}	// 자동 생성되는 키의 열 목록 지정, MEMBER 테이블은 ID 열이 자동 증가 키
												   			   );
								   pstmt.setString(1, member.getEmail());		// 첫 번째 인덱스 파라미터의 값 설정
								   pstmt.setString(2, member.getPassword());	// 두 번째 인덱스 파라미터의 값 설정
								   pstmt.setString(3, member.getName());		// 세 번째 인덱스 파라미터의 값 설정
								   pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));	// 네 번째 인덱스 파라미터의 값 설정
								   
								   return pstmt;
							   }
						   },
						   keyHolder	// JdbcTemplate#update() 메소드의 두 번째 파라미터로 앞서 생성한 KeyHolder 전달
						   );
		Number keyValue = keyHolder.getKey();	// KeyHolder에 보관된 키 값을 getKey() 메소드로 구함, java.lang.Number를 반환
		member.setId(keyValue.longValue());		// java.lang.Number 객체를 intValue(), longValue() 등의 메소드로 변환
	}
	
	public void update(Member member) {
		jdbcTemplate.update(	// INSERT, UPDATE, DELETE 쿼리는 update() 메소드 사용
						   "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
						   member.getName(), member.getPassword(), member.getEmail()
						   );
	}
	
	public List<Member> selectAll() {
		List<Member> results =
				jdbcTemplate.query(
								  "select * from MEMBER",
								  new MemberRowMapper()
								  );
		return results;
	}
	
	public int count() {
		Integer count =
				jdbcTemplate.queryForObject(	// 결과가 1행인 경우 사용할 수 있는 queryForObject() 메소드
										   "select count(*) from MEMBER",	// 쿼리
										   Integer.class					// 열을 읽어올 때 사용할 타입
										   );
		return count;
	}
	
}
