package nefelus.rpg.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import nefelus.rpg.dto.UserCredentialsDTO;
import nefelus.rpg.dto.UserLoginDTO;
import nefelus.rpg.model.UserModel;
import nefelus.rpg.repository.UserRepository;
import nefelus.rpg.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserService service;

	
	@GetMapping("/all")
	public ResponseEntity<List<UserModel>> getAll(){
		List<UserModel> list = repository.findAll();
		
		if(list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe nenhum usuário na lista!");
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<UserModel> getById(@PathVariable(value = "id") Long id){
		return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
				});
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserModel> postUsuario(@Valid @RequestBody UserModel user){
		return service.registerUser(user);
	}

	
	@PostMapping("/login")
	public ResponseEntity<UserCredentialsDTO> login(@Valid @RequestBody UserLoginDTO user){
		return service.credentials(user);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id){
		Optional<UserModel> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
		}
	}

}
