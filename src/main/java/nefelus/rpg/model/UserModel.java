package nefelus.rpg.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_user")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2, message = "Mínimo de caracteres é 2")
	private String user;
	
	@Email
	private String email;
	
	@Size(min = 6, message = "Mínimo de caracteres é 6")
	private String password;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private CharacterModel characterModel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public CharacterModel getCharacterModel() {
		return characterModel;
	}

	public void setCharacterModel(CharacterModel characterModel) {
		this.characterModel = characterModel;
	}	
}
