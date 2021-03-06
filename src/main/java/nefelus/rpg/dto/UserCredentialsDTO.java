package nefelus.rpg.dto;

public class UserCredentialsDTO {
	
	private String basicToken;
	private Long id;
	private String email;
	private String user;
	
	public UserCredentialsDTO(String basicToken, Long id, String email, String user) {
		super();
		this.basicToken = basicToken;
		this.id = id;
		this.email = email;
		this.user = user;
	}

	public String getBasicToken() {
		return basicToken;
	}

	public void setBasicToken(String basicToken) {
		this.basicToken = basicToken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}	
}
