package nefelus.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import nefelus.rpg.model.AttributeModel;
import nefelus.rpg.model.CharacterModel;
import nefelus.rpg.repository.AttributeRepository;
import nefelus.rpg.repository.CharacterRepository;

@Service
public class CharacterService {	
	
	private @Autowired CharacterRepository repository;
	private @Autowired AttributeRepository attributeRepository;
	
	public AttributeModel attribute(String vocation){
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
	
	public ResponseEntity<CharacterModel> createCharacter(CharacterModel character){
		CharacterModel newCharacter = new CharacterModel();
		AttributeModel attribute = attribute(character.getVocation().toString());		
		
		newCharacter.setName(character.getName());
		newCharacter.setVocation(character.getVocation());
		newCharacter.setRace(character.getRace());
		newCharacter.setAttributeModel(attribute);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCharacter));		
	}
}
