package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import spring.Member;
import spring.MemberDao;

public class MainForMemberDao {
	
	private static MemberDao memberDao;		// 정적 필드
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =	// AppCtx.java의 설정을 사용하여 스프링 컨테이너 생성
				new AnnotationConfigApplicationContext(AppCtx.class);
		
		memberDao = ctx.getBean(MemberDao.class);	// 컨테이너로부터 MemberDao 클래스의 빈을 구해서 정적 필드에 할당
		
		selectAll();
		updateMember();
		insertMember();
		
		ctx.close();
	}
	
	private static void selectAll() {
		System.out.println("----- selectAll");
		int total = memberDao.count();
		System.out.println("전체 데이터: " + total);
		
		List<Member> members = memberDao.selectAll();	// memberDao#selectAll() 메소드를 이용하여 전체 Member 데이터를 구한 뒤 콘솔에 차례대로 출력
		for (Member m : members) {
			System.out.println(m.getId() + ":" + m.getEmail() + ":" + m.getName());
		}
	}
	
	private static void updateMember() {
		System.out.println("----- updateMember");
		
		Member member = memberDao.selectByEmail("madvirus@madvirus.net");	// EMAIL 열 값이 madvirus@madvirus.net인 Member 객체를 구함
		
		String oldPw = member.getPassword();
		String newPw = Double.toHexString(Math.random());	// 임의의 새로운 암호 생성
		member.changePassword(oldPw, newPw);				// 새로운 암호로 변경
		
		memberDao.update(member);	// 변경 내용을 DB에 반영
		
		System.out.println("암호 변경: " + oldPw + " > " + newPw);
	}
	
	private static DateTimeFormatter formatter =
			DateTimeFormatter.ofPattern("MMddHHmmss");
	
	private static void insertMember() {
		System.out.println("----- insertMember");
		
		String prefix = formatter.format(LocalDateTime.now());	// 현재 시각으로 값 생성
		Member member = new Member(prefix + "@test.com", prefix, prefix, LocalDateTime.now());	// DB에 새로이 추가할 Member 객체 생성
		
		memberDao.insert(member);	// DB에 새로운 데이터 추가
		
		System.out.println(member.getId() + " 데이터 추가");	// 새로 생성된 키 값 출력(MemberDao#insert()에서 KeyHolder와 Member#setId()를 이용하여 키 값을 저장함)
	}
}
