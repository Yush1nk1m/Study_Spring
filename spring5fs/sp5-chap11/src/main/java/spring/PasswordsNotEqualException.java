package spring;

public class PasswordsNotEqualException extends RuntimeException {
	
	public PasswordsNotEqualException() {
		super("Password and confirm password are not equal.");
	}
}
