package nefelus.rpg.service;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import nefelus.rpg.dto.UserCredentialsDTO;
import nefelus.rpg.dto.UserLoginDTO;
import nefelus.rpg.model.User;
import nefelus.rpg.repository.UserRepository;

@Service
public class UserService {	
	
	private UserCredentialsDTO credentialsDTO;
	@Autowired
	private UserRepository repository;
	
	public ResponseEntity<User> registerUser(User user){
		Optional<User> optional = repository.findByEmail(user.getEmail());
		
		if(optional.isEmpty()) {
			user.setPassword(encryptPassword(user.getPassword()));
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Existente!");
		}		
	}
	
	public Optional<User> cadastrarUsuario(User usuario) {
		if(repository.findByEmail(usuario.getEmail()).isPresent()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
		
		usuario.setPassword(encryptPassword(usuario.getPassword()));
		return Optional.of(repository.save(usuario));
	}
	
	public ResponseEntity<UserCredentialsDTO> credentials(@Valid UserLoginDTO user){
		return repository.findByEmail(user.getEmail()).map(resp -> {
			
			if(passwordCompare(user.getPassword(), resp.getPassword())){
				credentialsDTO = new UserCredentialsDTO(
						generatorBasicToken(user.getEmail(), user.getPassword()), 
						resp.getId(), 
						resp.getEmail());
				return ResponseEntity.status(200).body(credentialsDTO);						
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha Inválida!");
			}
		}).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email inexistente!");
		});
	}
	
	private String encryptPassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(password);
		return senhaEncoder;

	}
	
	private boolean passwordCompare(String typedPassword, String databasePassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(typedPassword, databasePassword);
	}
	
	private String generatorBasicToken(String email, String password) {
		String structure = email + ":" + password;
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCI")));
		return "Basic " + new String(structureBase64);
	}
}
