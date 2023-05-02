package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {
	
	private MemberDao memberDao;
	
	@Transactional	// 메소드를 트랜잭션 범위에서 실행: 실패 시 rollback, 성공 시 commit
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();
		
		member.changePassword(oldPwd, newPwd);
		
		memberDao.update(member);
	}
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
