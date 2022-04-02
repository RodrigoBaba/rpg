package nefelus.rpg.dto;

import javax.validation.constraints.NotBlank;

public class UserLoginDTO {
	
	private @NotBlank String userLogin;
	
	private @NotBlank String password;

		
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
