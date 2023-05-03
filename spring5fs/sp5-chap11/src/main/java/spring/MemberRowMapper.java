package spring;

import spring.Member;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;

import org.springframework.jdbc.core.RowMapper;

public class MemberRowMapper implements RowMapper<Member> {		// 임의 클래스를 사용하지 않고 직접 구현하여 재사용성 증가
	
	@Override	// 재정의
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {	// RowMapper<Member> 구현 -> mapRow() 메소드 재정의
		Member member =
				new Member(
						  rs.getString("EMAIL"),
						  rs.getString("PASSWORD"),
						  rs.getString("NAME"),
						  rs.getTimestamp("REGDATE").toLocalDateTime()
						  );
		member.setId(rs.getLong("ID"));
		return member;		// 행을 mapping한 결과로 생성한 Member 객체 반환
	}
	
}
