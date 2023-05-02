package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;

@Configuration
@EnableTransactionManagement	// @Transactional annotation이 붙은 메소드를 트랜잭션 범위에서 실행하는 기능 활성화
public class AppCtx {
	
	@Bean(destroyMethod = "close")	// close 메소드는 커넥션 풀에 보관된 Connection을 닫음
	public DataSource dataSource() {
		DataSource ds = new DataSource();	// ds 객체 생성
		
		ds.setDriverClassName("com.mysql.jdbc.Driver");		// JDBC 드라이버 클래스 지정
		ds.setUrl("jdbc:mysql://localhost/spring5fs?useSSL=false&characterEncoding=utf8");	// JDBC URL 지정, 데이터베이스와 테이블의 캐릭터셋 명시
		ds.setUsername("spring5");	// DB 사용자 계정
		ds.setPassword("spring5");	// DB 사용자 암호
		ds.setInitialSize(2);	// 커넥션 풀 초기화 시 생성할 초기 커넥션 개수 지정
		ds.setMaxActive(10);	// 커넥션 풀에서 가져올 수 있는 최대 커넥션 개수 지정
		ds.setTestWhileIdle(true);	// 유휴 커넥션 검사 활성화
		ds.setMinEvictableIdleTimeMillis(1000 * 60 * 3);	// 최소 유휴 시간 3분으로 설정
		ds.setTimeBetweenEvictionRunsMillis(1000 * 10);		// 유휴 커넥션 검사 주기 10초로 설정
		
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {	// 스프링이 제공하는 트랜잭션 매니저 인터페이스
		DataSourceTransactionManager tm = new DataSourceTransactionManager();	// JDBC가 사용하는 PlatformTransactionManager는 DataSourceTransactionManager
		tm.setDataSource(dataSource());	// 트랜잭션 매니저의 DataSource를 dataSource Bean으로 설정
		return tm;
	}
	
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao());
	}
	
	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao());
		return pwdSvc;
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}
	
	@Bean
	public MemberListPrinter listPrinter() {
		return new MemberListPrinter(memberDao(), memberPrinter());
	}
	
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		infoPrinter.setMemberDao(memberDao());
		infoPrinter.setPrinter(memberPrinter());
		return infoPrinter;
	}
	
}
