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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import nefelus.rpg.model.CharacterModel;
import nefelus.rpg.repository.CharacterRepository;
import nefelus.rpg.service.CharacterService;

@RestController
@RequestMapping("/character")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CharacterController {

	@Autowired
	private CharacterRepository repository;
	
	private @Autowired CharacterService service;
	
	@GetMapping("/all")
	public ResponseEntity<List<CharacterModel>> getAll(){
		List<CharacterModel> list = repository.findAll();
		
		if(list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Você ainda não possui um personagem!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}
	
	@GetMapping("/id/{id_character}")
	public ResponseEntity<CharacterModel> getByIdCharacter(@PathVariable(value = "id_character") Long id){
		return repository.findById(id).map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp)).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!");
		});		
	}
	
	@PostMapping("/save")
	public ResponseEntity<CharacterModel> createCharacter(@Valid @RequestBody CharacterModel character){
		return service.createCharacter(character);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CharacterModel> updateCharacter(@Valid @RequestBody CharacterModel character){
		Optional<CharacterModel> optional = repository.findById(character.getId());
		
		if(optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inexistente!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(repository.save(character));
		}
	}
	
	@DeleteMapping("/delete/{id_character}")
	@SuppressWarnings("rawtypes")
	public ResponseEntity deleteCharacter(@PathVariable(value = "id_character") Long id) {
		Optional<CharacterModel> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inexistente!");
		}
	}
	
}
