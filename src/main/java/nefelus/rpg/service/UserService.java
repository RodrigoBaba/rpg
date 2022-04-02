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
import nefelus.rpg.model.AttributeModel;
import nefelus.rpg.model.CharacterModel;
import nefelus.rpg.model.UserModel;
import nefelus.rpg.repository.AttributeRepository;
import nefelus.rpg.repository.CharacterRepository;
import nefelus.rpg.repository.UserRepository;

@Service
public class UserService {	
	
	private UserCredentialsDTO credentialsDTO;	
	private @Autowired UserRepository repository;
	private @Autowired CharacterRepository repository2;
	private @Autowired AttributeRepository attributeRepository;
	
	public ResponseEntity<UserModel> registerUser(UserModel user){
		Optional<UserModel> optional = repository.findByEmail(user.getEmail());
		Optional<UserModel> optional2 = repository.findByUser(user.getUser());
		
		if(optional.isEmpty() && optional2.isEmpty()) {
			user.setPassword(encryptPassword(user.getPassword()));
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ou Usuário Existente!");
		}		
	}
	
	public ResponseEntity<UserCredentialsDTO> credentials(@Valid UserLoginDTO user){
		
		Optional<UserModel> optional = repository.findByEmail(user.getUserLogin());
		
		if(optional.isPresent()) {
			if(passwordCompare(user.getPassword(), optional.get().getPassword())){
				credentialsDTO = new UserCredentialsDTO(
						generatorBasicToken(optional.get().getEmail(), user.getPassword()), 
						optional.get().getId(), 
						optional.get().getEmail(),
						optional.get().getUser());
				return ResponseEntity.status(200).body(credentialsDTO);						
			}
			else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha Inválida!");
			}
		} else {
			Optional<UserModel> optional2 = repository.findByUser(user.getUserLogin());
				if(optional2.isPresent()) {
					if(passwordCompare(user.getPassword(), optional2.get().getPassword())) {
						credentialsDTO = new UserCredentialsDTO(
						generatorBasicToken(optional2.get().getEmail(), user.getPassword()), 
						optional2.get().getId(), 
						optional2.get().getEmail(),
						optional2.get().getUser());
						return ResponseEntity.status(200).body(credentialsDTO);
				} else {
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha Inválida!");
				}					
			} 
		}		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ou Usuário Inexistente!");		
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
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(structureBase64);
	}
	
	public Optional<UserModel> createCharacter(UserModel character){
		CharacterModel newCharacter = new CharacterModel();
		Optional<UserModel> user = repository.findById(character.getId());
		newCharacter = character(character.getCharacterModel());
		
		user.get().setCharacterModel(newCharacter);
		
		return Optional.of(repository.save(user.get()));
		
	}
	
	
	private AttributeModel attribute(String vocation){
		AttributeModel attribute = new AttributeModel();

		if(vocation == "Arqueiro") {
			attribute.setDefense(500);
			attribute.setMagicDamage(700);
			attribute.setPhysicalDamage(1400);			
		} else if(vocation == "Mago") {
			attribute.setDefense(700);
			attribute.setMagicDamage(1350);
			attribute.setPhysicalDamage(300);			
		} else if(vocation == "Cavaleiro") {
			attribute.setDefense(1300);
			attribute.setMagicDamage(200);
			attribute.setPhysicalDamage(800);			
		} else {
			attribute.setDefense(600);
			attribute.setMagicDamage(500);
			attribute.setPhysicalDamage(1450);
		}
		return attributeRepository.save(attribute);
	}
	
	private CharacterModel character(CharacterModel character){
		CharacterModel newCharacter = new CharacterModel();
		AttributeModel attribute = attribute(character.getVocation().toString());		
		
		newCharacter.setName(character.getName());
		newCharacter.setVocation(character.getVocation());
		newCharacter.setRace(character.getRace());
		newCharacter.setAttributeModel(attribute);
		
		return repository2.save(newCharacter);		
	}
}
