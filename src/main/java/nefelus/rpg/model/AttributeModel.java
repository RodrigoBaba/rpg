package nefelus.rpg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_attibute")
public class AttributeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer magicDamage;
	
	private Integer physicalDamage;
	
	private Integer defense;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMagicDamage() {
		return magicDamage;
	}

	public void setMagicDamage(Integer magicDamage) {
		this.magicDamage = magicDamage;
	}

	public Integer getPhysicalDamage() {
		return physicalDamage;
	}

	public void setPhysicalDamage(Integer physicalDamage) {
		this.physicalDamage = physicalDamage;
	}

	public Integer getDefense() {
		return defense;
	}

	public void setDefense(Integer defense) {
		this.defense = defense;
	}	
}
