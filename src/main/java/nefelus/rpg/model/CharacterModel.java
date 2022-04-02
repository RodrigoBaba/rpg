package nefelus.rpg.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nefelus.rpg.util.Race;
import nefelus.rpg.util.Vocation;

@Entity
@Table(name = "tb_character")
public class CharacterModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Vocation vocation;
	
	@Enumerated(EnumType.STRING)
	private Race race;
		
	@OneToOne(cascade = CascadeType.REMOVE)
	private AttributeModel attributeModel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vocation getVocation() {
		return vocation;
	}

	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}	

	public AttributeModel getAttributeModel() {
		return attributeModel;
	}

	public void setAttributeModel(AttributeModel attributeModel) {
		this.attributeModel = attributeModel;
	}	
}